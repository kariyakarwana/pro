package app;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransferMoney extends JFrame {

    private User user;
    private JTextField receiverIdField;
    private JTextField descriptionField;
    private JTextField amountField;
    private JButton transferButton;
    private JButton backButton;

    public TransferMoney(User user) {
        this.user = user;
        setupUI();
    }

    private void setupUI() {
        setTitle("Transfer Money");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Custom panel to paint the background image and overlay
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("C:\\Users\\Sandaruan\\Desktop\\OOP 2 Assignment\\Assignment\\Assignment\\src\\images\\WhatsApp Image 2024-04-25 at 20.57.21_3ce570d7.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

                // Draw orange color overlay with 10% opacity
                Color orangeWithOpacity = new Color(255, 165, 0, 26); // 26 is 10% of 255
                g.setColor(orangeWithOpacity);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Define a larger font
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel receiverIdLabel = new JLabel("Receiver ID:");
        receiverIdLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(receiverIdLabel, gbc);

        receiverIdField = new JTextField();
        receiverIdField.setFont(textFieldFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        backgroundPanel.add(receiverIdField, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        backgroundPanel.add(descriptionLabel, gbc);

        descriptionField = new JTextField();
        descriptionField.setFont(textFieldFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        backgroundPanel.add(descriptionField, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        backgroundPanel.add(amountLabel, gbc);

        amountField = new JTextField();
        amountField.setFont(textFieldFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        backgroundPanel.add(amountField, gbc);

        transferButton = new JButton("Transfer");
        transferButton.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTransfer();
            }
        });
        backgroundPanel.add(transferButton, gbc);

        backButton = new JButton("Back to Menu");
        backButton.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu(user).setVisible(true);
                dispose();
            }
        });
        backgroundPanel.add(backButton, gbc);

        setContentPane(backgroundPanel);
        setVisible(true);
    }


    private void handleTransfer() {
        try {
            int receiverId = Integer.parseInt(receiverIdField.getText().trim());
            String description = descriptionField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());

            TransactionService transactionService = new TransactionService();
            boolean success = transactionService.transferMoney(user.getId(), receiverId, description, amount);

            if (success) {
                JOptionPane.showMessageDialog(this, "Transfer Successful");
                dispose(); // Close the window after successful transfer
            } else {
                JOptionPane.showMessageDialog(this, "Transfer Failed. Check Balance.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for Receiver ID and Amount.");
        }
    }
}
