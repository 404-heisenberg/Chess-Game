package com.renda.chess;

import com.renda.chess.pieces.King;
import com.renda.chess.pieces.Piece;
import com.renda.chess.pieces.Pawn;
import com.renda.chess.pieces.Queen;
import com.renda.chess.pieces.Rook;
import com.renda.chess.pieces.Bishop;
import com.renda.chess.pieces.Knight;

import java.util.*;

/**
 * The ChessGame class is responsible for managing the game state and logic.
 */

public class ChessGame {
    private Player white; 
    private Player black; 
    private ChessBoard chessBoard;
    private boolean isWhiteTurn = true;
    private Piece[][] board = new Piece[8][8];

    public ChessGame(){
        white = new Player(Colour.WHITE);
        black = new Player(Colour.BLACK);
        chessBoard = new ChessBoard();
        this.board = chessBoard.getBoard();
    }

    public boolean makeMove(Position start, Position end) {
        int startRow = start.getRow();
        int startCol = start.getCol();
        Piece pieceToMove = board[startRow][startCol];

        if (pieceToMove == null){
            return false;
        }

        Colour pieceColour = pieceToMove.getColour();

        // if it's checkmate, you can't make a move
        if (isCheckMate(Colour.BLACK) || isCheckMate(Colour.WHITE)){
            return false;
        }

        Colour playerColour = getCurrentPlayerColour();
        if (stalemate(playerColour)) {
            return false;
        }

        // if the move is invalid or gets/leaves the king in check, don't move the piece
        if (!pieceToMove.isValidMove(end, board) || isStillInCheck(pieceColour, start, end)){
            return false;
        }

        if (isWhiteTurn && pieceColour == Colour.WHITE) {
            // only move the piece if it doesn't leave the king in check
            if (!isInCheck(pieceColour) || !isStillInCheck(pieceColour, start, end)) {
                white.movePiece(board, start, end);
                isWhiteTurn = false;
            }
        } else if (!isWhiteTurn && pieceColour == Colour.BLACK) {
            // only move the piece if it doesn't leave the king in check
            if (!isInCheck(pieceColour) || !isStillInCheck(pieceColour, start, end)) {
                black.movePiece(board, start, end);
                isWhiteTurn = true;
            }
        } else {
            return false;
        }

        // inform each player about their opponent's last move
        if (black.getLastMove() != null) {
            updateWhiteLastMove();
        }
        updateBlackLastMove();

        return true;
    }

    private void updateWhiteLastMove(){
        white.setOpponentLastMove(black.getLastMove());
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Piece piece = board[row][col];
                if (piece != null && piece instanceof Pawn && piece.getColour() == Colour.WHITE){
                    Pawn pawn = (Pawn) piece;
                    pawn.setLastMove(black.getLastMove());
                }
            }
        }
    }

    private void updateBlackLastMove(){
        black.setOpponentLastMove(white.getLastMove());
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Piece piece = board[row][col];
                if (piece != null && piece instanceof Pawn && piece.getColour() == Colour.BLACK){
                    Pawn pawn = (Pawn) piece;
                    pawn.setLastMove(white.getLastMove());
                }
            }
        }
    }

    public boolean stalemate(Colour playerColour){
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                if (board[x][y] != null && board[x][y].getColour() == playerColour) {
                    ArrayList<Position> possibleMoves = board[x][y].getLegalMoveList(board);
                    for (Position potentialMove : possibleMoves) {
                        Position currPosition = new Position(x, y);
                        if (!isStillInCheck(playerColour, currPosition, potentialMove)) {
                            return false;
                        }
                    }   
                }
            }
        }
        return true;
    }

    private Position getKingPosition(Colour pieceColour){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColour() == pieceColour){
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    public boolean isInCheck(Colour pieceColour){
        Position kingPosition = getKingPosition(pieceColour);
        // loop through the board to check if any of the opponent's pieces can attack the king
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Piece piece = board[row][col];
                if (piece != null && piece.getColour() != pieceColour){
                    if (piece.isValidMove(kingPosition, board)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckMate(Colour pieceColour){
        // If the king is not in check, it's not checkmate
        if (!isInCheck(pieceColour)) {
            return false;
        }

        Position kingPosition = getKingPosition(pieceColour);
        King king = (King) board[kingPosition.getRow()][kingPosition.getCol()];

        // Check all possible moves for the king
        for (int rowDelta = -1; rowDelta <= 1; rowDelta++) {
            for (int colDelta = -1; colDelta <= 1; colDelta++) {
                // Skip the current position
                if (rowDelta == 0 && colDelta == 0) {
                    continue;
                }

                Position newPosition = new Position(kingPosition.getRow() + rowDelta, kingPosition.getCol() + colDelta);

                // If the new position is on the board, is a valid move, and does not leave the king in check
                if (isOnBoard(newPosition) && king.isValidMove(newPosition, board) && !isStillInCheck(pieceColour, kingPosition, newPosition)) {
                    return false;
                }
            }
        }

        // check all possible moves for the player
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                if (board[x][y] != null && board[x][y].getColour() == pieceColour){
                    // get the valid moves for that piece
                    ArrayList<Position> legalMoves = board[x][y].getLegalMoveList(board);
                    // if any of the valid moves get the king out of check, stop the loop, return false
                    for (Position potentialMove : legalMoves){
                        Position currPosition = new Position(x, y);
                        if (!isStillInCheck(pieceColour, currPosition, potentialMove)){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean isOnBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < 8 && position.getCol() >= 0 && position.getCol() < 8;
    }

    // make a temporary move to check if the king is still in check
    public boolean isStillInCheck(Colour pieceColour, Position oldPosition, Position newPosition){

        Piece temp = board[newPosition.getRow()][newPosition.getCol()];

        // swap the pieces
        board[newPosition.getRow()][newPosition.getCol()] = board[oldPosition.getRow()][oldPosition.getCol()];
        board[oldPosition.getRow()][oldPosition.getCol()] = null;

        boolean stillInCheck = isInCheck(pieceColour);

        // restore to orginal positions
        board[oldPosition.getRow()][oldPosition.getCol()] = board[newPosition.getRow()][newPosition.getCol()];
        board[newPosition.getRow()][newPosition.getCol()] = temp;

        return stillInCheck;
    }

    public ChessBoard getBoard(){
        return chessBoard;
    }

    public void resetGame(){
        white.resetPlayer();
        black.resetPlayer();
        this.chessBoard.resetBoard();
        this.board = chessBoard.getBoard();
        isWhiteTurn = true;
    }

    public Colour getCurrentPlayerColour() {
        Colour playerColour = (isWhiteTurn) ? Colour.WHITE : Colour.BLACK;
        return playerColour;
    }

    private Position selectedPosition;
    private ArrayList<Position> MovablePositions = new ArrayList<>();

    public boolean pieceIsSelected(){
        return selectedPosition != null;
    }

    // handleSquareSelection either selects a piece or moves a piece based on the selected square
    public boolean handleSquareSelection(int row, int col) {
        
        // if a piece isn't selected, select the new piece
        if (!pieceIsSelected() && board[row][col] != null) {
            selectedPosition = new Position(row, col);
            MovablePositions = getLegalMovesForPieceAt(selectedPosition);
            return false; // a move wasn't made
        }
        // if a piece is already selected
        if (pieceIsSelected()) {
            // if a piece is already selected and another piece is selected of the same colour, deselect the first piece and select the new piece
            if (board[row][col] != null && board[row][col].getColour() == board[selectedPosition.getRow()][selectedPosition.getCol()].getColour()) {
                selectedPosition = new Position(row, col);
                MovablePositions = getLegalMovesForPieceAt(selectedPosition);
                return false; // a move wasn't made
            }
            // and a valid move is made, move the piece
            Position newPosition = new Position(row, col);
            if (MovablePositions.contains(newPosition)) {
                makeMove(selectedPosition, newPosition);
                selectedPosition = null;
                MovablePositions = new ArrayList<>();
                return true; // a move was made
            // if a piece is already selected and an invalid move is made, deselect the piece
            } else {
                selectedPosition = null;
                MovablePositions = new ArrayList<>();
                return false; // a move wasn't made
            }
        }
        return false;
    }

    public ArrayList<Position> getLegalMovesForPieceAt(Position position){
        int row = position.getRow();
        int col = position.getCol();
        Piece piece = this.chessBoard.getPiece(row, col);
        if (piece == null) {
            return new ArrayList<>(); // Return an empty list if there is no piece at the specified position
        }    
        return piece.getLegalMoveList(this.board);
    }

    public void promotePawn(int row, int col, Class <? extends Piece> c){
        Pawn pawn = (Pawn) board[row][col];
        if (c == Queen.class) {
            board[row][col] = new Queen(pawn.getColour(), new Position(row, col));
        } 
        else if (c == Rook.class){
            board[row][col] = new Rook(pawn.getColour(), new Position(row, col));
        }
        else if (c == Bishop.class){
            board[row][col] = new Bishop(pawn.getColour(), new Position(row, col));
        }
        else if (c == Knight.class){
            board[row][col] = new Knight(pawn.getColour(), new Position(row, col));
        }
    }
}
