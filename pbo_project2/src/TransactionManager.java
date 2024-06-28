import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TransactionManager {
    public static void addTransaction() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan ID User: ");
        int userId = scanner.nextInt();
        System.out.print("Masukkan ID Produk: ");
        int productId = scanner.nextInt();
        System.out.print("Masukkan Jumlah: ");
        int quantity = scanner.nextInt();

        if (!isUserIdValid(userId)) {
            System.out.println("ID User tidak valid.");
            return;
        }

        if (!isProductIdValid(productId)) {
            System.out.println("ID Produk tidak valid.");
            return;
        }

        double price = getProductPrice(productId);
        double totalPrice = quantity * price;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Transactions (user_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);
            statement.setDouble(4, totalPrice);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Transaksi berhasil ditambahkan.");
            } else {
                System.out.println("Transaksi gagal ditambahkan.");
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan SQL: " + e.getMessage());
        }
    }

    private static boolean isUserIdValid(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM Users WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan SQL: " + e.getMessage());
        }
        return false;
    }

    private static boolean isProductIdValid(int productId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM Products WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan SQL: " + e.getMessage());
        }
        return false;
    }

    private static double getProductPrice(int productId) {
        double price = 0.0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT price FROM Products WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan SQL: " + e.getMessage());
        }
        return price;
    }

    public static void viewTransactions() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Transactions";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");
                double totalPrice = resultSet.getDouble("total_price");

                System.out.println("ID: " + id + ", User ID: " + userId + ", Product ID: " + productId + ", Quantity: " + quantity + ", Total Price: " + totalPrice);
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan SQL: " + e.getMessage());
        }
    }
}
