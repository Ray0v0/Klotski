package view;

import control.Game;
import model.GameBoard;
import model.Piece;
import model.PieceAndPos;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final GameBoard gameBoard;
    private final Game game;
    private int unitSize = 100;

    public GamePanel(GameBoard gameBoard, Game game) {
        this.gameBoard = gameBoard;
        this.game = game;
        setLayout(null);
        setPreferredSize(new Dimension(unitSize * gameBoard.occupancyMap[0].length, unitSize * gameBoard.occupancyMap.length));
    }

    public void fresh() {
        removeAll();

        for (PieceAndPos pieceAndPos : gameBoard.piecesAndPoses) {
            Piece piece = pieceAndPos.piece;
            PiecePanel piecePanel = new PiecePanel(piece, game);
            add(piecePanel);
            piecePanel.setLocation(unitSize * pieceAndPos.w, unitSize * pieceAndPos.h);
            piecePanel.setSize(unitSize * piece.width, unitSize * piece.height);
        }

        revalidate();
        repaint();
    }
}
