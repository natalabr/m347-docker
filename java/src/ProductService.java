import java.sql.*;
import java.util.Scanner;

public class ProductService {
    public static void showProducts() {
        String query = "SELECT productCode, productName, productLine, productVendor, quantityInStock, buyPrice FROM products";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.printf("%-15s %-50s %-20s %-30s %-10s %-10s%n",
                    "Code", "Name", "Line", "Vendor", "Stock", "Price");

            while (resultSet.next()) {
                System.out.printf("%-15s %-50s %-20s %-30s %-10d %-10.2f%n",
                        resultSet.getString("productCode"),
                        resultSet.getString("productName"),
                        resultSet.getString("productLine"),
                        resultSet.getString("productVendor"),
                        resultSet.getInt("quantityInStock"),
                        resultSet.getDouble("buyPrice"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showProductsByLine() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Geben Sie die Produktelinie ein (z. B. Classic Cars, Motorcycles): ");
        String productLine = scanner.nextLine();

        String query = "SELECT productCode, productName, productLine, productVendor, quantityInStock, buyPrice " +
                "FROM products WHERE productLine = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productLine);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.printf("%-15s %-50s %-20s %-30s %-10s %-10s%n",
                    "Code", "Name", "Line", "Vendor", "Stock", "Price");

            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;
                System.out.printf("%-15s %-50s %-20s %-30s %-10d %-10.2f%n",
                        resultSet.getString("productCode"),
                        resultSet.getString("productName"),
                        resultSet.getString("productLine"),
                        resultSet.getString("productVendor"),
                        resultSet.getInt("quantityInStock"),
                        resultSet.getDouble("buyPrice"));
            }

            if (!hasResults) {
                System.out.println("Keine Produkte in der angegebenen Produktelinie gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Produktcode: ");
        String productCode = scanner.nextLine();

        System.out.print("Produktname: ");
        String productName = scanner.nextLine();

        System.out.print("Produktlinie: ");
        String productLine = scanner.nextLine();

        System.out.print("Produktmassstab (z. B. '1:10'): ");
        String productScale = scanner.nextLine();

        System.out.print("Hersteller: ");
        String productVendor = scanner.nextLine();

        System.out.print("Produktbeschreibung: ");
        String productDescription = scanner.nextLine();

        System.out.print("Anzahl auf Lager: ");
        int quantityInStock = scanner.nextInt();

        System.out.print("Einkaufspreis: ");
        double buyPrice = scanner.nextDouble();

        System.out.print("Verkaufspreis (MSRP): ");
        double msrp = scanner.nextDouble();
        scanner.nextLine();

        String query = "INSERT INTO Products (productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productCode);
            preparedStatement.setString(2, productName);
            preparedStatement.setString(3, productLine);
            preparedStatement.setString(4, productScale);
            preparedStatement.setString(5, productVendor);
            preparedStatement.setString(6, productDescription);
            preparedStatement.setInt(7, quantityInStock);
            preparedStatement.setDouble(8, buyPrice);
            preparedStatement.setDouble(9, msrp);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Das Produkt wurde erfolgreich eingefügt.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Geben Sie den Produktcode des zu ändernden Produkts ein: ");
        String productCode = scanner.nextLine();

        String querySelect = "SELECT * FROM products WHERE productCode = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(querySelect)) {

            preparedStatement.setString(1, productCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String existingProductName = resultSet.getString("productName");
                String existingProductLine = resultSet.getString("productLine");
                String existingProductScale = resultSet.getString("productScale");
                String existingProductVendor = resultSet.getString("productVendor");
                String existingProductDescription = resultSet.getString("productDescription");
                int existingQuantityInStock = resultSet.getInt("quantityInStock");
                double existingBuyPrice = resultSet.getDouble("buyPrice");
                double existingMsrp = resultSet.getDouble("MSRP");

                System.out.print("Neuer Produktname (Enter für keine Änderung): " + existingProductName + " --> ");
                String productName = scanner.nextLine();
                if (productName.isEmpty()) productName = existingProductName;

                System.out.print("Neue Produktlinie (Enter für keine Änderung): " + existingProductLine + " --> ");
                String productLine = scanner.nextLine();
                if (productLine.isEmpty()) productLine = existingProductLine;

                System.out.print("Neuer Produktmassstab (Enter für keine Änderung): " + existingProductScale + " --> ");
                String productScale = scanner.nextLine();
                if (productScale.isEmpty()) productScale = existingProductScale;

                System.out.print("Neuer Hersteller (Enter für keine Änderung): " + existingProductVendor + " --> ");
                String productVendor = scanner.nextLine();
                if (productVendor.isEmpty()) productVendor = existingProductVendor;

                System.out.print("Neue Produktbeschreibung (Enter für keine Änderung): " + existingProductDescription + " --> ");
                String productDescription = scanner.nextLine();
                if (productDescription.isEmpty()) productDescription = existingProductDescription;

                System.out.print("Neue Anzahl auf Lager (Enter für keine Änderung): " + existingQuantityInStock + " --> ");
                String quantityInStockInput = scanner.nextLine();
                int quantityInStock = existingQuantityInStock;
                if (!quantityInStockInput.isEmpty()) {
                    quantityInStock = Integer.parseInt(quantityInStockInput);
                }

                System.out.print("Neuer Einkaufspreis (Enter für keine Änderung): " + existingBuyPrice + " --> ");
                String buyPriceInput = scanner.nextLine();
                double buyPrice = existingBuyPrice;
                if (!buyPriceInput.isEmpty()) {
                    buyPrice = Double.parseDouble(buyPriceInput);
                }

                System.out.print("Neuer Verkaufspreis (MSRP) (Enter für keine Änderung): " + existingMsrp + " --> ");
                String msrpInput = scanner.nextLine();
                double msrp = existingMsrp;
                if (!msrpInput.isEmpty()) {
                    msrp = Double.parseDouble(msrpInput);
                }

                String queryUpdate = "UPDATE Products SET productName = ?, productLine = ?, productScale = ?, productVendor = ?, " +
                        "productDescription = ?, quantityInStock = ?, buyPrice = ?, MSRP = ? WHERE productCode = ?";

                try (PreparedStatement preparedStatementUpdate = connection.prepareStatement(queryUpdate)) {
                    preparedStatementUpdate.setString(1, productName);
                    preparedStatementUpdate.setString(2, productLine);
                    preparedStatementUpdate.setString(3, productScale);
                    preparedStatementUpdate.setString(4, productVendor);
                    preparedStatementUpdate.setString(5, productDescription);
                    preparedStatementUpdate.setInt(6, quantityInStock);
                    preparedStatementUpdate.setDouble(7, buyPrice);
                    preparedStatementUpdate.setDouble(8, msrp);
                    preparedStatementUpdate.setString(9, productCode);

                    int rowsUpdated = preparedStatementUpdate.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Das Produkt wurde erfolgreich aktualisiert.");
                    } else {
                        System.out.println("Kein Produkt mit dem angegebenen Produktcode gefunden.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Kein Produkt mit dem angegebenen Produktcode gefunden.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Geben Sie den Produktcode des zu löschenden Produkts ein: ");
        String productCode = scanner.nextLine();

        String query = "DELETE FROM products WHERE productCode = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productCode);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Das Produkt wurde erfolgreich gelöscht.");
            } else {
                System.out.println("Kein Produkt mit dem angegebenen Produktcode gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
