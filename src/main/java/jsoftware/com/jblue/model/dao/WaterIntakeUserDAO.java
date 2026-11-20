package jsoftware.com.jblue.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jblue.model.dto.WaterIntakeUserDTO;
import jsoftware.com.jblue.model.exp.DataAccesObjectException;
import jsoftware.com.jblue.model.exp.imp.CorruptInsertionException;
import jsoftware.com.jblue.model.exp.imp.KeyNotGenerateException;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 * Data Access Object (DAO) para la gestión transaccional de la tabla de
 * vinculación de tomas de agua.
 * <br>
 * Administra el ciclo de vida, estados financieros y auditoría de la tabla
 * wki_user.
 *
 * * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-05-30
 * @version 1.0
 */
public class WaterIntakeUserDAO extends AbstractDAO {

    private static final long serialVersionUID = 1L;

    public WaterIntakeUserDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    /**
     * Inserta un nuevo registro de vinculación entre un usuario y una toma de
     * agua.
     * <p>
     * TRAMITES:
     * </p>
     *
     * * @param connection Conexión activa.
     * @param dto DTO con los datos recolectados de la interfaz.
     * @return El ID autoincrementable generado por la base de datos.
     * @throws SQLException Si ocurre un error relacional en el motor.
     * @throws DataAccesObjectException Si la inserción es corrupta o falla la
     * generación de llaves.
     */
    public int insert(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException, DataAccesObjectException {
        int generated_id = -1;
        String query = """
                       INSERT INTO wki_user
                       (
                            process_id, employee_id, user_id, user_type_id, address_id, 
                            water_intake_id, water_intake_type_id, is_consumer, office_id, user_name, 
                            description, observation, fiscal_year, last_month_paid, last_amount_paid, 
                            last_process_id, last_employee_update, status
                       )
                       VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                       """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, dto.getProcess_id());
            ps.setInt(2, dto.getEmployee_id());
            ps.setInt(3, dto.getUser_id());
            ps.setInt(4, dto.getUser_type_id());
            ps.setInt(5, dto.getAddress_id());
            ps.setInt(6, dto.getWater_intake_id());
            ps.setInt(7, dto.getWater_intake_type_id());
            ps.setBoolean(8, dto.isIs_consumer());
            ps.setInt(9, dto.getOffice_id());
            ps.setString(10, dto.getUser_name());
            ps.setString(11, dto.getDescription());
            ps.setString(12, dto.getObservation());
            ps.setInt(13, dto.getFiscal_year());
            ps.setInt(14, dto.getLast_month_paid());
            ps.setBigDecimal(15, dto.getLast_amount_paid());
            ps.setInt(16, dto.getLast_process_id());
            ps.setInt(17, dto.getLast_employee_update());
            ps.setInt(18, dto.getStatus());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == PreparedStatement.EXECUTE_FAILED || affectedRows != 1) {
                throw new CorruptInsertionException();
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new KeyNotGenerateException();
                }
                generated_id = rs.getInt(1);
                // Enriquecimiento del DTO post-operación bajo el flujo JBlue
                dto.setId(generated_id);
            }
        }
        return generated_id;
    }

    /**
     * Recupera una vinculación específica por su ID empleando metadatos
     * dinámicos.
     *
     * @param connection Conexión activa.
     * @param id Llave primaria del registro.
     * @return Un Optional conteniendo el WaterIntakeUserDTO mapeado, o vacío.
     * @throws SQLException Si ocurre un error en la consulta.
     */
    public Optional<WaterIntakeUserDTO> findById(JDBConnection connection, int id) throws SQLException {
        Optional<WaterIntakeUserDTO> result = Optional.empty();
        String query = "SELECT * FROM wki_user WHERE id = ?";

        try (Connection conn = connection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    WaterIntakeUserDTO dto = build(rs);
                    result = Optional.of(dto);
                }
            }
        }
        return result;
    }

    /**
     * Verifica la existencia previa de una vinculación activa basándose en los
     * criterios clave del padrón. Utiliza comparaciones NULL-safe para la
     *
     * @param connection
     * @param dto
     * @return
     * @throws SQLException
     */
    public boolean exists(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException {
        boolean isPresent = false;
        // CORREGIDO: Nombre de tabla unificado y sintaxis de paréntesis rectificada
        String query = """
                       SELECT id, status, office_id FROM wki_user 
                       WHERE water_intake_id = ? 
                         AND user_id = ? 
                         AND address_id = ? 
                         AND user_name = ? 
                         AND description <=> ? 
                       LIMIT 1
                       """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            // 1. Flujo JBlue: Casteo manual numérico seguro
            ps.setInt(1, dto.getWater_intake_id());
            ps.setInt(2, dto.getUser_id());
            ps.setInt(3, dto.getAddress_id());
            // 2. Criterios de texto
            ps.setString(4, dto.getUser_name());
            ps.setString(5, dto.getDescription());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    isPresent = true;
                }
            }
        }
        return isPresent;
    }

    /**
     * MOVIMIENTO QUE ACTUALIZA EL ID DEL USUARIO, EL TIPO DE USUARIO, EL NOMBRE
     * DE USUARIO
     *
     * @param connection
     * @param dto
     * @param old_user_id
     * @return
     * @throws SQLException
     */
    public boolean updateOwnerChange(JDBConnection connection, WaterIntakeUserDTO dto, int old_user_id) throws SQLException {
        boolean res = false;
        // CORREGIDO: Se añadió la coma antes de status
        String query = """
                       UPDATE wki_user SET
                           user_id = ?,
                           user_type_id = ?,
                           user_name = ?,
                           description = ?,
                           observation = ?,
                           last_employee_update = ?,
                           last_process_id = ?,
                           status = '1'
                       WHERE id = ? AND user_id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            // Flujo JBlue: Cast explícito a tipos numéricos requeridos por MySQL
            ps.setInt(1, dto.getUser_id());
            ps.setInt(2, dto.getUser_type_id());
            ps.setString(3, dto.getUser_name());
            setNull(ps, 4, dto.getDescription());
            setNull(ps, 5, dto.getObservation());
            ps.setInt(6, dto.getLast_employee_update());

            // Proceso opcional
            setNull(ps, 7, dto.getLast_process_id());

            // Cláusula WHERE (CORREGIDO: Mapeo de índices secuenciales 8 y 9 sin colisiones)
            ps.setInt(8, dto.getId());
            ps.setInt(9, old_user_id);

            if (ps.executeUpdate() == 1) {
                res = true;
            }
        }
        return res;
    }

    /**
     * MOVIMIENTO QUE ACTUALIZA EL ID DE LA TOMA DE AGUA FISICA, PARA EL TITULAR
     * Y LOS CONSUMIDORES
     *
     * @param connection
     * @param dto
     * @return
     * @throws SQLException
     */
    public boolean updateWaterIntake(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException {
        boolean res = false;
        // CORREGIDO: Se añadió la coma antes de status
        String query = """
                       UPDATE wki_user SET
                           water_intake_id = ?,
                           water_intake_type_id = ?,
                           description = ?,
                           observation = ?,
                           last_employee_update = ?,
                           last_process_id = ?,
                           status = '1'
                       WHERE id = ? AND water_intake_id = ? AND water_intake_type_id = ? AND status = 1 AND date_end IS NULL
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, dto.getWater_intake_id());
            ps.setInt(2, dto.getWater_intake_type_id());
            setNull(ps, 3, dto.getDescription());
            setNull(ps, 4, dto.getObservation());
            ps.setInt(5, dto.getLast_employee_update());
            ps.setInt(6, dto.getLast_process_id());
            ps.setInt(7, dto.getId());
            ps.setInt(8, dto.getWater_intake_id());
            ps.setInt(9, dto.getWater_intake_type_id());
            if (ps.executeUpdate() == 1) {
                res = true;
            }
        }
        return res;
    }

    /**
     * METODO PARA ACTUALIZAR EL STATUS DESDE LAS RUTINAS DEL SISTEMA.
     */
    public boolean updateStatus(JDBConnection connection, WaterIntakeUserDTO dto, String new_status) throws SQLException {
        boolean res = false;
        // CORREGIDO: Se añadió la coma antes de status
        String query = """
                       UPDATE wki_user SET
                           description = ?,
                           observation = ?,
                           last_employee_update = ?,
                           last_process_id = ?,
                           status = ?
                       WHERE id = ? AND user_id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            setNull(ps, 1, dto.getDescription());
            setNull(ps, 2, dto.getObservation());

            // Flujo JBlue: Cast manual de auditorías y llaves
            ps.setInt(3, dto.getLast_employee_update());
            ps.setInt(4, dto.getLast_process_id());
            // Status e identificadores
            ps.setString(5, new_status);
            ps.setInt(6, dto.getId());
            ps.setInt(7, dto.getUser_id());
            if (ps.executeUpdate() == 1) {
                res = true;
            }
        }
        return res;
    }

//    /**
//     * Realiza una actualización dinámica basada en deltas (Delta Update) sobre
//     * los campos del padrón.
//     *
//     * * @param connection Conexión transaccional activa.
//     * @param oldData Datos vigentes en la base de datos.
//     * @param newData Datos modificados capturados en Swing.
//     * @return true si se registraron cambios en MySQL, false si no hubo
//     * alteración de datos.
//     * @throws SQLException Si ocurre un fallo en las sentencias del driver.
//     */
//    public boolean update(JDBConnection connection, WaterIntakeUserDTO oldData, WaterIntakeUserDTO newData) throws SQLException {
//        List<String> setColumns = new ArrayList<>();
//        List<Object> parameters = new ArrayList<>();
//
//        // Escaneo secuencial seguro de deltas de texto y auditorías
//        autoSet(oldData.getUserName(), newData.getUserName(), "user_name", setColumns, parameters);
//        autoSet(oldData.getDescription(), newData.getDescription(), "description", setColumns, parameters);
//        autoSet(oldData.getObservation(), newData.getObservation(), "observation", setColumns, parameters);
//        autoSet(oldData.getStatus(), newData.getStatus(), "status", setColumns, parameters);
//
//        // Control dinámico de Llaves e Índices Numéricos modificados
//        if (!java.util.Objects.equals(oldData.getAddressId(), newData.getAddressId())) {
//            setColumns.add("address_id");
//            parameters.add(Integer.valueOf(newData.getAddressId()));
//        }
//        if (!java.util.Objects.equals(oldData.getWaterIntakeTypeId(), newData.getWaterIntakeTypeId())) {
//            setColumns.add("water_intake_type_id");
//            parameters.add(Integer.valueOf(newData.getWaterIntakeTypeId()));
//        }
//        if (!java.util.Objects.equals(oldData.getUserTypeId(), newData.getUserTypeId())) {
//            setColumns.add("user_type_id");
//            parameters.add(Integer.valueOf(newData.getUserTypeId()));
//        }
//
//        // Control selectivo del Historial de Pagos y Recaudación (Casteos de tipos MySQL)
//        if (!java.util.Objects.equals(oldData.getFiscalYear(), newData.getFiscalYear())) {
//            setColumns.add("current_fiscal_year");
//            parameters.add(Integer.valueOf(newData.getFiscalYear()));
//        }
//        if (!java.util.Objects.equals(oldData.getLastMonthPaid(), newData.getLastMonthPaid())) {
//            setColumns.add("last_month_paid");
//            parameters.add(Integer.valueOf(newData.getLastMonthPaid()));
//        }
//        if (!java.util.Objects.equals(oldData.getLastAmountPaid(), newData.getLastAmountPaid())) {
//            setColumns.add("last_amount_paid");
//            parameters.add(Double.valueOf(newData.getLastAmountPaid()));
//        }
//
//        // Si el formulario en Swing no alteró ningún JTextField, cancelamos el viaje a la BD
//        if (setColumns.isEmpty()) {
//            return false;
//        }
//
//        // Adición forzada del operador responsable y tipos de procesos mutados
//        setColumns.add("last_employee_update");
//        parameters.add(Integer.valueOf(newData.getLastEmployeeUpdate()));
//
//        if (Func.isNotNullEmptyBlank(newData.getLastProcessId())) {
//            setColumns.add("last_process_id");
//            parameters.add(Integer.valueOf(newData.getLastProcessId()));
//        }
//
//        String setClause = String.join("=?, ", setColumns) + "=?";
//        String query = "UPDATE wki_user SET " + setClause + " WHERE id = ?";
//
//        try (Connection conn = connection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
//            int index = 1;
//            for (Object param : parameters) {
//                if (param == null) {
//                    ps.setNull(index, Types.VARCHAR);
//                } else if (param instanceof Integer intVal) {
//                    ps.setInt(index, intVal);
//                } else if (param instanceof Double doubleVal) {
//                    ps.setDouble(index, doubleVal);
//                } else {
//                    ps.setObject(index, param);
//                }
//                index++;
//            }
//
//            // Cierre posicional del parámetro del WHERE (ID de la vinculación)
//            ps.setInt(index, Integer.parseInt(newData.getId()));
//            return ps.executeUpdate() > 0;
//        }
//    }
    /**
     * ACTUALIZA LA TOMA A STATUS "ELIMINADO"
     *
     * @param connection
     * @param dto
     * @return
     * @throws SQLException
     */
    public boolean updateStatusDelete(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException {
        return updateStatus(connection, dto, "3");
    }

    /**
     * ACTUALIZA LA TOMA A STATUS "REGISTRADO" EN CASO DE QUE NO SE HAYA
     * REGISTRADO EL PAGO
     *
     * @param connection
     * @param dto
     * @return
     * @throws SQLException
     */
    public boolean updateStatusRegister(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException {
        return updateStatus(connection, dto, "21");
    }

    /**
     * ACTUALIZA LA TOMA A STATUS "RECONECTADO" EN CASO DE HABER ESTADO CON
     * STATUS "DESCONECTADO"
     *
     * @param connection
     * @param dto
     * @return
     * @throws SQLException
     */
    public boolean updateStatusReconected(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException {
        return updateStatus(connection, dto, "22");
    }

    /**
     * ACTUALIZA LA TOMA A STATUS "DESCONECTADO" EN CASO DE NO CUMPLIR LOS PAGOS
     *
     * @param connection
     * @param dto
     * @return
     * @throws SQLException
     */
    public boolean updateStatusDesconected(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException {
        return updateStatus(connection, dto, "23");
    }

    private <T> void autoSet(T oldValue, T newValue, String columnName, List<String> setColumns, List<Object> parameters) {
        if (!java.util.Objects.equals(oldValue, newValue)) {
            setColumns.add(columnName);
            parameters.add(newValue);
        }
    }

    /**
     * ACTUALIZA LA TOMA A STATUS "RECONECTADO" EN CASO DE HABER ESTADO CON
     * STATUS "DESCONECTADO"
     *
     * @param connection
     * @param dto
     * @return
     * @throws SQLException
     */
    public boolean addMov(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException {
        boolean res = false;
        // CORREGIDO: Se añadió la coma antes de status
        String query = """
                       UPDATE wki_user SET
                           description = ?,
                           observation = ?,
                           last_employee_update = ?,
                           last_process_id = ?,
                       WHERE id = ? AND user_id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, dto.getDescription());
            ps.setString(2, dto.getObservation());
            // Flujo JBlue: Cast manual de auditorías y llaves
            ps.setInt(3, dto.getLast_employee_update());
            ps.setInt(4, dto.getLast_process_id());
            ps.setInt(5, dto.getId());
            ps.setInt(6, dto.getUser_id());
            if (ps.executeUpdate() == 1) {
                res = true;
            }
        }
        return res;
    }

    /**
     * ACTUALIZA LA TOMA A STATUS "RECONECTADO" EN CASO DE HABER ESTADO CON
     * STATUS "DESCONECTADO"
     *
     * @param connection
     * @param dto
     * @return
     * @throws SQLException
     */
    public boolean updateLastPayment(JDBConnection connection, WaterIntakeUserDTO dto) throws SQLException {
        boolean res = false;
        // CORREGIDO: Se añadió la coma antes de status
        String query = """
                       UPDATE wki_user SET
                           fiscal_year=?,
                           last_month_paid=?,
                           last_amount_paid=?,
                           description = ?,
                           last_employee_update = ?
                       WHERE id = ? AND user_id = ?
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, dto.getFiscal_year());
            ps.setInt(2, dto.getLast_month_paid());
            ps.setBigDecimal(3, dto.getLast_amount_paid());
            ps.setString(4, dto.getDescription());
            ps.setInt(5, dto.getLast_employee_update());
            ps.setInt(6, dto.getId());
            ps.setInt(7, dto.getUser_id());
            if (ps.executeUpdate() == 1) {
                res = true;
            }
        }
        return res;
    }

    public List<WaterIntakeUserDTO> search(JDBConnection connection, String userName) throws SQLException {
        List<WaterIntakeUserDTO> listaResultados = new ArrayList<>();
        String query = """
                            SELECT 
                                id, 
                                user_name 
                            FROM 
                                wki_user 
                            WHERE 
                                REPLACE(REPLACE(user_name,'_', ''), ' ', '') 
                                LIKE replace(replace(?, '_', ''), ' ', '')
                       """;
        try (PreparedStatement pstmt = connection.getNewPreparedStatement(query)) {
            // Se agregan los comodines del LIKE directamente en el parámetro de Java
            pstmt.setString(1, "%" + userName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                // Usamos while para capturar todos los registros que coincidan con el LIKE
                while (rs.next()) {
                    WaterIntakeUserDTO dto = new WaterIntakeUserDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setUser_name(rs.getString("user_name"));
                    listaResultados.add(dto);
                }
            }
        }
        return listaResultados;
    }

    public WaterIntakeUserDTO build(ResultSet rs) throws SQLException {
        WaterIntakeUserDTO dto = new WaterIntakeUserDTO();
        dto.setId(rs.getInt("id"));
        dto.setProcess_id(rs.getInt("process_id"));
        dto.setEmployee_id(rs.getInt("employee_id"));
        dto.setUser_id(rs.getInt("user_id"));
        dto.setUser_type_id(rs.getInt("user_type_id"));
        dto.setAddress_id(rs.getInt("address_id"));
        dto.setWater_intake_id(rs.getInt("water_intake_id"));
        dto.setWater_intake_type_id(rs.getInt("water_intake_type_id"));
        dto.setIs_consumer(rs.getBoolean("is_consumer"));
        dto.setOffice_id(rs.getInt("office_id"));
        dto.setUser_name(rs.getString("user_name"));
        dto.setUser_name(rs.getString("description"));
        dto.setUser_name(rs.getString("observation"));
        dto.setFiscal_year(rs.getInt("fiscal_year"));
        dto.setLast_month_paid(rs.getInt("last_month_paid"));
        dto.setLast_amount_paid(rs.getBigDecimal("last_amount_paid"));
        dto.setLast_process_id(rs.getInt("last_process_id"));
        dto.setLast_employee_update(rs.getInt("last_employee_update"));
        dto.setStatus(rs.getInt("status"));
        Timestamp du = rs.getTimestamp("");
        dto.setDate_update(du == null ? null : du.toLocalDateTime());
        Timestamp dr = rs.getTimestamp("");
        dto.setDate_update(dr == null ? null : dr.toLocalDateTime());
        Timestamp de = rs.getTimestamp("");
        dto.setDate_update(de == null ? null : de.toLocalDateTime());
        return dto;
    }

}
