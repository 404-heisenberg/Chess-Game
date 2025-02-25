package com.renda.chess.pieces;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.renda.chess.pieces.Knight;
import com.renda.chess.pieces.Piece;
import com.renda.chess.pieces.Pawn;
import com.renda.chess.Colour;
import com.renda.chess.Position;

public class KnightTest {
    private static Piece[][] board = new Piece[8][8];

    @BeforeAll
    public static void setUp(){
        board[2][3] = new Knight(Colour.WHITE, new Position(2, 3));
        board[3][1] = new Pawn(Colour.WHITE, new Position(3, 1));
        board[0][3] = new Pawn(Colour.BLACK, new Position(0, 3));
        board[1][5] = new Pawn(Colour.BLACK, new Position(1, 5));
    }

    // test invalid move
    @Test
    public void testInvalidKnightMove(){
        boolean isValid = board[2][3].isValidMove(new Position(4, 3), board);
        assertFalse(isValid);
    }

    // test blocked knight move
    @Test
    public void testBlockedKnightMove(){
        boolean isValid = board[2][3].isValidMove(new Position(3, 1), board);
        assertFalse(isValid);
    }

    // test valid capture
    @Test 
    public void testValidKnightCapture(){
        boolean isValid = board[2][3].isValidMove(new Position(1, 5), board);
        assertTrue(isValid);
    }

    // test valid move
    @Test
    public void testValidKnightMove(){
        boolean isValid = board[2][3].isValidMove(new Position(0, 4), board);
        assertTrue(isValid);
    }
}
