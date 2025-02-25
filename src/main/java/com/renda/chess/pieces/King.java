package com.renda.chess.pieces;

import java.util.ArrayList;

import com.renda.chess.Colour;
import com.renda.chess.Position;
import java.util.ArrayList;

public class King extends Piece {
    private final Position originalPosition;

     public King(Colour colour, Position position){
        super(colour, position);
        originalPosition = position;
    }

    public boolean hasMoved(){
        if (this.position.equals(originalPosition)) return false;
        return true;
    }

    // check if the square is under attack by an enemy piece
    private boolean isSquareUnderAttack(Position position, Piece[][] board){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Piece piece = board[row][col];
                if (piece != null && piece.getColour() != this.colour && !(piece instanceof King)){
                    ArrayList<Position> legalMoves = piece.getLegalMoveList(board);
                    if (legalMoves != null && legalMoves.contains(position)) return true;
                }
            }
        }
        return false;
    }

    public boolean isCastling(Position newPosition, Piece[][] board){
        int currRow = this.position.getRow();
        int currCol = this.position.getCol();
        int newRow = newPosition.getRow();
        int newCol = newPosition.getCol();
        int rowDiff = Math.abs(newRow - currRow);
        int colDiff = Math.abs(newCol - currCol);

        // check if king has moved
        if (hasMoved()) return false;

        // if the move is a castling move
        if (rowDiff == 0 && colDiff == 2){
            // determine if we are castling to the left or right
            int rookCol = (newCol > currCol) ? 7 : 0;
            int step = (newCol > currCol) ? 1 : -1;

            // check if the rook has moved
            Piece rook = board[currRow][rookCol];
            if (rook == null || !(rook instanceof Rook) || ((Rook) rook).hasMoved()) return false;

            // check if the squares between the king and rook are empty
            for (int col = currCol + step; col != rookCol; col += step){
                if (board[currRow][col] != null) return false;
            }

            // check if the king passes through or lands on a square that is attacked by an enemy piece
            for (int col = currCol; col != newCol + step; col += step) {
                if (isSquareUnderAttack(new Position(currRow, col), board)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board){
        int currRow = this.position.getRow();
        int currCol = this.position.getCol();
        int newRow = newPosition.getRow();
        int newCol = newPosition.getCol();
        int rowDiff = Math.abs(newRow - currRow);
        int colDiff = Math.abs(newCol - currCol);

        Piece piece = board[newRow][newCol];

        // check if new position is within bounds
        if (!Position.isValidPosition(newPosition)){
            return false;
        }

        // if moving more than one square, check if castling
        if ((rowDiff > 1) || (colDiff > 1)) return isCastling(newPosition, board);

        // end position not blocked
        if (piece != null && piece.getColour() == this.colour) return false;
        
        return true;
    }

    @Override
    public ArrayList<Position> getLegalMoveList(Piece[][] board){
        clearLegalMoveList();
        int currRow = this.position.getRow();
        int currCol = this.position.getCol();

        // check all the squares around the king
        for (int r_step = -1; r_step <= 1; r_step++){
            for (int c_step = -1; c_step <= 1; c_step++){
                if (r_step == 0 && c_step == 0) continue;

                // if potential square is within bounds & move is valid
                if (Position.isValidPosition(new Position(currRow + r_step, currCol + c_step)) && isValidMove(new Position(currRow + r_step, currCol + c_step), board)){
                    legalMoveList.add(new Position(currRow + r_step, currCol + c_step));
                }
            }
        }

        // check for castling moves
        for (int c_step = -2; c_step <=2; c_step +=4){
            if (isCastling(new Position(currRow, currCol + c_step), board)){
                legalMoveList.add(new Position(currRow, currCol + c_step));
            }
        }

        return legalMoveList;
    }
}
