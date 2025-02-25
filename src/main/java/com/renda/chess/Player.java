package com.renda.chess;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.*;

import com.renda.chess.pieces.Piece;
import com.renda.chess.pieces.King;
import com.renda.chess.pieces.Pawn;

/**
 * The Player class represents a player in the game.
 */

public class Player {
    Colour colour;
    private int TurnCount;
    private Move opponentLastMove = null;
    // store a stored history of the player's moves
    private TreeMap<Integer, Move> moveHistory;

    public Player(Colour colour){
        this.colour = colour;
        TurnCount = 0;
        moveHistory = new TreeMap<Integer, Move>();
    }

    private void clearMoveHistory(){
        moveHistory.clear();
    }
    
    public void resetPlayer(){
        TurnCount = 0;
        clearMoveHistory();
        opponentLastMove = null;
    }
    
    // store the start and end position of a move
    public void storeMove(Position startPosition, Position endPosition){
        Move move = new Move(startPosition, endPosition);
        moveHistory.put(TurnCount, move);
    }

    public Move getLastMove(){
        Map.Entry<Integer, Move> lastMove = moveHistory.lastEntry();
        if (lastMove != null){
            return lastMove.getValue();
        }

        return null;
    }

    public void setOpponentLastMove(Move move){
        opponentLastMove = move;
    }

    // ensure that the player can't move the piece outside the board
    public boolean insideBoard(Position newPosition){
        int getRow = newPosition.getRow();
        int getCol = newPosition.getCol();

        if (getRow >= 0 && getRow <= 7){
            if (getCol >= 0 && getCol <= 7){
                return true;
            }
        }
        return false;
    }

    public boolean movePiece(Piece[][] board, Position currPosition, Position newPosition){
        int currRow = currPosition.getRow();
        int currCol = currPosition.getCol();
        int newRow = newPosition.getRow();
        int newCol = newPosition.getCol();

        Piece piece = board[currRow][currCol];

        // if the move is valid, move the piece to the new position 
        if (piece.isValidMove(newPosition, board) && piece.getColour() == colour && insideBoard(newPosition)){

            // check if the move is a castling move
            if (piece instanceof King && ((King) piece).isCastling(newPosition, board)){
                int colDiff = Math.abs(newCol - currCol);
                int rookCol = (newCol > currCol) ? 7 : 0;
                int step = (newCol > currCol) ? 1 : -1; // castling to the right or left

                // move the king
                board[newRow][newCol] = piece;
                piece.setPosition(newPosition);
                board[currRow][currCol] = null;

                // move the rook
                Piece rook = board[currRow][rookCol];
                board[currRow][currCol + step] = rook;
                rook.setPosition(new Position(currRow, currCol + step));
                board[currRow][rookCol] = null;

                TurnCount++;
                storeMove(currPosition, newPosition);

                return true;
            } 
            // check if the move is en passant 
            else if (piece instanceof Pawn && ((Pawn) piece).canEnPassant(newPosition, board, opponentLastMove)){
                // get the adjacent enemy pawn
                Piece enemyPawn = board[currRow][newCol];
                if (enemyPawn != null && enemyPawn instanceof Pawn && enemyPawn.getColour() != this.colour) {
                    // move the pawn
                    board[newRow][newCol] = piece; 
                    piece.setPosition(newPosition);
                    board[currRow][currCol] = null;
                    // capture the enemy pawn
                    board[currRow][newCol] = null;

                    TurnCount++;
                    storeMove(currPosition, newPosition);
                    return true;
                }
            } else {
                // capture the piece if necessary 
                if (piece.getColour() != this.colour) {
                    board[newRow][newCol] = null;   
                }

                board[newRow][newCol] = piece; 
                piece.setPosition(newPosition);
                board[currRow][currCol] = null;

                TurnCount++;
                storeMove(currPosition, newPosition);

                return true;
            }
        } 

        return false;
    }
}
