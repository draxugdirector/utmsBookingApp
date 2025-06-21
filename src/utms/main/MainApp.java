
package utms.main;

import utms.db.DatabaseConnector;
import utms.model.*;
import java.sql.*;
import java.util.Scanner;

public class MainApp {
    static Scanner scanner = new Scanner(System.in);
    static Users currentUser;

    public static void main(String[] args) {
        DatabaseConnector.initialize();
         System.out.println("=== Welcome to Victoria University Transport Management System ===");
        loginMenu();
    }

    private static void loginMenu() {
        System.out.print("Enter your username: ");
        String name = scanner.nextLine();

        System.out.println("Select your role:\n1. Student\n2. Lecturer\n3. Transport Officer");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> currentUser = new Student(name);
            case 2 -> currentUser = new Lecturer(name);
            case 3 -> currentUser = new TransportOfficer(name);
            default -> { System.out.println("Invalid role."); return; }
        }

        mainMenu();
    }

    private static void mainMenu() {
        int choice;
        do {
            System.out.println("\n1. Book a Seat\n2. View Bookings\n3. Cancel Booking\n0. Exit");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> bookSeat();
                case 2 -> viewBookings();
                case 3 -> cancelBooking();
            }
        } while (choice != 0);
    }

    private static void bookSeat() {
        System.out.println("Choose Vehicle:\n1. Bus\n2. Van");
        int vehicleChoice = Integer.parseInt(scanner.nextLine());

        String vehicle = (vehicleChoice == 1) ? "Bus" : "Van";

        System.out.println("Select Time:\n1. Morning\n2. Afternoon\n3. Evening");
        String[] times = {"Morning", "Afternoon", "Evening"};
        String time = times[Integer.parseInt(scanner.nextLine()) - 1];

        System.out.print("Enter desired seat number: ");
        int seat = Integer.parseInt(scanner.nextLine());

        try (Connection conn = DatabaseConnector.connect()) {
            String sql = "INSERT INTO bookings (username, vehicle, time, seat) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, currentUser.getUsername());
                pstmt.setString(2, vehicle);
                pstmt.setString(3, time);
                pstmt.setInt(4, seat);
                pstmt.executeUpdate();
                System.out.println("Booking successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewBookings() {
        try (Connection conn = DatabaseConnector.connect()) {
            String sql = "SELECT * FROM bookings WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, currentUser.getUsername());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.printf("Booking: Vehicle=%s, Time=%s, Seat=%d\n",
                        rs.getString("vehicle"), rs.getString("time"), rs.getInt("seat"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cancelBooking() {
        System.out.print("Enter seat number to cancel: ");
        int seat = Integer.parseInt(scanner.nextLine());

        try (Connection conn = DatabaseConnector.connect()) {
            String sql = "DELETE FROM bookings WHERE username = ? AND seat = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, currentUser.getUsername());
                pstmt.setInt(2, seat);
                int rows = pstmt.executeUpdate();
                if (rows > 0) System.out.println("Booking cancelled.");
                else System.out.println("No booking found for that seat.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}