package com.renda.chess.pieces;

import com.renda.chess.Colour;
import com.renda.chess.Position;

import java.util.ArrayList;

public abstract class Piece {
    protected Colour colour;
    protected Position position;
    protected ArrayList<Position> legalMoveList;

    public Piece(Colour colour, Position position) {
        this.colour = colour;
        this.position = position;
        legalMoveList = new ArrayList<>();
    }

    public Colour getColour() {
        return colour;
    }


    public void setPosition(Position newPosition) {
        position = newPosition;
    }

    public Position getPosition() {
        return position;
    }

    public abstract ArrayList<Position> getLegalMoveList(Piece[][] board);

    public void clearLegalMoveList(){
        legalMoveList.clear();
    }

    public abstract boolean isValidMove(Position newPosition, Piece[][] board);
}
