package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard implements Serializable {
    public boolean[][] occupancyMap;
    public ArrayList<PieceAndPos> piecesAndPoses = new ArrayList<>();

    public GameBoard(int height, int width) {
        occupancyMap = new boolean[height][width];
        clear();
    }

    public void clear() {
        for (boolean[] row : occupancyMap) {
            Arrays.fill(row, false);
        }
        piecesAndPoses.clear();
    }

    public void put(Piece piece, int h, int w) throws Exception {
        if (ableToPut(piece, h, w)) {
            piecesAndPoses.add(new PieceAndPos(piece, h, w));
            for (int i = 0; i < piece.height; i++) {
                for (int j = 0; j < piece.width; j++) {
                    occupancyMap[i + h][j + w] = true;
                }
            }
        } else {
            throw new Exception("无法放置棋子！");
        }
    }

    public PieceAndPos remove(Piece piece) throws Exception{
        for (PieceAndPos pieceAndPos : piecesAndPoses) {
            if (pieceAndPos.piece == piece) {
                piecesAndPoses.remove(pieceAndPos);
                for (int i = 0; i < piece.height; i++) {
                    for (int j = 0; j < piece.width; j++) {
                        occupancyMap[i + pieceAndPos.h][j + pieceAndPos.w] = false;
                    }
                }
                return pieceAndPos;
            }
        }
        throw new Exception("棋子未找到！");
    }

    private boolean ableToPut(Piece piece, int h, int w) {
        for (int i = 0; i < piece.height; i++) {
            for (int j = 0; j < piece.width; j++) {
                if (occupied(h + i, w + j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean occupied(int h, int w) {
        if (h >= occupancyMap.length || h < 0 || w >= occupancyMap[0].length || w < 0) {
            return true;
        }
        return occupancyMap[h][w];
    }

    public void print() {
        System.out.print("-\t");
        for (int i = 0; i < occupancyMap[0].length; i++) {
            System.out.print("-\t");
        }
        System.out.println("-");

        char[][] show = new char[occupancyMap.length][occupancyMap[0].length];
        for (PieceAndPos pieceAndPos : piecesAndPoses) {
            Piece piece = pieceAndPos.piece;
            for (int i = 0; i < piece.height; i++) {
                for (int j = 0; j < piece.width; j++) {
                    show[i + pieceAndPos.h][j + pieceAndPos.w] = piece.name.charAt(0);
                }
            }
        }

        for (int i = 0; i < occupancyMap.length; i++) {
            System.out.print("|\t");
            for (int j = 0; j < occupancyMap[i].length; j++) {
                System.out.print(show[i][j]);
                System.out.print("\t");
            }
            System.out.println("|");
        }

        System.out.print("-\t");
        for (int i = 0; i < occupancyMap[0].length; i++) {
            System.out.print("-\t");
        }
        System.out.println("-");
    }

    public ArrayList<PieceAndDirection> getAllPossibleMoves() throws Exception {
        ArrayList<PieceAndDirection> possibleMoves = new ArrayList<>();
        ArrayList<PieceAndPos> piecesAndPosesCopy = new ArrayList<>(piecesAndPoses);
        for (PieceAndPos pieceAndPos : piecesAndPosesCopy) {
            Piece piece = pieceAndPos.piece;
            for (Direction direction : Direction.ALL_DIRECTIONS) {
                if (ableToMove(piece, direction)) {
                    possibleMoves.add(new PieceAndDirection(piece, direction));
                }
            }
        }
        return possibleMoves;
    }

    public boolean ableToMove(Piece piece, Direction direction) throws Exception {
        PieceAndPos pieceAndPos = remove(piece);
        boolean ableToMove = ableToPut(pieceAndPos.piece, pieceAndPos.h + direction.h, pieceAndPos.w + direction.w);
        put(pieceAndPos.piece, pieceAndPos.h, pieceAndPos.w);
        return ableToMove;
    }

    public void move(Piece piece, Direction direction) throws Exception {
        PieceAndPos pieceAndPos = remove(piece);
        put(pieceAndPos.piece, pieceAndPos.h + direction.h, pieceAndPos.w + direction.w);
    }

    public boolean pieceAtPos(Piece piece, int h, int w) {
        for (PieceAndPos pieceAndPos : piecesAndPoses) {
            if (pieceAndPos.piece == piece && pieceAndPos.h == h && pieceAndPos.w == w) {
                return true;
            }
        }
        return false;
    }
}
