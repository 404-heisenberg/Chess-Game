package com.renda.chess;

/**
 * The Move class represents a move from one position to another.
 */

public class Move {
    Position startPosition;
    Position endPosition;

    public Move(Position startPosition, Position endPosition){
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Position getStartPosition(){
        return startPosition;
    }

    public Position getEndPosition(){
        return endPosition;
    }

    @Override
    public String toString(){
        return startPosition + " -> " + endPosition;
    }
}