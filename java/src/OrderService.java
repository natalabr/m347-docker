import java.sql.*;
import java.util.Scanner;

public class OrderService {
    public static void showOrdersForCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Geben Sie die Kundennummer ein: ");
        int customerNumber = scanner.nextInt();
        scanner.nextLine();

        String query = "SELECT c.customerName, o.customerNumber, o.orderNumber, o.orderDate, o.status, " +
                "o.requiredDate, o.shippedDate, o.comments " +
                "FROM orders o " +
                "JOIN customers c ON o.customerNumber = c.customerNumber " +
                "WHERE o.customerNumber = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.printf("%-30s %-15s %-15s %-15s %-15s %-15s %-15s %-30s%n",
                    "Customer Name", "Customer No", "Order No", "Order Date", "Status",
                    "Required Date", "Shipped Date", "Comments");

            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;
                System.out.printf("%-30s %-15d %-15d %-15s %-15s %-15s %-15s %-30s%n",
                        resultSet.getString("customerName"),
                        resultSet.getInt("customerNumber"),
                        resultSet.getInt("orderNumber"),
                        resultSet.getDate("orderDate"),
                        resultSet.getString("status"),
                        resultSet.getDate("requiredDate"),
                        resultSet.getDate("shippedDate"),
                        resultSet.getString("comments") != null ? resultSet.getString("comments") : "N/A");
            }

            if (!hasResults) {
                System.out.println("Keine Bestellungen für diesen Kunden gefunden.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showOrderDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Geben Sie die Bestellnummer ein: ");
        int orderNumber = scanner.nextInt();
        scanner.nextLine();

        String query = "SELECT o.orderNumber, od.productCode, p.productName, p.productLine, p.productVendor, " +
                "od.quantityOrdered, od.priceEach " +
                "FROM orderdetails od " +
                "JOIN products p ON od.productCode = p.productCode " +
                "JOIN orders o ON od.orderNumber = o.orderNumber " +
                "WHERE o.orderNumber = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.printf("%-15s %-15s %-50s %-20s %-30s %-15s %-15s%n",
                    "Order No", "Product Code", "Product Name", "Product Line",
                    "Product Vendor", "Quantity", "Price Each");

            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;
                System.out.printf("%-15d %-15s %-50s %-20s %-30s %-15d %-15.2f%n",
                        resultSet.getInt("orderNumber"),
                        resultSet.getString("productCode"),
                        resultSet.getString("productName"),
                        resultSet.getString("productLine"),
                        resultSet.getString("productVendor"),
                        resultSet.getInt("quantityOrdered"),
                        resultSet.getDouble("priceEach"));
            }

            if (!hasResults) {
                System.out.println("Keine Details für diese Bestellung gefunden.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
