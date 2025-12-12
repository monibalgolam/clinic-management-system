
package db;

import java.sql.*;

public class DB {
    
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost/clinic_db";
        String user = "root";
        String pass = "";

        return DriverManager.getConnection(url, user, pass);
    
    
}
}

