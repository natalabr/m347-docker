import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Produkte anzeigen");
            System.out.println("2. Produkte einer Produktelinie anzeigen");
            System.out.println("3. Kunden anzeigen");
            System.out.println("4. Kunden suchen");
            System.out.println("5. Produkte einfügen");
            System.out.println("6. Produktedaten ändern");
            System.out.println("7. Produktedaten löschen");
            System.out.println("8. Bestellungen eines Kunden anzeigen");
            System.out.println("9. Bestellungsdetails anzeigen");
            System.out.println("10. Exit");
            System.out.print("Wählen Sie eine Option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    ProductService.showProducts();
                    break;
                case "2":
                    ProductService.showProductsByLine();
                    break;
                case "3":
                    CustomerService.showCustomers();
                    break;
                case "4":
                    CustomerService.searchCustomers();
                    break;
                case "5":
                    ProductService.insertProduct();
                    break;
                case "6":
                    ProductService.updateProduct();
                    break;
                case "7":
                    ProductService.deleteProduct();
                    break;
                case "8":
                    OrderService.showOrdersForCustomer();
                    break;
                case "9":
                    OrderService.showOrderDetails();
                    break;
                case "10":
                    System.out.println("Programm beendet.");
                    return;
                default:
                    System.out.println("Ungültige Option. Bitte erneut versuchen.");
            }
        }
    }
}
