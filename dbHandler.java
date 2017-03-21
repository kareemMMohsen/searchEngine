package searchengine;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbHandler {

    public Connection conn = null;

    public dbHandler() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/crawler?autoReconnect=true&useSSL=false";
            conn = DriverManager.getConnection(url, "root", "");
        } catch (SQLException e) {
             e.printStackTrace();
        } catch (ClassNotFoundException e) {
             e.printStackTrace();
        }
    }

    public ResultSet SqlQuery(String sql) throws SQLException {
        Statement sta = conn.createStatement();
        return sta.executeQuery(sql);
    }

    public boolean Sql(String sql) throws SQLException {
        Statement sta = conn.createStatement();
        return sta.execute(sql);
    }
   
}

