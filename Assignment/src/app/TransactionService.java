package app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionService {

    public boolean transferMoney(int senderId, int receiverId, String description, double amount) {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Check if sender has sufficient balance
            BigDecimal senderBalance = getUserBalance(senderId);
            if (senderBalance.compareTo(BigDecimal.valueOf(amount)) < 0) {
                conn.rollback();
                return false; // Insufficient balance
            }

            // Deduct amount from sender's account
            String deductQuery = "INSERT INTO transactions (user_id, receiver_id, date, description, amount) VALUES (?, ?, NOW(), ?, ?)";
            try (PreparedStatement deductStmt = conn.prepareStatement(deductQuery)) {
                deductStmt.setInt(1, senderId);
                deductStmt.setInt(2, receiverId);
                deductStmt.setString(3, description);
                deductStmt.setBigDecimal(4, BigDecimal.valueOf(-amount));
                deductStmt.executeUpdate();
            }

            // Add amount to receiver's account
            String addQuery = "INSERT INTO transactions (user_id, receiver_id, date, description, amount) VALUES (?, ?, NOW(), ?, ?)";
            try (PreparedStatement addStmt = conn.prepareStatement(addQuery)) {
                addStmt.setInt(1, receiverId);
                addStmt.setInt(2, senderId);
                addStmt.setString(3, description);
                addStmt.setBigDecimal(4, BigDecimal.valueOf(amount));
                addStmt.executeUpdate();
            }

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
