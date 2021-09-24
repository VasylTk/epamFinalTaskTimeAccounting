package com.timeaccounting.DB;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

public class DBManager {

    private static DBManager instance;
    public DataSource ds;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            ds = (DataSource) new InitialContext()
                    .lookup("java:/comp/env/jdbc/timeaccounting");
        } catch (NamingException ex) {
            throw new IllegalStateException("Cannot init com.timeaccounting.DB.DBManager", ex);
        }
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
