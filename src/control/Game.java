package control;

import model.*;
import view.GameFrame;


public class Game {
    public GameBoard gameBoard;
    public Setting setting;
    private GameFrame frame;

    public Game() {

    }

    public void initialize(Setting setting) throws Exception {
        this.setting = setting;
        gameBoard = new GameBoard(setting.height, setting.width);
        reinitialize();
    }

    public void reinitialize() throws Exception {
        gameBoard.clear();
        for (PieceAndPos pieceAndPos : setting.piecesAndPoses) {
            gameBoard.put(pieceAndPos.piece, pieceAndPos.h, pieceAndPos.w);
        }
    }

    public void start() throws Exception {
        frame = new GameFrame(gameBoard, this);
    }

    public void step(Piece piece, Direction direction) {
        try {
            if (gameBoard.ableToMove(piece, direction)) {
                gameBoard.move(piece, direction);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        frame.gamePanel.fresh();
    }
}
