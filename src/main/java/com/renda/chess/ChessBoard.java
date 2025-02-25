package com.renda.chess;

import com.renda.chess.pieces.Piece;
import com.renda.chess.pieces.Pawn;
import com.renda.chess.pieces.Rook;
import com.renda.chess.pieces.Queen;
import com.renda.chess.pieces.King;
import com.renda.chess.pieces.Knight;
import com.renda.chess.pieces.Bishop;

public class ChessBoard {
    private Piece[][] board;
    
    public ChessBoard(){
        board = new Piece[8][8];
        setUpPieces();
    }

    public Piece[][] getBoard(){
        return board;
    }

    private void setUpPieces(){
        // Place Rooks
        board[0][0] = new Rook(Colour.BLACK, new Position(0, 0));
        board[0][7] = new Rook(Colour.BLACK, new Position(0, 7));
        board[7][0] = new Rook(Colour.WHITE, new Position(7, 0));
        board[7][7] = new Rook(Colour.WHITE, new Position(7, 7));
        // Place Knights
        board[0][1] = new Knight(Colour.BLACK, new Position(0, 1));
        board[0][6] = new Knight(Colour.BLACK, new Position(0, 6));
        board[7][1] = new Knight(Colour.WHITE, new Position(7, 1));
        board[7][6] = new Knight(Colour.WHITE, new Position(7, 6));
        // Place 
        board[0][2] = new Bishop(Colour.BLACK, new Position(0, 2));
        board[0][5] = new Bishop(Colour.BLACK, new Position(0, 5));
        board[7][2] = new Bishop(Colour.WHITE, new Position(7, 2));
        board[7][5] = new Bishop(Colour.WHITE, new Position(7, 5));
        // Place Queens
        board[0][3] = new Queen(Colour.BLACK, new Position(0, 3));
        board[7][3] = new Queen(Colour.WHITE, new Position(7, 3));
        // Place Kings
        board[0][4] = new King(Colour.BLACK, new Position(0, 4));
        board[7][4] = new King(Colour.WHITE, new Position(7, 4));
        // Place Pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Colour.BLACK, new Position(1, i));
            board[6][i] = new Pawn(Colour.WHITE, new Position(6, i));
        }
    }

    public void resetBoard(){
        board = new Piece[8][8];
        setUpPieces();
    }

    public Piece getPiece(int row, int col){
        return board[row][col];
    }

    @Override
    public String toString() {
        String strBoard = "";
        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece == null) {
                    strBoard += ". ";
                }
                else if (piece.getColour() == Colour.WHITE) {
                    if (piece instanceof Pawn) strBoard += "P ";
                    else if (piece instanceof Knight) strBoard += "N ";
                    else if (piece instanceof Rook) strBoard += "R ";
                    else if (piece instanceof Bishop) strBoard += "B ";
                    else if (piece instanceof King) strBoard += "K ";
                    else if (piece instanceof Queen) strBoard += "Q ";
                } 
                else if (piece.getColour() == Colour.BLACK) {
                    if (piece instanceof Pawn) strBoard += "p ";
                    else if (piece instanceof Knight) strBoard += "n ";
                    else if (piece instanceof Rook) strBoard += "r ";
                    else if (piece instanceof Bishop) strBoard += "b ";
                    else if (piece instanceof King) strBoard += "k ";
                    else if (piece instanceof Queen) strBoard += "q ";
                } 
            }
            strBoard += "\n";
        }
        return strBoard;
    }
}
