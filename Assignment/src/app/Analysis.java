package app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Analysis extends JFrame {
    private User user;

    public Analysis(User user) {
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Analysis Report");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Object[]> tableData = new ArrayList<>();

        double totalIncome = 0.0;
        double totalExpenses = 0.0;
        double totalTransactions = 0.0;

        try (Connection conn = Database.getConnection()) {
            // Query to get income data
            String incomeQuery = "SELECT date, description, SUM(amount) AS total FROM incomes WHERE user_id = ? GROUP BY date, description";
            try (PreparedStatement incomeStmt = conn.prepareStatement(incomeQuery)) {
                incomeStmt.setInt(1, user.getId());
                ResultSet incomeRs = incomeStmt.executeQuery();
                while (incomeRs.next()) {
                    String date = incomeRs.getDate("date").toString();
                    String description = incomeRs.getString("description");
                    double total = incomeRs.getDouble("total");
                    dataset.addValue(total, "Income", date);
                    tableData.add(new Object[]{date, "Income", description, total});
                    totalIncome += total;
                }
            }

            // Query to get expense data
            String expenseQuery = "SELECT date, description, SUM(amount) AS total FROM expenses WHERE user_id = ? GROUP BY date, description";
            try (PreparedStatement expenseStmt = conn.prepareStatement(expenseQuery)) {
                expenseStmt.setInt(1, user.getId());
                ResultSet expenseRs = expenseStmt.executeQuery();
                while (expenseRs.next()) {
                    String date = expenseRs.getDate("date").toString();
                    String description = expenseRs.getString("description");
                    double total = expenseRs.getDouble("total");
                    dataset.addValue(total, "Expense", date);
                    tableData.add(new Object[]{date, "Expense", description, total});
                    totalExpenses += total;
                }
            }

            // Query to get transaction data
            String transactionQuery = "SELECT date, description, amount FROM transactions WHERE user_id = ? AND receiver_id IS NOT NULL";
            try (PreparedStatement transactionStmt = conn.prepareStatement(transactionQuery)) {
                transactionStmt.setInt(1, user.getId());
                ResultSet transactionRs = transactionStmt.executeQuery();
                while (transactionRs.next()) {
                    String date = transactionRs.getDate("date").toString();
                    String description = transactionRs.getString("description");
                    double total = transactionRs.getDouble("amount");
                    dataset.addValue(total, "Transaction", date);
                    tableData.add(new Object[]{date, "Transaction", description, total});
                    totalTransactions += total;
                }
            }

            // Query to get last account balance
            String balanceQuery = "SELECT balance FROM accounts WHERE user_id = ? ORDER BY date DESC LIMIT 1";
            try (PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery)) {
                balanceStmt.setInt(1, user.getId());
                ResultSet balanceRs = balanceStmt.executeQuery();
                if (balanceRs.next()) {
                    double lastBalance = balanceRs.getDouble("balance");
                    tableData.add(new Object[]{"Last Balance", "", "", lastBalance});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JFreeChart chart = ChartFactory.createLineChart("Income and Expense Analysis", "Date", "Amount", dataset);
        ChartPanel chartPanel = new ChartPanel(chart);

        String[] columnNames = {"Date", "Type", "Description", "Amount"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Object[] row : tableData) {
            tableModel.addRow(row);
        }
        JTable dataTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(dataTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanel, tableScrollPane);
        splitPane.setDividerLocation(400);

        JPanel totalsPanel = new JPanel(new GridLayout(4, 2));
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        totalsPanel.add(new JLabel("Total Income: "));
        totalsPanel.add(new JLabel(String.format("%.2f", totalIncome)));

        totalsPanel.add(new JLabel("Total Expenses: "));
        totalsPanel.add(new JLabel(String.format("%.2f", totalExpenses)));

        totalsPanel.add(new JLabel("Total Transactions: "));
        totalsPanel.add(new JLabel(String.format("%.2f", totalTransactions)));

        add(splitPane, BorderLayout.CENTER);
        add(totalsPanel, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Back to Menu");
        btnBack.addActionListener(e -> {
            new Menu(user).setVisible(true);
            dispose();
        });
        add(btnBack, BorderLayout.NORTH);

        setLocationRelativeTo(null);
    }
}
