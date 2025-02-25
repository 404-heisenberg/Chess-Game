package com.renda.chess;

import com.renda.chess.pieces.Piece;
import com.renda.chess.pieces.Pawn;
import com.renda.chess.pieces.Rook;
import com.renda.chess.pieces.Knight;
import com.renda.chess.pieces.Bishop;
import com.renda.chess.pieces.Queen;
import com.renda.chess.pieces.King;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * The ChessGameUI class is responsible for creating the chessboard UI and handling user input.
 * This is where the game is played.
 */


public class ChessGameUI  extends JFrame {
    private JLabel turnLabel;
    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private ChessGame game = new ChessGame();
    private Map<Class <? extends Piece>, String> pieceUnicodeMap = new HashMap() {
        {
            put(Pawn.class, "\u265F");
            put(Rook.class, "\u265C");
            put(Knight.class, "\u265E");
            put(Bishop.class, "\u265D");
            put(Queen.class, "\u265B");
            put(King.class, "\u265A");
        }
    };

    public ChessGameUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        addGameResetOption();
        initializeBoard();
        pack(); // Adjust window size to fit chessboard
        setSize(600, 600);
        setVisible(true);
    }

    private class MouseClickedListener extends MouseAdapter {
        private final int finalRow;
        private final int finalCol;

        public MouseClickedListener(int row, int col){
            finalRow = row;
            finalCol = col;
        }

        @Override
        public void mouseClicked(MouseEvent e){
            handleSquareClick(finalRow, finalCol);
        }
    }

    private void initializeBoard() {
        for (int row = 0; row <squares.length; row++){
            for (int col = 0; col < squares[row].length; col++){
                final int finalRow = row;
                final int finalCol = col;
                ChessSquareComponent square = new ChessSquareComponent(row, col);
                square.addMouseListener(new MouseClickedListener(finalRow, finalCol));
                add(square);
                squares[row][col] = square;
            }
        }
        refreshBoard();
    }

    // update each sqaure with the piece's symbol and colour
    private void refreshBoard() {
        ChessBoard board = game.getBoard();
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Piece piece = board.getPiece(row, col);

                if (piece != null) {
                    String symbol = pieceUnicodeMap.get(piece.getClass()); // get the piece's symbol based off its class
                    Colour colour = piece.getColour();
                    squares[row][col].setPieceSymbol(symbol, colour);
                } else {
                    squares[row][col].clearPieceSymbol();
                }
            }
        }
    }

    public void highlightSquare(int row, int col){
        Piece[][] board = game.getBoard().getBoard();
        if (board[row][col] != null) {
            squares[row][col].setBackground(Color.YELLOW);
        }
    }

    public void highlightLegalMoves(Position position){
        Piece[][] board = game.getBoard().getBoard();
        Colour colour = game.getCurrentPlayerColour();
        ArrayList<Position> legalMoves = game.getLegalMovesForPieceAt(position);
        for (Position move : legalMoves) {
            if (!game.isStillInCheck(colour, position, move)) {
                squares[move.getRow()][move.getCol()].setBackground(Color.GREEN);
            }
            // highlight possible captures in red
            if (board[move.getRow()][move.getCol()] != null){
                if (board[move.getRow()][move.getCol()].getColour() != board[position.getRow()][position.getCol()].getColour()){
                    squares[move.getRow()][move.getCol()].setBackground(Color.RED);
                }
            }
        }
    }

    public void clearHighlights(){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Color backColour = (row + col) % 2 == 0 ? Color.LIGHT_GRAY : new Color(205, 133, 63);
                squares[row][col].setBackground(backColour);
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        boolean moveMade = game.handleSquareSelection(row, col);
        clearHighlights();
        if (moveMade) { 
            // if a piece was moved, reset the board, check game states 
            refreshBoard();
            updateTurnLabel();
            checkGameState();
            checkGameOver();
            // if the move was a pawn promotion, prompt the user to select a piece
            handlePawnPromotion(row, col);
        } else if (!moveMade) {
            // only highlight squares for current player
            Colour currentPlayerColour = game.getCurrentPlayerColour();
            Piece selectedPiece = game.getBoard().getPiece(row, col);

            if (selectedPiece != null){
                if (currentPlayerColour == selectedPiece.getColour()){
                    highlightLegalMoves(new Position(row, col));
                    highlightSquare(row, col);
                }
            }
        }
        refreshBoard();
    }

    public void handlePawnPromotion(int row, int col){
        // check that a pawn is at the end of the board
        Piece piece = game.getBoard().getPiece(row, col);
        if (piece != null && piece instanceof Pawn){
            Pawn pawn = (Pawn) piece;
            if (pawn.isAtEndOfBoard()){
                // prompt the user to select a piece to promote the pawn to (either a queen, rook, knight or bishop of the same colour)
                showPromotionOptions(row, col);
            }
        }
    }

    public void showPromotionOptions(int row, int col){
        JDialog dialog = new JDialog(this, "Promote Pawn", true);
        // dialog.setLayout(new FlowLayout());
        dialog.setSize(380, 120);
        addDialogMenuItems(row, col, dialog);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private class QueenPromotionListener implements ActionListener {
        private final int row;
        private final int col;
        private final JDialog dialog;

        public QueenPromotionListener(int row, int col, JDialog dialog){
            this.row = row;
            this.col = col;
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            game.promotePawn(row, col, Queen.class);
            refreshBoard();
            dialog.dispose();
        }
    }

    private class RookPromotionListener implements ActionListener {
        private final int row;
        private final int col;
        private final JDialog dialog;

        public RookPromotionListener(int row, int col, JDialog dialog){
            this.row = row;
            this.col = col;
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            game.promotePawn(row, col, Rook.class);
            refreshBoard();
            dialog.dispose();
        }
    }

    private class KnightPromotionListener implements ActionListener {
        private final int row;
        private final int col;
        private final JDialog dialog;

        public KnightPromotionListener(int row, int col, JDialog dialog){
            this.row = row;
            this.col = col;
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            game.promotePawn(row, col, Knight.class);
            refreshBoard();
            dialog.dispose();
        }
    }

    private class BishopPromotionListener implements ActionListener {
        private final int row;
        private final int col;
        private final JDialog dialog;

        public BishopPromotionListener(int row, int col, JDialog dialog){
            this.row = row;
            this.col = col;
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            game.promotePawn(row, col, Bishop.class);
            refreshBoard();
            dialog.dispose();
        }
    }

    private void addDialogMenuItems(int row, int col, JDialog dialog){ 
        Colour playerColour = game.getCurrentPlayerColour();
        Color btnColor = (playerColour == Colour.WHITE) ? Color.BLACK : Color.WHITE;
        
        JButton queen = new JButton(pieceUnicodeMap.get(Queen.class));
        JButton rook = new JButton(pieceUnicodeMap.get(Rook.class));
        JButton knight = new JButton(pieceUnicodeMap.get(Knight.class));
        JButton bishop = new JButton(pieceUnicodeMap.get(Bishop.class));
        
        // Create a larger font specifically for chess symbols
        Font symbolFont = queen.getFont().deriveFont(36f);  
        
        JButton[] buttons = {queen, rook, knight, bishop};
        for (JButton button : buttons) {
            button.setForeground(btnColor);
            button.setBackground(Color.GRAY);
            button.setFocusPainted(false);
            button.setFont(symbolFont);
            // Make buttons square and larger
            button.setPreferredSize(new Dimension(70, 70));
            // Remove the default button margin to give the symbol more space
            button.setMargin(new Insets(0, 0, 0, 0));
        }

        queen.addActionListener(new QueenPromotionListener(row, col, dialog));
        rook.addActionListener(new RookPromotionListener(row, col, dialog));
        knight.addActionListener(new KnightPromotionListener(row, col, dialog));
        bishop.addActionListener(new BishopPromotionListener(row, col, dialog));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        for (JButton button : buttons) {
            panel.add(button);
        }

        dialog.add(panel);
    }

    private void updateTurnLabel(){
        Colour currentPlayer = game.getCurrentPlayerColour();
        turnLabel.setText(currentPlayer + "'s turn");
    }

    private void checkGameState() {
        Colour currentPlayer = game.getCurrentPlayerColour();
        boolean inCheck = game.isInCheck(currentPlayer);

        if (inCheck){
            JOptionPane.showMessageDialog(this, currentPlayer + " is in check!");        
        }
    }

    private void checkGameOver() {
        if (game.isCheckMate(game.getCurrentPlayerColour())) {
            int response = JOptionPane.showConfirmDialog(this, "Checkmate! Would you like to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        } else if (game.stalemate(game.getCurrentPlayerColour())) {
            int response = JOptionPane.showConfirmDialog(this, "Stalemate! Would you like to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            resetGame();
        }
    }  

    private void addGameResetOption() {
      JMenuBar menuBar = new JMenuBar();
      JMenu gameMenu = new JMenu("Game");
      JMenuItem resetItem = new JMenuItem("Reset");
      resetItem.addActionListener(new ResetListener());
      gameMenu.add(resetItem);
      turnLabel = new JLabel("WHITE's turn");
      menuBar.add(gameMenu);
      menuBar.add(turnLabel);
      setJMenuBar(menuBar);
    }

    private void resetGame() {
      game.resetGame();
      turnLabel.setText("WHITE's turn");
      refreshBoard();
    } 

    public static void main(String[] args) {
        Runnable runGameUI = new Runnable() {
            @Override
            public void run(){
                new ChessGameUI();
            }
        };

        SwingUtilities.invokeLater(runGameUI);
    }
}
