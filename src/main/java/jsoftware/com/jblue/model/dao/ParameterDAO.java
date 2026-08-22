/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jsoftware.com.jblue.model.exp.DataAccesObjectException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class ParameterDAO {

    public boolean getParameter(JDBConnection connection, String name) throws SQLException, DataAccesObjectException {
        String query = "SELECT value, data_type FROM dev_parameters WHERE parameter = ? AND status = 1";
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new DataAccesObjectException("PARAMETRO INEXISTENTE");
                }
                if (!rs.getString(2).equalsIgnoreCase("BOOL")) {
                    throw new DataAccesObjectException("VALOR NO VALIDO");
                }
                return rs.getBoolean(1);
            }
        }
    }
}
