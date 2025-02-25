package com.renda.chess.pieces;

import java.util.ArrayList;
import com.renda.chess.Colour;
import com.renda.chess.Position;

public class Queen extends Piece {
    public Queen(Colour colour, Position posistion){
        super(colour, posistion);
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

        Piece piece = board[newRow][newCol];

        // valid queen movement (either straight line or diagonal)
        if (rowDiff != colDiff  && rowDiff != 0 && colDiff != 0 ) return false;

        // diagonal movement
        if (rowDiff == colDiff){
            // in-between squares must be empty
            int r_step = (newRow > currRow) ? 1:-1;
            int c_step = (newCol > currCol) ? 1:-1;
            int row = currRow + r_step;
            int col = currCol + c_step;

            while (row != newRow && col != newCol) {
                if (!Position.isValidPosition(row, col)){
                    return false;
                }
                if (board[row][col] != null) {
                    return false;
                }
                row += r_step;
                col += c_step;
            }
        }

        // check vertical movements
        else if (colDiff == 0) {
            int step = (newRow > currRow) ? 1:-1; 
            // check all the in-between squares 
            for (int row = currRow + step; row != newRow; row += step){
                if (board[row][currCol] != null || !Position.isValidPosition(row, currCol)) return false;
            }
        } 

        // check horizontal movements
        else if (rowDiff == 0){
            int step = (newCol > currCol) ? 1:-1; 
            // check all the in-between squares 
            for (int col = currCol + step; col != newCol; col += step){
                if (board[currRow][col] != null || !Position.isValidPosition(currRow, col)) return false;
            }
        } 
    

        // end position not blocked
        if (piece != null && piece.getColour() == this.getColour()) return false;
        
        return true;
    }

    @Override
    public ArrayList<Position> getLegalMoveList(Piece[][] board){
        clearLegalMoveList();

        // check vertical moves 
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

        // check diagonal moves
        int NWrow = currRow - 1;
        int NWcol = currCol - 1;
        while (isValidMove(new Position(NWrow, NWcol), board) && NWrow >= 0 && NWcol >= 0){
            legalMoveList.add(new Position(NWrow, NWcol));
            NWrow--;
            NWcol--;
        }

        int SWrow = currRow + 1;
        int SWcol = currCol - 1;
        while (isValidMove(new Position(SWrow, SWcol), board) && SWrow < 8 && SWcol >= 0){
            legalMoveList.add(new Position(SWrow, SWcol));
            SWrow++;
            SWcol--;
        }

        int NErow = currRow - 1;
        int NEcol = currCol + 1;
        while (isValidMove(new Position(NErow, NEcol), board) && NErow >= 0 && NEcol < 8){
            legalMoveList.add(new Position(NErow, NEcol));
            NErow--;
            NEcol++;
        }

        int SErow = currRow + 1;
        int SEcol = currCol + 1;
        while (isValidMove(new Position(SErow, SEcol), board) && SErow < 8 && SEcol < 8){
            legalMoveList.add(new Position(SErow, SEcol));
            SErow++;
            SEcol++;
        }

        return legalMoveList;
    }
}
