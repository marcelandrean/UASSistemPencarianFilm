package view;

import java.awt.Font;
import java.awt.GridBagConstraints;  
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LandingPage {
    public LandingPage() {
        JFrame frame = new JFrame("Landing Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Button Login
        JButton loginButton = new JButton("Login Pengguna");
        loginButton.setFont(loginButton.getFont().deriveFont(Font.BOLD, 18f));
        loginButton.setMargin(new Insets(10, 10, 10, 10));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginScreen();
                frame.dispose();
            }
        });
        frame.add(loginButton, gbc);

        // Button Registrasi
        gbc.gridy = 1;
        JButton regisButton = new JButton("Registrasi Pengguna Baru");
        regisButton.setFont(regisButton.getFont().deriveFont(Font.BOLD, 18f));
        regisButton.setMargin(new Insets(10, 10, 10, 10));
        regisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationScreen();
                frame.dispose();
            }
        });
        frame.add(regisButton, gbc);

        // Button Exit
        gbc.gridy = 2;
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(exitButton.getFont().deriveFont(Font.BOLD, 18f));
        exitButton.setMargin(new Insets(10, 10, 10, 10));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        frame.add(exitButton, gbc);

        frame.setVisible(true);
    }
}