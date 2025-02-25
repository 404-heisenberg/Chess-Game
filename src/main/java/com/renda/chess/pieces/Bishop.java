package com.renda.chess.pieces;

import com.renda.chess.Colour;
import com.renda.chess.Position;
import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(Colour colour, Position position){
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

        // diagonal movement
        if (rowDiff - colDiff != 0) return false;

        // in-between squares must be empty
        int r_step = (newRow > currRow) ? 1:-1;
        int c_step = (newCol > currCol) ? 1:-1;
        int row = currRow + r_step;
        int col = currCol + c_step;

        while (row != newRow && col != newCol) {
            if (!Position.isValidPosition(newPosition)){
                return false;
            }
            if (board[row][col] != null) {
                return false;
            }
            row += r_step;
            col += c_step;
        }

        // end posistion not blocked
        Piece piece = board[newPosition.getRow()][newPosition.getCol()];
        if (piece != null && piece.getColour() == this.getColour()) return false;

        return true;
    }

    public ArrayList<Position> getLegalMoveList(Piece[][] board){
        clearLegalMoveList();

        int currRow = this.position.getRow();
        int currCol = this.position.getCol();

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
