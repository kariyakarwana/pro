package app;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Income extends JFrame {
    private User user;
    private JTextField txtDate, txtDescription, txtAmount;

    public Income(User user) {
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Add Income");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblDate = new JLabel("Date (YYYY-MM-DD):");
        lblDate.setBounds(10, 10, 120, 25);
        add(lblDate);

        txtDate = new JTextField();
        txtDate.setBounds(140, 10, 140, 25);
        add(txtDate);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setBounds(10, 50, 120, 25);
        add(lblDescription);

        txtDescription = new JTextField();
        txtDescription.setBounds(140, 50, 140, 25);
        add(txtDescription);

        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(10, 90, 120, 25);
        add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(140, 90, 140, 25);
        add(txtAmount);

        JButton btnAddIncome = new JButton("Add Income");
        btnAddIncome.setBounds(10, 130, 270, 25);
        btnAddIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = txtDate.getText();
                String description = txtDescription.getText();
                double amount = Double.parseDouble(txtAmount.getText());

                if (addIncomeToDatabase(date, description, amount)) {
                    JOptionPane.showMessageDialog(null, "Income Added Successfully");
                    new Menu(user).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Add Income");
                }
            }
        });
        
        JButton btnBack = new JButton("Back to Menu");
        btnBack.setBounds(10, 170, 270, 25);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new Menu(user).setVisible(true);
                    dispose();             
            }
        });
        add(btnAddIncome);
        add(btnBack);

        setLocationRelativeTo(null);
    }

    private boolean addIncomeToDatabase(String date, String description, double amount) {
        String sql = "INSERT INTO incomes (user_id, date, description, amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, date);
            pstmt.setString(3, description);
            pstmt.setDouble(4, amount);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

