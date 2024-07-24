package app;

import app.Database;
import app.User;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public static boolean signUp(User user) {
        String sql = "INSERT INTO users (name, nic, password) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getNic());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User signIn(String nic, String password) {
        String sql = "SELECT * FROM users WHERE nic = ? AND password = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nic);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("nic"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getUserBalance(int userId) {
        BigDecimal balance = BigDecimal.ZERO;

        try (Connection conn = Database.getConnection()) {
            // Calculate total income from the incomes table
            String incomeQuery = "SELECT COALESCE(SUM(amount), 0) FROM incomes WHERE user_id = ?";
            try (PreparedStatement incomeStmt = conn.prepareStatement(incomeQuery)) {
                incomeStmt.setInt(1, userId);
                try (ResultSet incomeRs = incomeStmt.executeQuery()) {
                    if (incomeRs.next()) {
                        balance = balance.add(incomeRs.getBigDecimal(1));
                    }
                }
            }

            // Calculate total expenses from the expenses table
            String expensesQuery = "SELECT COALESCE(SUM(amount), 0) FROM expenses WHERE user_id = ?";
            try (PreparedStatement expensesStmt = conn.prepareStatement(expensesQuery)) {
                expensesStmt.setInt(1, userId);
                try (ResultSet expensesRs = expensesStmt.executeQuery()) {
                    if (expensesRs.next()) {
                        balance = balance.subtract(expensesRs.getBigDecimal(1));
                    }
                }
            }

            // Calculate total transfers sent (where user_id is sender and receiver_id is not NULL)
            String transfersSentQuery = "SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE user_id = ? AND receiver_id IS NOT NULL";
            try (PreparedStatement transfersSentStmt = conn.prepareStatement(transfersSentQuery)) {
                transfersSentStmt.setInt(1, userId);
                try (ResultSet transfersSentRs = transfersSentStmt.executeQuery()) {
                    if (transfersSentRs.next()) {
                        balance = balance.subtract(transfersSentRs.getBigDecimal(1));
                    }
                }
            }

            // Calculate total transfers received (where user_id is receiver)
            String transfersReceivedQuery = "SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE receiver_id = ?";
            try (PreparedStatement transfersReceivedStmt = conn.prepareStatement(transfersReceivedQuery)) {
                transfersReceivedStmt.setInt(1, userId);
                try (ResultSet transfersReceivedRs = transfersReceivedStmt.executeQuery()) {
                    if (transfersReceivedRs.next()) {
                        balance = balance.add(transfersReceivedRs.getBigDecimal(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

}
