package app;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame {
    private JTextField txtName, txtNic, txtPassword, txtConfirmPassword;

    public SignUp() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sign Up");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(10, 10, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(100, 10, 160, 25);
        add(txtName);

        JLabel lblNic = new JLabel("NIC:");
        lblNic.setBounds(10, 50, 80, 25);
        add(lblNic);

        txtNic = new JTextField();
        txtNic.setBounds(100, 50, 160, 25);
        add(txtNic);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(10, 90, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 90, 160, 25);
        add(txtPassword);

        JLabel lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setBounds(10, 130, 120, 25);
        add(lblConfirmPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(140, 130, 120, 25);
        add(txtConfirmPassword);

        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setBounds(10, 170, 250, 25);
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String nic = txtNic.getText();
                String password = new String(((JPasswordField) txtPassword).getPassword());
                String confirmPassword = new String(((JPasswordField) txtConfirmPassword).getPassword());

                if (password.equals(confirmPassword)) {
                    User user = new User(name, nic, password);
                    if (UserService.signUp(user)) {
                        JOptionPane.showMessageDialog(null, "Sign Up Successful");
                        new SignIn().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Sign Up Failed");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match");
                }
            }
        });
        JButton btnSignIn = new JButton("Sign In");
        btnSignIn.setBounds(10, 200, 250, 25);
        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        new SignIn().setVisible(true);
                        dispose();
                    
            }
        });
        add(btnSignUp);
        add(btnSignIn);

        setLocationRelativeTo(null);
    }
}

