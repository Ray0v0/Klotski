package model;

public class PieceAndDirection {
    public Piece piece;
    public Direction direction;

    public PieceAndDirection(Piece piece, Direction direction) {
        this.piece = piece;
        this.direction = direction;
    }

    public String toString() {
        return piece.name +
                "可以向" +
                direction.name +
                "移动";
    }
}
