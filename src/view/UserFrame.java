package view;

import control.Client;
import model.UserLib;

import javax.swing.*;
import java.awt.*;

public class UserFrame extends JFrame {

    UserLib.User user;
    JButton singleBtn = new MyButton("🎮 单人模式");
    JButton coopBtn = new MyButton("🤝 联机合作");
    JButton versusBtn = new MyButton("⚔ 联机对战");
    JButton modifyPasswordBtn = new MyButton("修改密码");
    JButton deleteUserBtn = new MyButton("删除用户");
    JLabel introLabel = new MyLabel("游戏模式");
    JLabel usernameLabel;
    JButton settingsBtn = new JButton("...");
    JButton backBtn = new JButton("⬅");
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel mainPanel = new JPanel();
    JPanel container = new JPanel(new BorderLayout());

    public UserFrame(Client client, UserLib.User user) {
        this.user = user;
        usernameLabel = new MyLabel(user.username);
        setTitle("Klotski");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 距离底边 & 右边

        settingsBtn.setPreferredSize(new Dimension(60, 60));
        settingsBtn.setFocusPainted(false);
        settingsBtn.setContentAreaFilled(false);
        settingsBtn.setBorderPainted(false);

        backBtn.setPreferredSize(new Dimension(60, 60));
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(true);
        backBtn.setBorderPainted(false);

        deleteUserBtn.setForeground(Color.RED);

        // 主容器
        container.add(topPanel, BorderLayout.NORTH);
        container.add(mainPanel, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(container);

        // 按钮事件
        singleBtn.addActionListener(e -> {
            // 进入单人模式逻辑
        });

        coopBtn.addActionListener(e -> {
            // 进入联机合作逻辑
        });

        versusBtn.addActionListener(e -> {
            // 进入联机对战逻辑
        });

        settingsBtn.addActionListener(e -> {
            settingsPage();
        });

        backBtn.addActionListener(e -> {
            mainPage();
        });

        mainPage();
        setVisible(true);
    }

    private void mainPage() {
        topPanel.removeAll();
        mainPanel.removeAll();
        bottomPanel.removeAll();

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(introLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(singleBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(coopBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(versusBtn);

        bottomPanel.add(settingsBtn, BorderLayout.EAST);
        fresh();
    }

    private void settingsPage() {
        topPanel.removeAll();
        mainPanel.removeAll();
        bottomPanel.removeAll();

        topPanel.add(backBtn, BorderLayout.WEST);
        mainPanel.add(usernameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(modifyPasswordBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(deleteUserBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        fresh();
    }

    public void fresh() {
        topPanel.repaint();
        topPanel.revalidate();

        mainPanel.repaint();
        mainPanel.revalidate();

        bottomPanel.repaint();
        bottomPanel.revalidate();

        container.repaint();
        container.revalidate();
    }

}
