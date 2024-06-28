import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductManager {
    public static void addProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan Nama Produk: ");
        String name = scanner.nextLine();
        System.out.print("Masukkan Harga: ");
        double price = scanner.nextDouble();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Products (name, price) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.executeUpdate();

            System.out.println("Produk berhasil ditambahkan.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
