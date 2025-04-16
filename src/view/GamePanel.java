package view;

import control.Game;
import model.Direction;
import model.GameBoard;
import model.Piece;
import model.PieceAndPos;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final GameBoard gameBoard;
    private final Game game;
    private int unitSize = 100;
    private double animationLength = 0.15;

    public GamePanel(GameBoard gameBoard, Game game) {
        this.gameBoard = gameBoard;
        this.game = game;
        setLayout(null);
        setPreferredSize(new Dimension(unitSize * gameBoard.occupancyMap[0].length, unitSize * gameBoard.occupancyMap.length));
    }

    public void update(Piece animatedPiece, Direction direction) {
        long startTime = System.currentTimeMillis();

        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            long currentTime = System.currentTimeMillis();
            double animatedRate = Math.min((currentTime - startTime) / (animationLength * 1000), 1.0);
            fresh(animatedPiece, direction, animatedRate);
            if (animatedRate >= 1.0) {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    public void fresh(Piece animatedPiece, Direction direction, double animatedRate) {
        removeAll();

        for (PieceAndPos pieceAndPos : gameBoard.piecesAndPoses) {
            Piece piece = pieceAndPos.piece;
            PiecePanel piecePanel = new PiecePanel(piece, game);
            add(piecePanel);
            if (animatedPiece == piece) {
                piecePanel.setLocation(
                        (int)(unitSize * (pieceAndPos.w - (1 - animatedRate) * direction.w)),
                        (int)(unitSize * (pieceAndPos.h - (1 - animatedRate) * direction.h))
                );
            } else {
                piecePanel.setLocation(unitSize * pieceAndPos.w, unitSize * pieceAndPos.h);
            }
            piecePanel.setSize(unitSize * piece.width, unitSize * piece.height);
        }

        revalidate();
        repaint();
    }
}
