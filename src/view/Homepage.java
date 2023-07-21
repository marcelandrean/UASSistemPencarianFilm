package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.DatabaseController;
import model.SingletonUser;
import model.Todo;
import model.User;

public class Homepage {
    private User user;
    private ArrayList<Todo> todos;

    public Homepage() {
        user = SingletonUser.getInstance().getUser();
        todos = new DatabaseController().getAllTodos(user.getId());

        JFrame frame = new JFrame();
        frame.setTitle("Homepage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Profil pengguna
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Nama pengguna: " + user.getName());
        profilePanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        JLabel email = new JLabel("Email: " + user.getEmail());
        profilePanel.add(email, gbc);

        gbc.gridy = 2;
        JLabel gender = new JLabel("Gender: " + user.getGenderAsString());
        profilePanel.add(gender, gbc);

        gbc.gridy = 3;
        JLabel birthday = new JLabel("Birthday: " + user.getBirthday());
        profilePanel.add(birthday, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        String projectDirectory = System.getProperty("user.dir");
        String fotoPath = projectDirectory + "/images/" + user.getPhoto();
        ImageIcon fotoIcon = new ImageIcon(fotoPath);
        Image originalFoto = fotoIcon.getImage();

        double aspectRatio = (double) originalFoto.getWidth(null) / originalFoto.getHeight(null);
        double targetAspectRatio = 1.0 / 1.0;

        int newWidth, newHeight;

        if (aspectRatio > targetAspectRatio) {
            newWidth = 80;
            newHeight = (int) (80 / targetAspectRatio);
        } else {
            newWidth = (int) (80 * targetAspectRatio);
            newHeight = 80;
        }

        Image resizedImage = originalFoto.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel fotoLabel = new JLabel(resizedIcon);
        profilePanel.add(fotoLabel, gbc);
        frame.add(profilePanel, BorderLayout.NORTH);

        // Main panel untuk todo
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        int y = 0;
        int c = 1;
        gbc.gridheight = 1;
        for (Todo todo : todos) {
            y++;
            gbc.gridy = y;
            gbc.gridx = 0;
            JLabel countLabel = new JLabel("TODO " + c);
            mainPanel.add(countLabel, gbc);

            y++;
            gbc.gridy = y;
            gbc.anchor = GridBagConstraints.WEST;
            JLabel title = new JLabel("Title: " + todo.getTitle());
            mainPanel.add(title, gbc);

            y++;
            gbc.gridy = y;
            JLabel note = new JLabel("Note: " + todo.getNote());
            mainPanel.add(note, gbc);

            y++;
            gbc.gridy = y;
            mainPanel.add(new JLabel(), gbc); // space
            c++;
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        frame.add(scrollPane);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton logOutButton = new JButton("Log Out");
        buttonPanel.add(logOutButton);

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingletonUser.getInstance().reset();
                new LandingPage();
                frame.dispose();
            }
        });

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}