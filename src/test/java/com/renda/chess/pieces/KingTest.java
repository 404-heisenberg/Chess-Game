package com.renda.chess.pieces;

import org.junit.jupiter.api.*;

import com.renda.chess.pieces.Piece;
import com.renda.chess.Colour;
import com.renda.chess.Position;
import com.renda.chess.pieces.Pawn;
import com.renda.chess.pieces.King;

public class KingTest {

    private static Piece[][] board = new Piece[8][8];

    @BeforeAll
    public static void setUpPieces(){
        // white pieces
        board[3][2] = new King(Colour.WHITE, new Position(3, 2));
        board[2][1] = new Pawn(Colour.WHITE, new Position(2, 1));

        // black pieces
        board[1][2] = new King(Colour.BLACK, new Position(1, 2));
        board[0][3] = new Pawn(Colour.BLACK, new Position(0, 3));
        board[4][3] = new Pawn(Colour.BLACK, new Position(4, 3));
    }

    // test valid forward move white king
    @Test
    public void testValidForwardMoveWhiteKing() {
        boolean isValid = board[3][2].isValidMove(new Position(2,2), board);
        Assertions.assertTrue(isValid);
    }

    // test valid backward move white king
    @Test
    public void testValidBackwardMoveWhiteKing() {
        boolean isValid = board[3][2].isValidMove(new Position(4,2), board);
        Assertions.assertTrue(isValid);
    }

    // test valid left move white king
    @Test
    public void testValidLeftMoveWhiteKing() {
        boolean isValid = board[3][2].isValidMove(new Position(3,1), board);
        Assertions.assertTrue(isValid);
    }

    // test valid right move white king
    @Test
    public void testValidRightMoveWhiteKing() {
        boolean isValid = board[3][2].isValidMove(new Position(3,3), board);
        Assertions.assertTrue(isValid);
    }

    // test valid forward diagonal move white king
    @Test
    public void testValidForwardDiagonalMoveWhiteKing() {
        boolean isValid = board[3][2].isValidMove(new Position(2,3), board);
        Assertions.assertTrue(isValid);
    }

    // test valid backward diagonal move white king
    @Test
    public void testValidBackwardDiagonalMoveWhiteKing() {
        boolean isValid = board[3][2].isValidMove(new Position(4,1), board);
        Assertions.assertTrue(isValid);
    }

    // test invalid move white king
    @Test
    public void testInvalidMoveWhiteKing() {
        boolean isValid = board[3][2].isValidMove(new Position(5,2), board);
        Assertions.assertFalse(isValid, "White king should not be able to move two squares");
    }

    // test valid white capture
    @Test
    public void testValidWhiteCapture() {
        boolean isValid = board[3][2].isValidMove(new Position(4,3), board);
        Assertions.assertTrue(isValid);
    }

    // test blocked white move
    @Test
    public void testBlockedWhiteMove() {
        boolean isValid = board[3][2].isValidMove(new Position(2,1), board);
        Assertions.assertFalse(isValid, "White king should not be able to move if blocked");
    }

    // test valid forward move black king
    @Test
    public void testValidForwardMoveBlackKing() {
        boolean isValid = board[1][2].isValidMove(new Position(2,2), board);
        Assertions.assertTrue(isValid);
    }

    // test valid backward move black king
    @Test
    public void testValidBackwardMoveBlackKing() {
        boolean isValid = board[1][2].isValidMove(new Position(0,2), board);
        Assertions.assertTrue(isValid);
    }

    // test valid left move black king
    @Test
    public void testValidLeftMoveBlackKing() {
        boolean isValid = board[1][2].isValidMove(new Position(1,1), board);
        Assertions.assertTrue(isValid);
    }

    // test valid right move black king
    @Test
    public void testValidRightMoveBlackKing() {
        boolean isValid = board[1][2].isValidMove(new Position(1,3), board);
        Assertions.assertTrue(isValid);
    }

    // test valid forward diagonal move black king
    @Test
    public void testValidForwardDiagonalMoveBlackKing() {
        boolean isValid = board[1][2].isValidMove(new Position(2,3), board);
        Assertions.assertTrue(isValid);
    }

    // test valid backward diagonal move black king
    @Test
    public void testValidBackwardDiagonalMoveBlackKing() {
        boolean isValid = board[1][2].isValidMove(new Position(0,1), board);
        Assertions.assertTrue(isValid);
    }

    // test invalid move black king
    @Test
    public void testInvalidMoveBlackKing() {
        boolean isValid = board[1][2].isValidMove(new Position(3,2), board);
        Assertions.assertFalse(isValid, "Black king should not be able to move two squares");
    }

    // test valid black capture
    @Test
    public void testValidBlackCapture() {
        boolean isValid = board[1][2].isValidMove(new Position(2,1), board);
        Assertions.assertTrue(isValid);
    }

    // test blocked black move
    @Test
    public void testBlockedBlackMove() {
        boolean isValid = board[1][2].isValidMove(new Position(0,3), board);
        Assertions.assertFalse(isValid, "Black king should not be able to move if blocked");
    }
}
