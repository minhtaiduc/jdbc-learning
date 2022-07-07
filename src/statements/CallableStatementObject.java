package statements;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CallableStatementObject {
    static final String DB_URL = "jdbc:mysql://localhost/jdbc_learning";
    static final String USER = "root";
    static final String PASS = "1234";
    static final String QUERY = "{call getEmpName (?, ?)}";

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            CallableStatement stmt = conn.prepareCall(QUERY);
        ) {
            // Bind values into the parameters.
            stmt.setInt(1, 102);  // This would set ID
            // Because second parameter is OUT so register it
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            //Use execute method to run stored procedure.
            System.out.println("Executing stored procedure..." );
            stmt.execute();
            //Retrieve employee name with getXXX method
            String empName = stmt.getString(2);
            System.out.println("Emp Name with ID: 102 is " + empName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
