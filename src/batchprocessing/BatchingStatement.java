package batchprocessing;

import java.sql.*;

public class BatchingStatement {
    static final String DB_URL = "jdbc:mysql://localhost/jdbc_learning";
    static final String USER = "root";
    static final String PASS = "1234";

    public static void printResultSet(ResultSet rs) throws SQLException {
        rs.beforeFirst();
        while (rs.next()) {
            // Display values
            System.out.print("ID: " + rs.getInt("id"));
            System.out.print(", Age: " + rs.getInt("age"));
            System.out.print(", First: " + rs.getString("first"));
            System.out.println(", Last: " + rs.getString("last"));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_UPDATABLE)
        ) {
            conn.setAutoCommit(false);

            ResultSet rs = stmt.executeQuery("Select * from Employees");
            printResultSet(rs);

            // Create SQL statement
            String SQL = "INSERT INTO Employees (first, last, age) VALUES('Zia', 'Ali', 30)";
            // Add above SQL statement in the batch.
            stmt.addBatch(SQL);

            // Create one more SQL statement
            SQL = "INSERT INTO Employees (first, last, age) VALUES('Raj', 'Kumar', 35)";
            // Add above SQL statement in the batch
            stmt.addBatch(SQL);

            // Create one more SQL statement
            SQL = "UPDATE Employees SET age = 35 WHERE id = 7";
            // Add above SQL statement in the batch.
            stmt.addBatch(SQL);

            // Create an int[] to hold returned values
            int[] count = stmt.executeBatch();

            //Explicitly commit statements to apply changes
            conn.commit();

            rs = stmt.executeQuery("Select * from Employees");
            printResultSet(rs);

            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
