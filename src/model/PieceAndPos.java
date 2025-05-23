package model;

import java.io.Serializable;

public class PieceAndPos implements Serializable {
    public Piece piece;
    public int h, w;

    public PieceAndPos(Piece piece, int h, int w) {
        this.piece = piece;
        this.h = h;
        this.w = w;
    }

    public String toString() {
        return piece.name + " (" + h + ", " + w + ")";
    }
}
