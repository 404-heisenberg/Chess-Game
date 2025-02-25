package com.renda.chess.pieces;

import com.renda.chess.Colour;
import com.renda.chess.Position;
import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(Colour colour, Position position){
        super(colour, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board){
        int currRow = this.position.getRow();
        int currCol = this.position.getCol();

        int newRow = newPosition.getRow();
        int newCol = newPosition.getCol();

        int rowDiff = Math.abs(newRow - currRow);
        int colDiff = Math.abs(newCol - currCol);

        // check if new position is within bounds
        if (!Position.isValidPosition(newPosition)){
            return false;
        }

        // valid L move
        if ((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1)){
            // destination square capturable or not blocked
            Piece piece = board[newRow][newCol];
            if (piece == null || piece.getColour() != this.getColour()) return true;
        }  

        return false;
    }

    @Override
    public ArrayList<Position> getLegalMoveList(Piece[][] board){
        clearLegalMoveList();
        int currRow = this.position.getRow();
        int currCol = this.position.getCol();

        if (isValidMove(new Position(currRow + 2, currCol - 1), board)){
            legalMoveList.add(new Position(currRow + 2, currCol - 1));
        }

        if (isValidMove(new Position(currRow - 2, currCol - 1), board)){
            legalMoveList.add(new Position(currRow - 2, currCol - 1));
        }

        if (isValidMove(new Position(currRow + 2, currCol + 1), board)){
            legalMoveList.add(new Position(currRow + 2, currCol + 1));
        }

        if (isValidMove(new Position(currRow - 2, currCol + 1), board)){
            legalMoveList.add(new Position(currRow - 2, currCol + 1));
        }

        if (isValidMove(new Position(currRow + 1, currCol + 2), board)){
            legalMoveList.add(new Position(currRow + 1, currCol + 2));
        }

        if (isValidMove(new Position(currRow + 1, currCol - 2), board)){
            legalMoveList.add(new Position(currRow + 1, currCol - 2));
        }

        if (isValidMove(new Position(currRow - 1, currCol + 2), board)){
            legalMoveList.add(new Position(currRow - 1, currCol + 2));
        }

        if (isValidMove(new Position(currRow - 1, currCol - 2), board)){
            legalMoveList.add(new Position(currRow - 1, currCol - 2));
        }

        return legalMoveList;
    }
}
