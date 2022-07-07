package resultsets;

import java.sql.*;

public class NavigatingResultSet {
    static final String DB_URL = "jdbc:mysql://localhost/jdbc_learning";
    static final String USER = "root";
    static final String PASS = "1234";
    static final String QUERY = "SELECT id, first, last, age FROM Employees";

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            // Move cursor to the last row.
            System.out.println("Moving cursor to the last...");
            rs.last();

            // Extract data from result set
            System.out.println("Displaying record...");
            //Retrieve by column name
            int id  = rs.getInt("id");
            int age = rs.getInt("age");
            String first = rs.getString("first");
            String last = rs.getString("last");

            // Display values
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);

            // Move cursor to the first row.
            System.out.println("Moving cursor to the first row...");
            rs.first();

            // Extract data from result set
            System.out.println("Displaying record...");
            // Retrieve by column name
            id  = rs.getInt("id");
            age = rs.getInt("age");
            first = rs.getString("first");
            last = rs.getString("last");

            // Display values
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);
            // Move cursor to the first row.

            System.out.println("Moving cursor to the next row...");
            rs.next();

            // Extract data from result set
            System.out.println("Displaying record...");
            id  = rs.getInt("id");
            age = rs.getInt("age");
            first = rs.getString("first");
            last = rs.getString("last");

            // Display values
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
