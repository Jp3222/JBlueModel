/*
 * Copyright (C) 2023 jp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jsoftware.com.jblue.sys.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import jsoftware.com.jblue.model.constants.Const;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author jp
 */
public final class AppConfig {

    //
    public static final String DB_USER = "DB USER";
    public static final String DB_PASSWORD = "DB PASSWORD";
    public static final String DB_URL = "DB URL";
    public static final String DB_MOTOR = "DB MOTOR";
    public static final String DB_PORT = "DB PORT";
    public static final String DB_HOST = "DB HOST";
    public static final String DB_NAME = "DB NAME";
    public static final String DB_UUID = "DB UUID";
    public static final String[] DB_KEYS = {
        DB_USER, DB_PASSWORD, DB_HOST, DB_MOTOR, DB_NAME, DB_PORT, DB_UUID
    };
    //
    public static final String TITLE1 = "TITLE 1";
    public static final String TITLE2 = "TITLE 2";
    public static final String LOGIN_ICON = "LOGIN ICON";
    //
    public static final String HOUR_OPEN = "HOUR OPEN";
    public static final String HOUR_CLOSE = "HOUR CLOSE";

    public static String getMaterUser(JDBConnection connection) throws SQLException {
        return String.valueOf(getParameter(connection, "USUARIO_MAESTRO"));
    }

    public static String getMaterPassword(JDBConnection connection) throws SQLException {
        return String.valueOf(getParameter(connection, "CONTRASEÑA_MAESTRA"));
    }

    public static LocalTime getOpenHour(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "HORA_DE_APERTURA");
        if (valueOf == null) {
            return null;
        }
        String hour = String.valueOf(valueOf);
        return LocalTime.parse(hour, DateTimeFormatter.ofPattern("kk:mm:ss"));
    }

    public static LocalTime getCloseHour(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "HORA_DE_CIERRE");
        if (valueOf == null) {
            return null;
        }
        String hour = String.valueOf(valueOf);
        return LocalTime.parse(hour, DateTimeFormatter.ofPattern("kk:mm:ss"));
    }

    public static boolean isWorkTime(JDBConnection connection) throws SQLException {
        LocalTime a = getOpenHour(connection),
                b = getCloseHour(connection),
                o = LocalTime.now();

        if (a == null || b == null) {
            return false;
        }
        boolean hour_validate = isHourValidate(connection);
        if (hour_validate) {
            return o.isAfter(a) && o.isBefore(b);
        }
        return true;
    }

    public static int getPayDay(JDBConnection connection) throws SQLException {
        Object parameter = getParameter(connection, "ULTIMO_DIA_DE_PAGO");
        if (parameter == null) {
            return -1;
        }
        return Integer.parseInt(String.valueOf(parameter));
    }

    public static boolean isPayDay(JDBConnection connection) throws SQLException {
        LocalDate o = LocalDate.now();
        if (getPayDay(connection) <= 0) {
            return false;
        }
        return getPayDay(connection) > o.getDayOfMonth();
    }

    public static boolean isAutoPay(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "COBRO_AUTOMATICO");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isHourValidate(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "VALIDAR_HORA_DE_ENTRADA");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isDevMessages(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "MENSAJES_DEV");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isDbMessages(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "MENSAJES_DB");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isTestMessages(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "MENSAJES_TEST");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isDevFunction(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "FUNCIONES_DEV");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isTestFunction(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "FUNCIONES_TEST");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isLogsDev(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "LOGS_DEV");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isLogsTest(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "LOGS_TEST");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static boolean isLogsDB(JDBConnection connection) throws SQLException {
        Object valueOf = getParameter(connection, "LOGS_DB");
        if (valueOf == null) {
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(valueOf));
    }

    public static LocalDate getReferenceDate(JDBConnection connection) throws SQLException {
        Object value = getParameter(connection, "FECHA_DE_INICIO");
        if (value == null) {
            return LocalDate.now();
        }
        return LocalDate.parse((CharSequence) value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDate getDataBaseDate(JDBConnection connection) throws SQLException {
        Object value = getParameter(connection, "FECHA_DEL_PROGRAMA");
        if (value == null) {
            return LocalDate.now();
        }
        return LocalDate.parse((CharSequence) value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static boolean getParameterBoolean(JDBConnection connection, String name) throws SQLException {
        Boolean res = Boolean.valueOf((String) getParameter(connection, name));
        if (res == null) {
            return Boolean.FALSE;
        }
        return res;
    }

    public static int getParameterInt(JDBConnection connection, String name) throws SQLException {
        return (int) getParameter(connection, name);
    }

    public static String getParameterString(JDBConnection connection, String name) throws SQLException {
        return (String) getParameter(connection, name);
    }

    private static Object getParameter(JDBConnection connection, String name) throws SQLException {
        String query = "SELECT value, data_type FROM %s WHERE parameter = '%s' AND status = 1"
                .formatted(Const.DEV_PARAMETERS_TABLE.getTableName(), name);
        ResultSet rs = connection.query(query);
        if (rs.next()) {
            return rs.getObject(1);
        }
        return null;
    }

    private AppConfig() {

    }
}
