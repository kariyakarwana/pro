package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private User user;

    public Menu(User user) {
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Finance Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        JButton btnAddIncome = new JButton("Add Income");
        JButton btnAddExpense = new JButton("Add Expense");
        JButton btnTransaction = new JButton("Transaction");
        JButton btnAnalysis = new JButton("Analysis Report");
        JButton btnTransferMoney = new JButton("Transfer Money");

        btnAddIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Income(user).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Menu.this, "Failed to open Add Income window.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAddExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Expense(user).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Menu.this, "Failed to open Add Expense window.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Transaction(user).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Menu.this, "Failed to open Transaction window.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAnalysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Analysis(user).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Menu.this, "Failed to open Analysis Report window.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnTransferMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TransferMoney(user).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Menu.this, "Failed to open Transfer Money window.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(btnAddIncome);
        add(btnAddExpense);
        add(btnTransaction);
        add(btnAnalysis);
        add(btnTransferMoney);
        setLocationRelativeTo(null);
    }
}
