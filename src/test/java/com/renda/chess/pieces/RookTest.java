package com.renda.chess.pieces;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.renda.chess.pieces.Rook;
import com.renda.chess.pieces.Pawn;
import com.renda.chess.pieces.Piece;
import com.renda.chess.Colour;
import com.renda.chess.Position;

public class RookTest {
    private static Piece[][] board = new Piece[8][8];

    @BeforeAll
    public static void setUp() {
        // set up white pieces
        board[5][3] = new Rook(Colour.WHITE, new Position(5, 3));
        board[5][1] = new Pawn(Colour.WHITE, new Position(5, 1));
        board[2][0] = new Pawn(Colour.WHITE, new Position(2, 0));
        board[2][6] = new Pawn(Colour.WHITE, new Position(2, 6));

        // set up black pieces
        board[2][3] = new Rook(Colour.BLACK, new Position(2, 3));
        board[2][5] = new Pawn(Colour.BLACK, new Position(2, 5));
        board[5][0] = new Pawn(Colour.BLACK, new Position(5, 0));
        board[5][6] = new Pawn(Colour.BLACK, new Position(5, 6));
    }

    // test white rook moving forward
    @Test 
    public void testWhiteRookForwardMove(){
        boolean isValid = board[5][3] .isValidMove(new Position(2, 3), board);
        assertTrue(isValid);
    }

    // test white rook backwards move
    @Test 
    public void testWhiteRookBackwardMove(){
        boolean isValid = board[5][3] .isValidMove(new Position(7, 3), board);
        assertTrue(isValid);
    }

    // test white rook right move
    @Test
    public void testWhiteRookRightMove(){
        boolean isValid = board[5][3] .isValidMove(new Position(5, 6), board);
        assertTrue(isValid);
    }

    // test white rook blocked left move
    @Test
    public void testWhiteRookLeftMove(){
        boolean isValid = board[5][3].isValidMove(new Position(5, 3), board);
        assertFalse(isValid);
    }

    // test black rook forward move
    @Test
    public void testBlackRookForwardMove(){
        boolean isValid = board[2][3].isValidMove(new Position(5, 3), board);
        assertTrue(isValid);
    }

    // test black rook backwards move
    @Test
    public void testBlackRookBackwardMove(){
        boolean isValid = board[2][3].isValidMove(new Position(0, 3), board);
        assertTrue(isValid);
    }

    // test black rook left move
    @Test
    public void testBlockRookBlackLeftMove(){
        boolean isValid = board[2][3].isValidMove(new Position(2, 0), board);
        assertTrue(isValid);
    }

    // test black rook blocked right move
    @Test
    public void testBlackRookBlockedRightMove(){
        boolean isValid = board[2][3].isValidMove(new Position(2, 6), board);
        assertFalse(isValid);
    }

    // test black rook diagonal move
    @Test
    public void testBlackRookDiagMove(){
        boolean isValid = board[2][3].isValidMove(new Position(5, 6), board);
        assertFalse(isValid);
    }

    // test white rook diagonal move
    @Test
    public void testWhiteRookDiagMove(){
        boolean isValid = board[5][3].isValidMove(new Position(4, 5), board);
        assertFalse(isValid);
    }
}
