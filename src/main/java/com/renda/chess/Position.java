package com.renda.chess;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public static boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public static boolean isValidPosition(Position position) {
        return isValidPosition(position.getRow(), position.getCol());
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Position)) return false; // also checks if o is null
        Position p = (Position) o;
        return p.row == this.row && p.col == this.col;
    }
}
