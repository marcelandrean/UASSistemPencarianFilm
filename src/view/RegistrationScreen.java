package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controller.DatabaseController;
import model.User;

public class RegistrationScreen {

    public RegistrationScreen() {
        JFrame frame = new JFrame();
        frame.setTitle("Registrasi Pengguna Baru");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel emailLabel = new JLabel("Email:");
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JTextField emailField = new JTextField();
        formPanel.add(emailField, gbc);

        // Nama
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel nameLabel = new JLabel("Nama:");
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JTextField nameField = new JTextField();
        formPanel.add(nameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        JLabel genderLabel = new JLabel("Jenis kelamin:");
        formPanel.add(genderLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        JRadioButton maleRadioButton = new JRadioButton("Pria");
        maleRadioButton.setActionCommand("0");
        JRadioButton femaleRadioButton = new JRadioButton("Wanita");
        femaleRadioButton.setActionCommand("1");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        formPanel.add(genderPanel, gbc);

        // Birthday
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        JLabel birthdayLabel = new JLabel("Tanggal ulang tahun:");
        formPanel.add(birthdayLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JDateChooser birthdayChooser = new JDateChooser();
        formPanel.add(birthdayChooser, gbc);

        // File photo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        JLabel photoLabel = new JLabel("File photo:");
        formPanel.add(photoLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JFileChooser photoChooser = new JFileChooser();
        formPanel.add(photoChooser, gbc);

        // Button
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setPreferredSize(new Dimension(500, buttonPanel.getPreferredSize().height));

        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton, gbc);

        JButton regisButton = new JButton("Registrasi");
        buttonPanel.add(regisButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LandingPage();
                frame.dispose();
            }
        });

        regisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                boolean isPassValid = checkPassword(password);

                if (isPassValid) {
                    saveFile(photoChooser);

                    Date selectedDate = birthdayChooser.getDate();

                    // Convert the Date to LocalDate
                    Instant instant = selectedDate.toInstant();
                    ZoneId zoneId = ZoneId.systemDefault();
                    LocalDate localDate = instant.atZone(zoneId).toLocalDate();

                    User user = new User(
                            0,
                            nameField.getText(),
                            emailField.getText(),
                            password,
                            Integer.parseInt(genderGroup.getSelection().getActionCommand()),
                            localDate,
                            photoChooser.getSelectedFile().getName());
                    new DatabaseController().insertNewUser(user);
                    JOptionPane.showMessageDialog(null, "Registrasi berhasil.");

                    frame.dispose();
                    new LoginScreen();
                } else {
                    JOptionPane.showMessageDialog(null, "Registrasi gagal.", "Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formPanel.add(buttonPanel, gbc);
        frame.add(formPanel);
        frame.setVisible(true);
    }

    private boolean checkPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

    private boolean saveFile(JFileChooser file) {
        File originFile = file.getSelectedFile();
        String fileName = originFile.getName();
        File destinationFile = new File(System.getProperty("user.dir") + "/images/" + fileName);
        try {
            Files.copy(originFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}