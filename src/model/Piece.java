package model;

import java.awt.*;

public class Piece {
    public String name;
    public int height, width;
    public Color color;

    public Piece(String name, int height, int width, Color color) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.color = color;
    }

}
