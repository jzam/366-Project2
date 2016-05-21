import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBConnection connects to the database and handles all the queries for the 
 * other classes.
 *
 * @author Brian Fung
 * @author Kevin Yang
 * @author Justin Zaman
 */
public class DBConnection {
    public DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;
        }
    }

    public Connection getConnection() {
        java.sql.Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://cslvm74.csc.calpoly.edu:5432/bfung", "postgres",
                    "");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        return connection;
    }
    
    
    // Executes query string and returns the result.
    public ResultSet execQuery(String query) throws SQLException {
        Connection con = this.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        Statement statement = con.createStatement();

        ResultSet result = statement.executeQuery(query);
        con.close(); // Not sure if allowed to close connection before result.
        // MAKE SURE to close result after using it! result.close();
        return result;
    }
    
    // Executes an update on the DB. This can be for deletes and changing data.
    public void execUpdate(String query) throws SQLException{
        Connection con = this.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate(query);
        statement.close();
        con.commit();
        con.close();
    }
}