package view;

import control.Game;
import model.GameBoard;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GamePanel gamePanel;

    public GameFrame(GameBoard gameBoard, Game game) {
        setTitle("Klotski");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel(gameBoard, game);
        gamePanel.fresh(null, null, 0);
        setContentPane(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
