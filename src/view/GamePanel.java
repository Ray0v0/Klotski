package view;

import control.Game;
import model.Direction;
import model.GameBoard;
import model.Piece;
import model.PieceAndPos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GamePanel extends JPanel implements ComponentListener {

    public final GameBoard gameBoard;
    private final Game game;
    private final GameFrame gameFrame;
    public int unitSize = 100;
    private final double animationLength = 0.15;

    public GamePanel(GameBoard gameBoard, Game game, GameFrame gameFrame) {
        this.gameBoard = gameBoard;
        this.game = game;
        this.gameFrame = gameFrame;
        setLayout(null);
        setPreferredSize(new Dimension((int) (unitSize * gameBoard.occupancyMap[0].length), (int) (unitSize * gameBoard.occupancyMap.length)));
        addComponentListener(this);
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
                piecePanel.setLocation((int) (unitSize * pieceAndPos.w), (int) (unitSize * pieceAndPos.h));
            }
            piecePanel.setSize((int) (unitSize * piece.width), (int) (unitSize * piece.height));
        }

        revalidate();
        repaint();
        gameFrame.pack();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int width = getWidth();
        int height = getHeight();

        if (Math.abs(width - unitSize * gameBoard.occupancyMap[0].length) > 1) {
            unitSize = width / gameBoard.occupancyMap[0].length;
        } else if (Math.abs(height - unitSize * gameBoard.occupancyMap.length) > 1) {
            unitSize = height / gameBoard.occupancyMap.length;
        }

        setPreferredSize(new Dimension(unitSize * gameBoard.occupancyMap[0].length, unitSize * gameBoard.occupancyMap.length));

        fresh(null, null, 0);
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
