package control;

import ai.BFSMap;
import model.*;
import view.GameFrame;


public class Game {
    public GameBoard gameBoard;
    public Setting setting;
    private GameFrame frame;
    private BFSMap aiMap;

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

    public void start() {
        frame = new GameFrame(gameBoard, this);
    }

    public void ai() throws Exception {
        if (aiMap == null) {
            aiMap = BFSMap.load("data/BFSMap/map.ser");
        }
        PieceAndDirection move = aiMap.query(gameBoard);
        step(move.piece, move.direction);
    }

    public void step(Piece piece, Direction direction) {
        try {
            if (gameBoard.ableToMove(piece, direction)) {
                gameBoard.move(piece, direction);
                frame.gamePanel.update(piece, direction);
                PieceAndPos winCondition = setting.winCondition;
                if (gameBoard.pieceAtPos(winCondition.piece, winCondition.h, winCondition.w)) {
                    end();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void end() {
        System.out.println("恭喜获胜！");
        frame.dispose();
    }
}
