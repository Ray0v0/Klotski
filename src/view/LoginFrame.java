package view;

import control.Client;
import model.UserLib;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    JPanel panel = new JPanel(new GridBagLayout()); // 使用 GridBagLayout 更灵活地控制布局
    JLabel usernameLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");
    JTextField usernameField = new JTextField(15);
    JPasswordField passwordField = new JPasswordField(15);
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");

    public LoginFrame(Client client) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // 添加控件到面板中
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 1行2列，间距10px
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 占据两列
        panel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            client.login(new UserLib.User(username, password));
        });

        registerButton.addActionListener(e -> {
           String username = usernameField.getText();
           String password = String.valueOf(passwordField.getPassword());
           client.register(new UserLib.User(username, password));
        });

        setContentPane(panel);
        setVisible(true);
    }

    public void show(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
