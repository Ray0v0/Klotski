package view;

import control.Game;
import model.Direction;
import model.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PiecePanel extends JPanel implements MouseListener {
    private Piece piece;
    private Game game;
    private int xPressed, yPressed, xReleased, yReleased;

    public PiecePanel(Piece piece, Game game) {
        this.piece = piece;
        this.game = game;
        setBackground(piece.color);
        setPreferredSize(new Dimension(piece.width * 100, piece.height * 100));
        addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        xPressed = e.getX();
        yPressed = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        xReleased = e.getX();
        yReleased = e.getY();

        int xDiff = xReleased - xPressed;
        int yDiff = yReleased - yPressed;

        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff > 0) {
                game.step(piece, Direction.RIGHT);
            } else {
                game.step(piece, Direction.LEFT);
            }
        } else {
            if (yDiff > 0) {
                game.step(piece, Direction.DOWN);
            } else {
                game.step(piece, Direction.UP);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
