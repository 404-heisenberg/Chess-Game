package com.renda.chess.pieces;

import java.util.ArrayList;
import com.renda.chess.Colour;
import com.renda.chess.Position;
import com.renda.chess.pieces.Rook;

public class Rook extends Piece {
    private final Position originalPosition;

    public Rook(Colour colour, Position position){
        super(colour, position);
        originalPosition = position;
    }

    public boolean hasMoved(){
        if (originalPosition.equals(this.position)) return false;   
        return true;
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        int rowDiff = this.position.getRow() - newPosition.getRow();
        int colDiff = this.position.getCol() - newPosition.getCol();

        int currRow = this.position.getRow();
        int currCol = this.position.getCol();

        int newRow = newPosition.getRow();
        int newCol = newPosition.getCol();

        Piece piece = board[newPosition.getRow()][newPosition.getCol()];

        // check if new position is within bounds
        if (!Position.isValidPosition(newPosition)){
            return false;
        }

        // invalid rook movement (not a striaght line)
        if (rowDiff != 0 && colDiff != 0 ) return false;

        // check vertical movements
        if (colDiff == 0) {
            int step = (newRow > currRow) ? 1:-1; // determine if we are moving or down
            // check all the in-between squares 
            for (int row = currRow + step; row != newRow; row += step){
                // if sqaure not empty or out of bounds, return false
                if (!Position.isValidPosition(row, currCol) || board[row][currCol] != null) return false;
            }
        } 
        // check horizontal movements
        else if (rowDiff == 0){
            int step = (newCol > currCol) ? 1:-1; // determine if we are moving left or right
            // check all the in-between squares 
            for (int col = currCol + step; col != newCol; col += step){
                // if sqaure not empty or out of bounds, return false
                if (board[currRow][col] != null || !Position.isValidPosition(currCol, col)) return false;
            }
        }

        // check if blocked at end position 
        if (piece != null && piece.getColour() == this.getColour()) return false; 

        return true;

    }

    @Override
    public ArrayList<Position> getLegalMoveList(Piece[][] board){
        clearLegalMoveList();
        int currRow = this.position.getRow();
        int currCol = this.position.getCol();

        int up = currRow + 1;
        while (Position.isValidPosition(up, currCol) && isValidMove(new Position(up, currCol), board)){
            legalMoveList.add(new Position(up, currCol));
            up++;
        }

        int down = currRow - 1;
        while (Position.isValidPosition(down, currCol) && isValidMove(new Position(down, currCol), board)){
            legalMoveList.add(new Position(down, currCol));
            down--;
        }

        int right = currCol + 1;
        while (Position.isValidPosition(currRow, right) && isValidMove(new Position(currRow, right), board)){
            legalMoveList.add(new Position(currRow, right));
            right++;
        }

        int left = currCol - 1;
        while (Position.isValidPosition(currRow, left) && isValidMove(new Position(currRow, left), board)){
            legalMoveList.add(new Position(currRow, left));
            left--;
        }

        return legalMoveList;
    }
}
