package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jblue.model.dto.ProcessDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 * DAO para la gestión y auditoría de procesos en el sistema de cobros.
 *
 * @author Juan P. Campos C.
 */
public class ProcessDAO extends AbstractDAO {

    public ProcessDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    public boolean startProcess(JDBConnection connection, ProcessDTO dto) throws SQLException {
        boolean success = false;
        String sql = """
                     INSERT INTO pro_process 
                     (
                        sequence, process_type, employee_start, date_start, administration_start_id, 
                        current_db_user, status, committee_id, office_id, 
                        last_employee_update
                     )
                     VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_USER, 10, ?, ?, ?)
                 """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getSequence());
            pstmt.setInt(2, dto.getProcess_type());
            pstmt.setInt(3, dto.getEmployee_start());
            pstmt.setInt(4, dto.getAdministration_start_id());
            pstmt.setString(5, dto.getCurrent_db_user());
            pstmt.setInt(6, dto.getCommittee_id());
            pstmt.setInt(7, dto.getOffice_id());
            pstmt.setInt(8, dto.getLast_employee_update());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }
        }
        return success;
    }

    public boolean validProcess(JDBConnection connection, ProcessDTO dto) throws SQLException {
        boolean success = false;
        String sql = """
                        UPDATE pro_process SET
                            employee_valid = ?, 
                            date_valid = CURRENT_TIMESTAMP,  
                            current_db_user = CURRENT_USER, 
                            status = 11, 
                            last_employee_update = ?
                        WHERE id = ?
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getEmployee_valid());
            pstmt.setInt(2, dto.getLast_employee_update());
            pstmt.setInt(3, dto.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }
        }
        return success;
    }

    public boolean invalidProcess(JDBConnection connection, ProcessDTO dto) throws SQLException {
        boolean success = false;
        String sql = """
                        UPDATE pro_process SET
                            employee_valid = NULL, 
                            date_valid = NULL,  
                            current_db_user = CURRENT_USER, 
                            status = 5, 
                            last_employee_update = ?,
                            date_end = CURRENT_TIMESTAMP
                        WHERE id = ?
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getLast_employee_update());
            pstmt.setInt(2, dto.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }
        }
        return success;
    }

    public boolean movProcess(JDBConnection connection, ProcessDTO dto) throws SQLException {
        boolean success = false;
        String sql = """
                        UPDATE pro_process SET
                            employee_mov = ?, 
                            date_mov = CURRENT_TIMESTAMP,  
                            current_db_user = CURRENT_USER, 
                            last_employee_update = ?
                        WHERE id = ?
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getEmployee_mov());
            pstmt.setInt(2, dto.getLast_employee_update());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }
        }
        return success;
    }

    public boolean paymentProcess(JDBConnection connection, ProcessDTO dto) throws SQLException {
        boolean success = false;
        String sql = """
                        UPDATE pro_process SET
                            employee_payment = ?, 
                            date_payment = CURRENT_TIMESTAMP,
                            status = 12,
                            current_db_user = CURRENT_USER, 
                            last_employee_update = ?
                        WHERE id = ?
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getEmployee_payment());
            pstmt.setInt(2, dto.getLast_employee_update());
            pstmt.setInt(3, dto.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        }
        return success;
    }

    public boolean finalizeProcess(JDBConnection connection, ProcessDTO dto) throws SQLException {
        boolean success = false;
        String sql = """
                        UPDATE pro_process SET
                            employee_finalize = ?, 
                            date_finalize = CURRENT_TIMESTAMP,
                            administration_end = ?
                            status = 13,
                            current_db_user = CURRENT_USER, 
                            last_employee_update = ?
                        WHERE id = ?
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getEmployee_valid());
            pstmt.setInt(2, dto.getAdministration_end_id());
            pstmt.setInt(3, dto.getLast_employee_update());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        }
        return success;
    }

    public boolean cancelProcess(JDBConnection connection, ProcessDTO dto) throws SQLException {
        boolean success = false;
        String sql = """
                        UPDATE pro_process SET
                            administration_end = ?,
                            status = 5,
                            current_db_user = CURRENT_USER, 
                            last_employee_update = ?,
                            date_end = CURRENT_TIMESTAMP
                        WHERE id = ?
                     """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dto.getAdministration_end_id());
            ps.setInt(2, dto.getLast_employee_update());
            ps.setInt(3, dto.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        }
        return success;
    }

    public boolean caducateProcess(JDBConnection connection, ProcessDTO dto) throws SQLException {
        boolean success = false;
        String sql = """
                        UPDATE pro_process SET
                            administration_end = ?,
                            status = 14,
                            current_db_user = CURRENT_USER, 
                            last_employee_update = ?,
                            date_end = CURRENT_TIMESTAMP
                        WHERE id = ?
                     """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dto.getAdministration_end_id());
            ps.setInt(2, dto.getLast_employee_update());
            ps.setInt(3, dto.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        }
        return success;
    }

    /**
     * Obtiene la lista de procesos filtrando por estatus, oficina y comité.
     *
     * @param connection
     * @param status
     * @param officeId
     * @param committeeId
     * @return
     * @throws java.sql.SQLException
     */
    public List<ProcessDTO> get(JDBConnection connection, int status, int officeId, int committeeId) throws SQLException {
        List<ProcessDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM pro_process WHERE status = ? AND office_id = ? AND committee_id = ?";
        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, status);
            pstmt.setInt(2, officeId);
            pstmt.setInt(3, committeeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    /**
     * Busca y retorna un proceso específico por su ID.
     *
     * @param connection
     * @param id
     * @return
     */
    public Optional<ProcessDTO> getProcess(JDBConnection connection, int id) throws SQLException {
        String sql = "SELECT * FROM pro_process WHERE id = ?";
        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    private ProcessDTO mapRow(ResultSet rs) throws SQLException {
        ProcessDTO dto = new ProcessDTO();
        dto.setId(rs.getInt("id"));
        dto.setSequence(rs.getInt("sequence"));
        dto.setProcess_type(rs.getInt("process_type"));

        dto.setEmployee_start(rs.getInt("employee_start"));
        dto.setDate_start(toLocalDateTime(rs.getTimestamp("date_start")));

        dto.setEmployee_valid(rs.getInt("employee_valid"));
        dto.setDate_valid(toLocalDateTime(rs.getTimestamp("date_valid")));

        dto.setEmployee_mov(rs.getInt("employee_mov"));
        dto.setDate_mov(toLocalDateTime(rs.getTimestamp("date_mov")));

        dto.setEmployee_payment(rs.getInt("employee_payment"));
        dto.setDate_payment(toLocalDateTime(rs.getTimestamp("date_payment")));

        dto.setEmployee_finalize(rs.getInt("employee_finalize"));
        dto.setDate_finalize(toLocalDateTime(rs.getTimestamp("date_finalize")));

        dto.setEmployee_print(rs.getInt("employee_print"));
        dto.setDate_print(toLocalDateTime(rs.getTimestamp("date_print")));

        dto.setAdministration_start_id(rs.getInt("administration_start_id"));
        dto.setAdministration_end_id(rs.getInt("administration_end_id"));
        dto.setCurrent_db_user(rs.getString("current_db_user"));
        dto.setStatus(rs.getInt("status"));
        dto.setCommittee_id(rs.getInt("committee_id"));
        dto.setOffice_id(rs.getInt("office_id"));
        dto.setLast_employee_update(rs.getInt("last_employee_update"));

        dto.setDate_update(toLocalDateTime(rs.getTimestamp("date_update")));
        dto.setDate_register(toLocalDateTime(rs.getTimestamp("date_register")));
        dto.setDate_end(toLocalDateTime(rs.getTimestamp("date_end")));
        return dto;
    }

    private void setTimestampOrNull(PreparedStatement pstmt, int index, LocalDateTime value) throws SQLException {
        if (value != null) {
            pstmt.setTimestamp(index, Timestamp.valueOf(value));
        } else {
            pstmt.setNull(index, Types.TIMESTAMP);
        }
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

}
