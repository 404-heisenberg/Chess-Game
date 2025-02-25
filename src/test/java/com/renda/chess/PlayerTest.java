package com.renda.chess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.renda.chess.pieces.Pawn;
import com.renda.chess.pieces.Piece;
import com.renda.chess.ChessBoard;
import com.renda.chess.Position;
import com.renda.chess.Colour;
import com.renda.chess.Player;

public class PlayerTest {

    private static ChessBoard chessBoard = new ChessBoard();
    private static Player player1 = new Player(Colour.WHITE);
    private static Player player2 = new Player(Colour.BLACK);
    private static Piece[][] board = chessBoard.getBoard();

    // test valid white pawn move
    @Test
    public void testValidWhitePawnMove(){
        Position currPosition = new Position(6, 4);
        Position newPosition = new Position(4, 4);

        player1.movePiece(board, currPosition, newPosition);

        assertEquals(Pawn.class, board[4][4].getClass(), "The piece should move to the new position");
        assertNull(board[6][4], "The old position should be empty");
    }

    // test invalid black move
    @Test 
    public void testInvalidBlackMove(){
        Position currPosition = new Position(0, 2);
        Position newPosition = new Position(3, 5);

        boolean isValid = player2.movePiece(board, currPosition, newPosition);

        assertFalse(isValid);
    }

}
