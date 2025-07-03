import java.sql.*;
import java.util.Scanner;

public class CustomerService {
    public static void showCustomers() {
        String query = "SELECT customerNumber, customerName, contactLastName, contactFirstName, postalCode, city, state FROM customers";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.printf("%-15s %-40s %-20s %-20s %-10s %-20s %-20s%n",
                    "Customer No", "Name", "Last Name", "First Name", "Postal Code", "City", "State");

            while (resultSet.next()) {
                System.out.printf("%-15d %-40s %-20s %-20s %-10s %-20s %-20s%n",
                        resultSet.getInt("customerNumber"),
                        resultSet.getString("customerName"),
                        resultSet.getString("contactLastName"),
                        resultSet.getString("contactFirstName"),
                        resultSet.getString("postalCode"),
                        resultSet.getString("city"),
                        resultSet.getString("state") != null ? resultSet.getString("state") : "N/A");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchCustomers() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Geben Sie die Anfangsbuchstaben des Kundennamens ein: ");
        String namePrefix = scanner.nextLine();

        String query = "SELECT customerNumber, customerName, contactLastName, contactFirstName, postalCode, city, state " +
                "FROM customers WHERE customerName LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, namePrefix + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.printf("%-15s %-40s %-20s %-20s %-10s %-20s %-20s%n",
                    "Customer No", "Name", "Last Name", "First Name", "Postal Code", "City", "State");

            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;
                System.out.printf("%-15d %-40s %-20s %-20s %-10s %-20s %-20s%n",
                        resultSet.getInt("customerNumber"),
                        resultSet.getString("customerName"),
                        resultSet.getString("contactLastName"),
                        resultSet.getString("contactFirstName"),
                        resultSet.getString("postalCode"),
                        resultSet.getString("city"),
                        resultSet.getString("state") != null ? resultSet.getString("state") : "N/A");
            }

            if (!hasResults) {
                System.out.println("Keine Kunden mit diesen Anfangsbuchstaben gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
