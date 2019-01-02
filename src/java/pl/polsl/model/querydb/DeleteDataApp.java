package pl.polsl.model.querydb;

import java.sql.*;

public class DeleteDataApp {

    public void deleteData() {
        
        try {
            // loading the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
            return;
        }

        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "lab", "lab")) {
            Statement statement = con.createStatement();
            // Usuwamy dane z tabeli
            statement.executeUpdate("DELETE FROM Dane WHERE nazwisko = 'Mickiewicz'");
            System.out.println("Data removed");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        DeleteDataApp deleteDataApp = new DeleteDataApp();
        deleteDataApp.deleteData();
    }
}
