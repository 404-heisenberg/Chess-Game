package com.renda.chess.pieces;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.*;

import com.renda.chess.pieces.Piece;
import com.renda.chess.pieces.Bishop;
import com.renda.chess.Colour;
import com.renda.chess.Position;

import java.util.*;

public class BishopTest {

    private static Piece[][] board = new Piece[8][8];

    @BeforeAll
    private static void setUpPieces(){
        // black pieces 
        board[1][0] = new Pawn(Colour.BLACK, new Position(1,0));
        board[6][5] = new Pawn(Colour.BLACK, new Position(6,5));
        board[2][3] = new Bishop(Colour.BLACK, new Position(2,3));
        board[4][5] = new Pawn(Colour.BLACK, new Position(4,5));

        // white pieces
        board[2][1] = new Pawn(Colour.WHITE, new Position(2,1));
        board[4][3] = new Bishop(Colour.WHITE, new Position(4,3));
        board[0][1] = new Pawn(Colour.WHITE, new Position(0,1));
        board[5][6] = new Pawn(Colour.WHITE, new Position(5,6));
    }

    // test getting the correct legal moves
    @Test
    public void testGetLegalMoveList(){
        int expectedLength  = 8;
        ArrayList<Position> legalMoves = board[2][3].getLegalMoveList(board);
        int actualLength = legalMoves.size();
        Assertions.assertEquals(expectedLength, actualLength);
    }

    // test non diagonal white bishop move
    @Test
    public void testInvalidWhiteMove(){
        Assertions.assertFalse(board[4][3].isValidMove(new Position(4,5), board)); // invalid horizontal move
        Assertions.assertFalse(board[4][3].isValidMove(new Position(6,3), board)); // invalid vertical move
    }

    // test diagonal forward white bishop move
    @Test
    public void testWhiteValidForwardMove(){
        Assertions.assertTrue(board[4][3].isValidMove(new Position(1, 6), board));
    }

    // test diagonal backward white bishop move
    @Test
    public void testwhiteValidBackwardMove(){
        Assertions.assertTrue(board[4][3].isValidMove(new Position(6, 1), board));
    }

    // test diagonal white bishop capture
    @Test
    public void testWhiteValidCapture(){
        Assertions.assertTrue(board[4][3].isValidMove(new Position(6, 5), board));
    }

    // test blocked diagonal move
    @Test
    public void testWhiteBlockedMove(){
        Assertions.assertFalse(board[4][3].isValidMove(new Position(1,0), board));
    }

    // test non diagonal black bishop move
    @Test
    public void testInvalidBlackMove(){
        Assertions.assertFalse(board[2][3].isValidMove(new Position(2,5), board)); // invalid horizontal move
        Assertions.assertFalse(board[2][3].isValidMove(new Position(3,3), board)); // invalid vertical move
    }

    // test diagonal forward black bishop move
    @Test
    public void testBlackValidForwardMove(){
        Assertions.assertTrue(board[2][3].isValidMove(new Position(5, 0), board));
    }

    // test diagonal backward black bishop move
    @Test
    public void testBlackValidBackwardMove(){
        Assertions.assertTrue(board[2][3].isValidMove(new Position(0, 5), board));
    }

    // test diagonal black bishop capture
    @Test
    public void testBlackValidCapture(){
        Assertions.assertTrue(board[2][3].isValidMove(new Position(0, 1), board));
    }

    // test blocked diagonal move
    @Test
    public void testBlackBlockedMove(){
        Assertions.assertFalse(board[2][3].isValidMove(new Position(5,6), board));
    }

}
