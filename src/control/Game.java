package control;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public GameBoard gameBoard;
    public Setting setting;

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
        Scanner in = new Scanner(System.in);
        while (true) {
            assert gameBoard != null;
            gameBoard.print();

            if (gameBoard.pieceAtPos(setting.winCondition.piece, setting.winCondition.h, setting.winCondition.w)) {
                System.out.println("恭喜获胜！");
                break;
            }

            ArrayList<PieceAndDirection> moves = gameBoard.getAllPossibleMoves();
            System.out.println("可选择的移动方式：");
            for (int i = 0; i < moves.size(); i++) {
                System.out.print(i);
                System.out.print("：");
                System.out.println(moves.get(i));
            }
            System.out.print("请选择移动方式：");
            int choice = in.nextInt();

            PieceAndDirection pieceAndDirection = moves.get(choice);
            gameBoard.move(pieceAndDirection.piece, pieceAndDirection.direction);
        }
    }
}
