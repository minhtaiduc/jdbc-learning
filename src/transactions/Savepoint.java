package transactions;

import java.sql.*;

public class Savepoint {
    static final String DB_URL = "jdbc:mysql://localhost/jdbc_learning";
    static final String USER = "root";
    static final String PASS = "1234";
    static final String QUERY = "SELECT id, first, last, age FROM Employees";
    static final String DELETE_QUERY = "DELETE FROM Employees WHERE ID = 8";
    static final String DELETE_QUERY_1 = "DELETE FROM Employees WHERE ID = 9";

    public static void printResultSet(ResultSet rs) throws SQLException{
        // Ensure we start with first row
        rs.beforeFirst();
        while(rs.next()){
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
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        ) {

            conn.setAutoCommit(false);
            ResultSet rs = stmt.executeQuery(QUERY);
            System.out.println("List result set for reference....");
            printResultSet(rs);

            // delete row having ID = 8
            // But save point before doing so.
            java.sql.Savepoint savepoint1 = conn.setSavepoint("ROWS_DELETED_1");
            System.out.println("Deleting row....");
            stmt.executeUpdate(DELETE_QUERY);
            // Rollback the changes after save point 1.
            conn.rollback(savepoint1);

            // delete rows having ID = 9
            // But save point before doing so.
            conn.setSavepoint("ROWS_DELETED_2");
            System.out.println("Deleting row....");

            stmt.executeUpdate(DELETE_QUERY_1);

            rs = stmt.executeQuery(QUERY);
            System.out.println("List result set for reference....");
            printResultSet(rs);

            // Clean-up environment
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
