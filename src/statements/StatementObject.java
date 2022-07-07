package statements;

import java.sql.*;

public class StatementObject {
    static final String DB_URL = "jdbc:mysql://localhost/jdbc_learning";
    static final String USER = "root";
    static final String PASS = "1234";
    static final String QUERY = "SELECT id, first, last, age FROM Employees";
    static final String UPDATE_QUERY = "UPDATE Employees set age=30 WHERE id=103";

    public static void main(String[] args) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
        ) {
            // Check if it returns a true Result Set or not.
            Boolean ret = stmt.execute(UPDATE_QUERY);
            System.out.println("Return value is : " + ret.toString());

            // Update age of the record with ID = 103;
            int rows = stmt.executeUpdate(UPDATE_QUERY);
            System.out.println("Rows impacted : " + rows);

            // Let us select all the records and display them.
            ResultSet rs = stmt.executeQuery(QUERY);

            // Extract data from result set
            while (rs.next()) {
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Age: " + rs.getInt("age"));
                System.out.print(", First: " + rs.getString("first"));
                System.out.println(", Last: " + rs.getString("last"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
