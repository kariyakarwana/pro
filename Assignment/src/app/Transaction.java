package app;

/**
 *
 * @author Sandaruwan
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Transaction extends JFrame {
    private User user;
    private JTable transactionTable;

    public Transaction(User user) {
        this.user = user;
        setupUI();
        initTransaction();
    }

    private void initTransaction() {
        // Implement the actual functionality here
        loadTransactions();
    }

    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());
        transactionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Menu(user).setVisible(true);
                dispose();
            }
        });
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(backButton, BorderLayout.WEST);
        panel.add(southPanel, BorderLayout.SOUTH);

        this.add(panel);
        this.setSize(600, 400); // Set the window size appropriately
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Center the window
    }

    private void loadTransactions() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Description", "Amount"}, 0);
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT date, description, amount FROM transactions WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String date = rs.getString("date");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                model.addRow(new Object[]{date, description, amount});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        transactionTable.setModel(model);
    }

    // Other methods related to transactions
}
