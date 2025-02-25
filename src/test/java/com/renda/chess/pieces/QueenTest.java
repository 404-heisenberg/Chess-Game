package com.renda.chess.pieces;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.renda.chess.pieces.Queen;
import com.renda.chess.pieces.Pawn;
import com.renda.chess.Colour;
import com.renda.chess.Position;
import com.renda.chess.pieces.Piece;

public class QueenTest {

    private static Piece[][] board = new Piece[8][8];

    @BeforeAll
    public static void setUp(){
        board[3][3] = new Queen(Colour.WHITE, new Position(3, 3));
        board[1][5] = new Pawn(Colour.WHITE, new Position(1, 5));
        board[3][5] = new Pawn(Colour.WHITE, new Position(3, 5));
        board[5][5] = new Pawn(Colour.WHITE, new Position(5, 5));
        board[5][3] = new Pawn(Colour.WHITE, new Position(5, 3));
    }

    // test valid forward move
    @Test
    public void testFowardMove(){
        boolean isValid = board[3][3].isValidMove(new Position(0, 3), board);
        assertTrue(isValid);
    }

    // test valid diag move
    @Test
    public void testDiagMove(){
        boolean isValid = board[3][3].isValidMove(new Position(0, 0), board);
        assertTrue(isValid);
    }

    // test valid left move
    @Test
    public void testLeftMove(){
        boolean isValid = board[3][3].isValidMove(new Position(3, 0), board);
        assertTrue(isValid);
    }

    // test valid backwards diag move
    @Test
    public void testBackwardsDiagMove(){
        boolean isValid = board[3][3].isValidMove(new Position(6, 0), board);
        assertTrue(isValid);
    }

    // test blocked backwards move
    @Test
    public void testBlockedBackwardsMove(){
        boolean isValid = board[3][3].isValidMove(new Position(6, 3), board);
        assertFalse(isValid);
    }

    // test blocked diag backwards move
    @Test
    public void testBlockedBackDiagMove(){
        boolean isValid = board[3][3].isValidMove(new Position(6, 6), board);
        assertFalse(isValid);
    }

    // test blocked right move
    @Test
    public void testBlockedRightMove(){
        boolean isValid = board[3][3].isValidMove(new Position(3, 6), board);
        assertFalse(isValid);
    }

    // test blocked diag move
    @Test
    public void testBlockDiagMove(){
        boolean isValid = board[3][3].isValidMove(new Position(0, 6), board);
        assertFalse(isValid);
    }

    // test invalid queen move
    @Test
    public void testInvalidMove(){
        boolean isValid = board[3][3].isValidMove(new Position(4, 1), board);
        assertFalse(isValid);
    }
}   
