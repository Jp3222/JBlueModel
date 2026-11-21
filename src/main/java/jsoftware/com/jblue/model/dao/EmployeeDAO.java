package jsoftware.com.jblue.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import jsoftware.com.jblue.model.dto.EmployeeDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 * DAO para la gestión de datos generales, fiscales y de contacto de empleados.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 */
public class EmployeeDAO extends AbstractDAO {

    private static final String BASE_COLUMNS
            = "id, rfc, curp, first_name, last_name1, last_name2, gender, birthdate, "
            + "personal_email, personal_number, street1, street2, inside_number, "
            + "outside_number, committee_id, status, last_employee_update, "
            + "date_update, date_register, date_end";

    public EmployeeDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    /**
     * Inserta un nuevo registro de empleado y enriquece el DTO con el ID
     * autogenerado.
     */
    public boolean insert(JDBConnection connection, EmployeeDTO dto) {
        boolean success = false;
        String sql = "INSERT INTO employee ("
                + "rfc, curp, first_name, last_name1, last_name2, gender, birthdate, "
                + "personal_email, personal_number, street1, street2, inside_number, "
                + "outside_number, committee_id, status, last_employee_update, "
                + "date_register, date_end"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, dto.getRfc());
            pstmt.setString(2, dto.getCurp());
            pstmt.setString(3, dto.getFirst_name());
            pstmt.setString(4, dto.getLast_name1());
            pstmt.setString(5, dto.getLast_name2());
            pstmt.setInt(6, dto.getGender());
            setDateOrNull(pstmt, 7, dto.getBirthdate());

            pstmt.setString(8, dto.getPersonal_email());
            pstmt.setString(9, dto.getPersonal_number());
            pstmt.setInt(10, dto.getStreet1());
            pstmt.setInt(11, dto.getStreet2());
            pstmt.setString(12, dto.getInside_number());
            pstmt.setString(13, dto.getOutside_number());
            pstmt.setInt(14, dto.getCommittee_id());
            pstmt.setInt(15, dto.getStatus());
            pstmt.setInt(16, dto.getLast_employee_update());

            LocalDateTime registerDate = dto.getDate_register() != null ? dto.getDate_register() : LocalDateTime.now();
            pstmt.setTimestamp(17, Timestamp.valueOf(registerDate));
            setTimestampOrNull(pstmt, 18, dto.getDate_end());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }
        } catch (SQLException e) {
            System.getLogger(EmployeeDAO.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al insertar EmployeeDTO", e);
            success = false;
        }
        return success;
    }

    /**
     * Busca y retorna un registro de empleado por su ID.
     */
    public Optional<EmployeeDTO> get(JDBConnection connection, int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.getLogger(EmployeeDAO.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al consultar EmployeeDTO por id", e);
        }
        return Optional.empty();
    }

    private EmployeeDTO mapRow(ResultSet rs) throws SQLException {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(rs.getInt("id"));
        dto.setRfc(rs.getString("rfc"));
        dto.setCurp(rs.getString("curp"));
        dto.setFirst_name(rs.getString("first_name"));
        dto.setLast_name1(rs.getString("last_name1"));
        dto.setLast_name2(rs.getString("last_name2"));
        dto.setGender(rs.getInt("gender"));
        dto.setBirthdate(toLocalDate(rs.getDate("birthdate")));

        dto.setPersonal_email(rs.getString("personal_email"));
        dto.setPersonal_number(rs.getString("personal_number"));
        dto.setStreet1(rs.getInt("street1"));
        dto.setStreet2(rs.getInt("street2"));
        dto.setInside_number(rs.getString("inside_number"));
        dto.setOutside_number(rs.getString("outside_number"));
        dto.setCommittee_id(rs.getInt("committee_id"));
        dto.setStatus(rs.getInt("status"));
        dto.setLast_employee_update(rs.getInt("last_employee_update"));

        dto.setDate_update(toLocalDateTime(rs.getTimestamp("date_update")));
        dto.setDate_register(toLocalDateTime(rs.getTimestamp("date_register")));
        dto.setDate_end(toLocalDateTime(rs.getTimestamp("date_end")));
        return dto;
    }

    private void setDateOrNull(PreparedStatement pstmt, int index, LocalDate value) throws SQLException {
        if (value != null) {
            pstmt.setDate(index, Date.valueOf(value));
        } else {
            pstmt.setNull(index, Types.DATE);
        }
    }

    private void setTimestampOrNull(PreparedStatement pstmt, int index, LocalDateTime value) throws SQLException {
        if (value != null) {
            pstmt.setTimestamp(index, Timestamp.valueOf(value));
        } else {
            pstmt.setNull(index, Types.TIMESTAMP);
        }
    }

    private LocalDate toLocalDate(Date date) {
        return date != null ? date.toLocalDate() : null;
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
