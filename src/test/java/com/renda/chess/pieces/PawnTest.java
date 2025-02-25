package com.renda.chess.pieces;

import org.junit.jupiter.api.*;

import com.renda.chess.Colour;
import com.renda.chess.ChessBoard;
import com.renda.chess.Position;
import com.renda.chess.pieces.Pawn;
import com.renda.chess.pieces.Piece;

public class PawnTest {

    private static ChessBoard chessBoard;
    private static Piece[][] board;

    @BeforeAll
    public static void setUp() {
        chessBoard = new ChessBoard();
        board = chessBoard.getBoard();
    }

    // test black forward direction
    @Test
    public void testBlackForwardDirection() {
        int actualDirection = ((Pawn) board[1][1]).detForwardDirection();
        int expectedDirection = 1;
        Assertions.assertEquals(expectedDirection, actualDirection, "Black forward direction should be 1");
    }

    // test white forward direction
    @Test
    public void testWhiteForwardDirection() {
        int actualDirection = ((Pawn) board[6][1]).detForwardDirection();
        int expectedDirection = -1;
        Assertions.assertEquals(expectedDirection, actualDirection, "White forward direction should be -1");
    }

    // test forward move for white pawn
    @Test
    public void testWhiteForwardMove() {
        Position newPosition = new Position(5, 1);
        boolean isValid = board[6][1].isValidMove(newPosition, board);
        Assertions.assertTrue(isValid, "White pawn should be able to move forward one square");
    }

    // test forward move for black pawn
    @Test
    public void testBlackForwardMove() {
        Position newPosition = new Position(2, 1);
        boolean isValid = board[1][1].isValidMove(newPosition, board);
        Assertions.assertTrue(isValid, "Black pawn should be able to move forward one square");
    }

    // test diagonal move for white pawn
    @Test
    public void testWhiteDiagonalMove() {
        Position newPosition = new Position(5, 2);
        boolean isValid = board[6][1].isValidMove(newPosition, board);
        Assertions.assertFalse(isValid, "White pawn should not be able to move diagonally without capturing");
    }

    // test diagonal move for black pawn
    @Test
    public void testBlackDiagonalMove() {
        Position newPosition = new Position(2, 2);
        boolean isValid = board[1][1].isValidMove(newPosition, board);
        Assertions.assertFalse(isValid, "Black pawn should not be able to move diagonally without capturing");
    }

    // test two-square move for white pawn
    @Test
    public void testWhiteTwoSquareMove() {
        Position newPosition = new Position(4, 1);
        boolean isValid = board[6][1].isValidMove(newPosition, board);
        Assertions.assertTrue(isValid, "White pawn should be able to move two squares on first move");
    }

    // test two-square move for black pawn
    @Test
    public void testBlackTwoSquareMove() {
        Position newPosition = new Position(3, 1);
        boolean isValid = board[1][1].isValidMove(newPosition, board);
        Assertions.assertTrue(isValid, "Black pawn should be able to move two squares on first move");
    }

    // test two-square move for white pawn after first move
    @Test
    public void testWhiteTwoSquareMoveAfterFirstMove() {
        board[5][5] = new Pawn(Colour.WHITE, new Position(5, 5));
        Position newPosition = new Position(3, 5);
        boolean isValid = board[5][5].isValidMove(newPosition, board);
        Assertions.assertFalse(isValid, "White pawn should not be able to move two squares after first move");
    }

    // test two-square move for black pawn after first move
    @Test
    public void testBlackTwoSquareMoveAfterFirstMove() {
        board[2][2] = new Pawn(Colour.BLACK, new Position(2, 2));
        Position newPosition = new Position(4, 2);
        boolean isValid = board[2][2].isValidMove(newPosition, board);
        Assertions.assertFalse(isValid, "Black pawn should not be able to move two squares after first move");
    }

    // test two-square move for white pawn with piece in between
    @Test
    public void testWhiteTwoSquareMoveWithPieceInBetween() {
        board[5][1] = new Pawn(Colour.WHITE, new Position(5, 1));
        boolean isValid = board[6][1].isValidMove(new Position(4, 1), board);
        Assertions.assertFalse(isValid, "White pawn should not be able to move two squares with piece in between");
    }

    // test two-square move for black pawn with piece in between
    @Test
    public void testBlackTwoSquareMoveWithPieceInBetween() {
        board[2][1] = new Pawn(Colour.BLACK, new Position(2, 1));
        boolean isValid = board[1][1].isValidMove(new Position(3, 1), board);
        Assertions.assertFalse(isValid, "Black pawn should not be able to move two squares with piece in between");
    }

    // test diagonal capture for white
    @Test
    public void testWhiteDiagonalCapture() {
        board[5][6] = new Pawn(Colour.BLACK, new Position(5, 6));
        boolean isValid = board[6][5].isValidMove(new Position(5, 6), board);
        Assertions.assertTrue(isValid, "White pawn should be able to capture diagonally");
    }

    // test blocked diagonal move for white
    @Test
    public void testWhiteBlockedDiagonalMove() {
        board[5][4] = new Pawn(Colour.WHITE, new Position(5, 4));
        boolean isValid = board[6][5].isValidMove(new Position(5, 4), board);
        Assertions.assertFalse(isValid, "White pawn should not be able to move diagonally if blocked");
    }

    // test diagonal capture for black
    @Test
    public void testBlackDiagonalCapture() {
        board[2][6] = new Pawn(Colour.WHITE, new Position(2, 6));
        boolean isValid = board[1][5].isValidMove(new Position(2, 6), board);
        Assertions.assertTrue(isValid, "Black pawn should be able to capture diagonally");
    }

    // test blocked diagonal move for black
    @Test
    public void testBlackBlockedDiagonalMove() {
        board[2][4] = new Pawn(Colour.BLACK, new Position(2, 4));
        boolean isValid = board[1][5].isValidMove(new Position(2, 4), board);
        Assertions.assertFalse(isValid, "Black pawn should not be able to move diagonally if blocked");
    }

    // test white pawn can't move backwards
    @Test
    public void testWhiteBackwardMove() {
        board[3][6] = new Pawn(Colour.WHITE, new Position(3, 6));
        boolean isValid = board[3][6].isValidMove(new Position(4, 6), board);
        Assertions.assertFalse(isValid, "White pawn should not be able to move backwards");
    }

    // test black pawn can't move backwards
    @Test
    public void testBlackBackwardMove() {
        board[3][7] = new Pawn(Colour.BLACK, new Position(3, 7));
        boolean isValid = board[3][7].isValidMove(new Position(2, 7), board);
        Assertions.assertFalse(isValid, "Black pawn should not be able to move backwards");
    }
}
