package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jsoftware.com.jblue.model.dto.ProcessDTO;
import jsoftware.com.jblue.model.exp.ProcessException;
import jsoftware.com.jblue.model.exp.imp.CorruptInsertionException;
import jsoftware.com.jblue.model.exp.imp.KeyNotGenerateException;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 * Data Access Object (DAO) para el control evolutivo de trámites operativos.
 * <br>
 * Administra de forma secuencial las fases y estados del Workflow de JBlue.
 *
 * * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-05-30
 * @version 1.1
 */
public class ProcessDAO extends AbstractDAO {

    private static final long serialVersionUID = 1L;

    public ProcessDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    /**
     * Fase [1]: Captura de Datos. Registra el inicio de un trámite en
     * ventanilla.
     */
    public boolean startProcess(JDBConnection connection, ProcessDTO dto) throws SQLException, KeyNotGenerateException, CorruptInsertionException {
        boolean res = false;
        String query = """
                   INSERT INTO pro_process
                   (sequence_process, process_type, employee_start, date_start, administration_start, current_db_user, status, last_employee_update) 
                   VALUES
                   (?, ?, ?, ?, ?, CURRENT_USER, 10, ?)
                   """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Mapeo corregido de parámetros posicionales
            ps.setString(1, dto.getSequenceProcess());
            ps.setString(2, dto.getProcessType());
            ps.setString(3, dto.getEmployeeStart());
            ps.setString(4, dto.getDateStart());
            ps.setString(5, dto.getAdministrationStart());
            ps.setString(6, dto.getLastEmployeeUpdate());
            int affectedRows = ps.executeUpdate();
            res = affectedRows == 1;
            if (!res) {
                throw new CorruptInsertionException();
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new KeyNotGenerateException();
                }
                int id = rs.getInt(1);

                // Enriquecimiento del DTO tras el éxito de la inserción
                dto.put("id", String.valueOf(id));
                res = true;
            }
        }
        return res;
    }

    /**
     * Fase [2]: VALIDACION DE INFORMACION.
     * <br> AQUI SE PUEDE REGISTRAR LA ADMINISTRACION QUE FINALIZA DEBIDO A QUE
     * SE PUEDE PONER EN SITUACION CRITICA EL TRAMITE
     */
    public boolean validProcess(JDBConnection connection, ProcessDTO dto) throws SQLException, ProcessException {
        String query = """
                        UPDATE pro_process SET
                            employee_valid = ?, 
                            date_valid = CURRENT_TIMESTAMP,
                            administration_end = ?,
                            current_db_user = CURRENT_USER, 
                            status = ?, 
                            last_employee_update = ?
                        WHERE id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, dto.getEmployeeValid());
            setNull(ps, 2, dto.getAdministrationEnd());
            setNull(ps, 3, dto.getStatus());
            ps.setString(4, dto.getLastEmployeeUpdate());
            ps.setString(5, dto.getId());
            //ESTA FUNCION DEBE ACTUALIZAR SOLO UN REGISTRO
            boolean rt = ps.executeUpdate() == 1;
            if (!rt) {
                throw new ProcessException(1, "ERROR EN VALIDACIÓN: El trámite ID " + dto.getId() + " no existe o no pudo mutar.");
            }
            return rt;
        }
    }

    /**
     * Fase [2]: VALIDACION DE INFORMACION.
     */
    public boolean movProcess(JDBConnection connection, ProcessDTO dto) throws SQLException, ProcessException {
        String query = """
                        UPDATE pro_process SET
                            employee_mov = ?, 
                            date_mov = CURRENT_TIMESTAMP, 
                            current_db_user = CURRENT_USER, 
                            last_employee_update = ?
                        WHERE id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, dto.getEmployeeMov());
            ps.setString(2, dto.getLastEmployeeUpdate());
            ps.setString(4, dto.getLastEmployeeUpdate());
            //ESTA FUNCION DEBE ACTUALIZAR SOLO UN REGISTRO
            boolean rt = ps.executeUpdate() == 1;
            if (!rt) {
                throw new ProcessException(1, "ERROR EN VALIDACIÓN: El trámite ID " + dto.getId() + " no existe o no pudo mutar.");
            }
            return rt;
        }
    }

    /**
     * Fase [4]: Registro de Pago en Cajas.
     */
    public boolean payProcess(JDBConnection connection, ProcessDTO dto) throws SQLException, ProcessException {
        boolean res = false;
        String query = """
                        UPDATE pro_process SET
                            employee_payment = ?, 
                            date_payment = CURRENT_TIMESTAMP, 
                            current_db_user = CURRENT_USER,
                            status = 12,
                            last_employee_update = ?
                        WHERE id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, dto.getEmployeePayment());
            ps.setString(2, dto.getLastEmployeeUpdate());
            ps.setString(3, dto.getId());
            //ESTA FUNCION DEBE ACTUALIZAR SOLO UN REGISTRO
            res = ps.executeUpdate() == 1;
            if (!res) {
                throw new ProcessException(1, "ERROR EN VALIDACIÓN: El trámite ID " + dto.getId() + " no existe o no pudo mutar.");
            }
        }
        return res;
    }

    /**
     * Fase [6]: Finalización e incorporación definitiva al Padrón Activo.
     */
    public boolean endProcess(JDBConnection connection, ProcessDTO dto) throws SQLException, ProcessException {
        boolean res = false;
        String query = """
                        UPDATE pro_process SET
                            employee_finalize = ?, 
                            date_finalize = CURRENT_TIMESTAMP, 
                            administration_end = ?
                            current_db_user = CURRENT_USER,
                            status = 13,
                            last_employee_update = ?
                        WHERE id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, dto.getEmployeeFinalize());
            ps.setString(2, dto.getAdministrationEnd());
            ps.setString(3, dto.getLastEmployeeUpdate());
            ps.setString(4, dto.getId());
            //ESTA FUNCION DEBE ACTUALIZAR SOLO UN REGISTRO
            res = ps.executeUpdate() == 1;
            if (!res) {
                throw new ProcessException(1, "ERROR EN VALIDACIÓN: El trámite ID " + dto.getId() + " no existe o no pudo mutar.");
            }
        }
        return res;
    }

    /**
     * Fase [5]: Impresión de Comprobante / Título de Concesión.
     */
    public boolean printProcess(JDBConnection connection, ProcessDTO dto) throws SQLException, ProcessException {
        boolean res = false;
        String query = """
                        UPDATE pro_process SET
                            employee_print = ?, 
                            date_print = CURRENT_TIMESTAMP, 
                            current_db_user = CURRENT_USER,
                            last_employee_update = ?
                        WHERE id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, dto.getEmployeePrint());
            ps.setString(2, dto.getLastEmployeeUpdate());
            ps.setString(3, dto.getId());
            //ESTA FUNCION DEBE ACTUALIZAR SOLO UN REGISTRO
            res = ps.executeUpdate() == 1;
            if (!res) {
                throw new ProcessException(1, "ERROR EN VALIDACIÓN: El trámite ID " + dto.getId() + " no existe o no pudo mutar.");
            }
        }
        return res;
    }

    /**
     * Cancelación explícita del trámite por dictamen o desistimiento del
     * ciudadano.
     * <br>CUANDO CADUCA UN TRAMITE A ESTE SE LE AGREGA LA FECGA DE
     * FINALIZACION(DATE_END) QUE ES LA FECHA EN QUE SE CANCELO, CADUCO, SE
     * INHABILITO EL REGISTRO Y/O SE ELIMINO EL REGISTRO(STATUS 3)
     */
    public boolean cancelProcess(JDBConnection connection, ProcessDTO dto) throws SQLException, ProcessException {
        boolean res = false;
        String query = """
                        UPDATE pro_process SET
                            employee_valid = ?, 
                            date_valid = CURRENT_TIMESTAMP,
                            administration_end = ?, 
                            date_end = CURRENT_TIMESTAMP, 
                            current_db_user = CURRENT_USER,
                            status = 5
                            last_employee_update = ?
                        WHERE id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, dto.getEmployeeValid());
            ps.setString(1, dto.getAdministrationEnd());
            ps.setString(2, dto.getLastEmployeeUpdate());
            ps.setString(3, dto.getId());
            //ESTA FUNCION DEBE ACTUALIZAR SOLO UN REGISTRO
            res = ps.executeUpdate() == 1;
            if (!res) {
                throw new ProcessException(1, "ERROR EN VALIDACIÓN: El trámite ID " + dto.getId() + " no existe o no pudo mutar.");
            }
        }
        return res;
    }

    /**
     * Cierre automático por caducidad (Expiración del plazo de 30 días).
     */
    public boolean caducateProcess(JDBConnection connection, ProcessDTO dto) throws SQLException, ProcessException {
        boolean res = false;
        String query = """
                        UPDATE pro_process SET
                            administration_end = ?, 
                            date_end = CURRENT_TIMESTAMP, 
                            current_db_user = CURRENT_USER,
                            status = 14
                            last_employee_update = ?
                        WHERE id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, dto.getEmployeePrint());
            ps.setString(2, dto.getLastEmployeeUpdate());
            ps.setString(3, dto.getId());
            //ESTA FUNCION DEBE ACTUALIZAR SOLO UN REGISTRO
            res = ps.executeUpdate() == 1;
            if (!res) {
                throw new ProcessException(1, "ERROR EN VALIDACIÓN: El trámite ID " + dto.getId() + " no existe o no pudo mutar.");
            }
        }
        return res;
    }

    // --- MÉTODOS DE FILTRADO Y CONSULTAS ---
    public List<ProcessDTO> getStartProcedures(JDBConnection connection) throws SQLException {
        return getProcess(connection, 10);
    }

    public List<ProcessDTO> getValidProcedures(JDBConnection connection) throws SQLException {
        return getProcess(connection, 11);
    }

    public List<ProcessDTO> getPaymentProcedures(JDBConnection connection) throws SQLException {
        return getProcess(connection, 12);
    }

    public List<ProcessDTO> getEndProcedures(JDBConnection connection) throws SQLException {
        return getProcess(connection, 13);
    }

    public List<ProcessDTO> getCaducateProcedures(JDBConnection connection) throws SQLException {
        return getProcess(connection, 14);
    }

    public List<ProcessDTO> getCanceledProcedures(JDBConnection connection) throws SQLException {
        return getProcess(connection, 5);
    }

    /**
     * Recupera y mapea de manera dinámica la lista de trámites filtrados por su
     * estatus actual.
     */
    public List<ProcessDTO> getProcess(JDBConnection connection, int status) throws SQLException {
        List<ProcessDTO> list = new ArrayList<>(50);
        String query = "SELECT * FROM pro_process WHERE status = ?";

        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                int size = md.getColumnCount();

                while (rs.next()) {
                    // CORRECCIÓN: Se remueve el parámetro 'size' del constructor. 
                    // El DTO administra su propio mapa interno de capacidad fija 40.
                    ProcessDTO process = new ProcessDTO();
                    for (int i = 1; i <= size; i++) {
                        String label = md.getColumnLabel(i);
                        process.put(label, rs.getString(label));
                    }
                    list.add(process);
                }
            }
        }
        return list;
    }

    public boolean exist(JDBConnection connection, ProcessDTO process) throws SQLException {
        boolean res = false;
        String query = "SELECT * FROM pro_process WHERE id = ? AND status NOT IN(2, 3, 6) AND date_end IS NULL";
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, process.getId());
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                int size = md.getColumnCount();
                res = rs.next();
                if (!res) {
                    return res;
                }
                // CORRECCIÓN: Se remueve el parámetro 'size' del constructor. 
                // El DTO administra su propio mapa interno de capacidad fija 40.
                for (int i = 1; i <= size; i++) {
                    String label = md.getColumnLabel(i);
                    process.put(label, rs.getString(label));
                }

            }
        }
        return res;
    }
}
