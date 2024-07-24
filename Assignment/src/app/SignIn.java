package app;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignIn extends JFrame {
    private JTextField txtNic;
    private JPasswordField txtPassword;

    public SignIn() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sign In");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblNic = new JLabel("NIC:");
        lblNic.setBounds(10, 10, 80, 25);
        add(lblNic);

        txtNic = new JTextField();
        txtNic.setBounds(100, 10, 160, 25);
        add(txtNic);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(10, 50, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 50, 160, 25);
        add(txtPassword);

        JButton btnSignIn = new JButton("Sign In");
        btnSignIn.setBounds(10, 90, 250, 25);
        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nic = txtNic.getText();
                String password = new String(txtPassword.getPassword());

                User user = UserService.signIn(nic, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Sign In Successful");
                    new Menu(user).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Sign In Failed");
                }
            }
        });
        
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setBounds(10, 130, 250, 25);
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                    new SignUp().setVisible(true);
                    dispose();
               
            }
        });
        add(btnSignIn);
        add(btnSignUp);
        

        setLocationRelativeTo(null);
    }
}

