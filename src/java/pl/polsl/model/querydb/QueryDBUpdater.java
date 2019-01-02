package pl.polsl.model.querydb;

import java.sql.*;

public class QueryDBUpdater {

    public void updateData() {

        try {
            // loading the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
            return;
        }

        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "lab", "lab")) {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // Wysyłamy zapytanie do bazy danych
            ResultSet rs = statement.executeQuery("SELECT * FROM Dane");

            //adding a new row
            rs.moveToInsertRow();
            rs.updateInt("ID", 100);
            rs.updateString("nazwisko", "Sobieski");
            rs.updateString("imie", "Jan");
            rs.updateFloat("ocena", 0);
            rs.insertRow();
            rs.moveToCurrentRow();
            
            // Przeglądamy otrzymane wyniki zamieniając wszystkie listery nazwiska na duże
            while (rs.next()) {
                rs.updateString("nazwisko", rs.getString("nazwisko").toUpperCase());
                rs.updateRow();
            }

            rs.close();
            System.out.println("Data updated");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        QueryDBUpdater updateDataApp = new QueryDBUpdater();
        updateDataApp.updateData();
    }
}
