package com.renda.chess.pieces;

import com.renda.chess.Colour;
import com.renda.chess.Position;
import com.renda.chess.Move;
import java.util.ArrayList;

public class Pawn extends Piece {
    private Position lastMoveStart;
    private Position lastMoveEnd;

    public Pawn(Colour colour, Position position) {
        super(colour, position);
        lastMoveEnd = null;
        lastMoveStart = null;
    }

    public void setLastMove(Move m) {
        Position start = m.getStartPosition();
        Position end = m.getEndPosition();
        lastMoveStart = start;
        lastMoveEnd = end;
    }

    public boolean canEnPassant(Position newPosition, Piece[][] board, Position lastMoveStart, Position lastMoveEnd) {
        int currRow = this.position.getRow();
        int currCol = this.position.getCol();
        int targetRow = newPosition.getRow();
        int targetCol = newPosition.getCol();
        int rowDiff = Math.abs(currRow - targetRow);
        int colDiff = Math.abs(currCol - targetCol);

        // the opponent's last move was a pawn moving two squares forward 
        if ((Math.abs(lastMoveEnd.getRow() - lastMoveStart.getRow()) == 2) && (lastMoveEnd.getCol() == targetCol)) {
            // the opponent's pawn is adjacent to the current pawn
            if (lastMoveEnd.getRow() == currRow && Math.abs(lastMoveEnd.getCol() - currCol) == 1) {
                // the current pawn is moving diagonally behind the opponent's pawn
                if (rowDiff == 1 && colDiff == 1 && targetCol == lastMoveEnd.getCol()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canEnPassant(Position newPosition, Piece[][] board, Move lastMove) {
        if (lastMove == null) return false;
        return canEnPassant(newPosition, board, lastMove.getStartPosition(), lastMove.getEndPosition());
    }

    // determine the forward direction of piece based off its colour
    public int detForwardDirection() {
        if (this.getColour().equals(Colour.WHITE)) {
            return -1;
        } else {
            return 1;
        }
    }

    public boolean isAtEndOfBoard(){
        if (this.colour == Colour.WHITE && this.position.getRow() == 0){
            return true;
        } else if (this.colour == Colour.BLACK && this.position.getRow() == 7){
            return true;
        }
        return false;
    }

    // check if pawn move is valid
    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        int forwardDirection = detForwardDirection();
        int rowDiff = (newPosition.getRow() - position.getRow()) * forwardDirection;
        int colDiff = (newPosition.getCol() - position.getCol());

        Piece piece = board[newPosition.getRow()][newPosition.getCol()];

        // check if new position is within bounds
        if (!Position.isValidPosition(newPosition)) {
            return false;
        }

        // forward direction 
        if (rowDiff == 1 && colDiff == 0 && piece == null) return true;

        // diagonal direction 
        if (rowDiff == 1 && Math.abs(colDiff) == 1 && piece != null && piece.getColour() != this.getColour()) return true;

        // two-square move
        int inbetween = this.getPosition().getRow() + forwardDirection;  // check that the in-between square is empty
        if ((rowDiff == 2 && colDiff == 0) && (piece == null && board[inbetween][newPosition.getCol()] == null)) {
            if (this.getPosition().getRow() == 6 && this.getColour() == Colour.WHITE) {
                return true;
            } else if (this.getPosition().getRow() == 1 && this.getColour() == Colour.BLACK) {
                return true;
            }
        }

        // en passant
        if (rowDiff == 1 && Math.abs(colDiff) == 1 && piece == null && lastMoveStart != null && lastMoveEnd != null) {
            if (canEnPassant(newPosition, board, lastMoveStart, lastMoveEnd)) return true;
        }

        return false;
    }

    @Override
    public ArrayList<Position> getLegalMoveList(Piece[][] board) {
        clearLegalMoveList();
        int currRow = this.position.getRow();
        int currCol = this.position.getCol();
        int step = detForwardDirection(); // forward direction based off pawn colour

        // single move
        if (Position.isValidPosition(currRow + step, currCol) && isValidMove(new Position(currRow + step, currCol), board)) {
            legalMoveList.add(new Position(currRow + step, currCol));
        }

        // double move
        if (Position.isValidPosition(currRow + 2 * step, currCol) && isValidMove(new Position(currRow + 2 * step, currCol), board)) {
            legalMoveList.add(new Position(currRow + 2 * step, currCol));
        }

        // capture right
        if (Position.isValidPosition(currRow + step, currCol + 1) && isValidMove(new Position(currRow + step, currCol + 1), board)) {
            legalMoveList.add(new Position(currRow + step, currCol + 1));
        }

        // capture left
        if (Position.isValidPosition(currRow + step, currCol - 1) && isValidMove(new Position(currRow + step, currCol - 1), board)) {
            legalMoveList.add(new Position(currRow + step, currCol - 1));
        }

        return legalMoveList;
    }
}