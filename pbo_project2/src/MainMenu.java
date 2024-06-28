import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Tambah User");
            System.out.println("2. Tambah Produk");
            System.out.println("3. Lakukan Transaksi");
            System.out.println("4. Lihat Transaksi");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    UserManager.addUser();
                    break;
                case 2:
                    ProductManager.addProduct();
                    break;
                case 3:
                    TransactionManager.addTransaction();
                    break;
                case 4:
                    TransactionManager.viewTransactions();
                    break;
                case 5:
                    System.out.println("Keluar...");
                    break;
                default:
                    System.out.println("Opsi tidak valid!");
            }
        } while (choice != 5);
        
        scanner.close();
    }
}
