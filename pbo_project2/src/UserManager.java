import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserManager {
    public static void addUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan Username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan No Telepon: ");
        String noTelepon = scanner.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Users (username, no_telepon) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, noTelepon);
            statement.executeUpdate();

            System.out.println("User berhasil ditambahkan.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
