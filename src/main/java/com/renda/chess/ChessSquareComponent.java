package com.renda.chess;

import javax.swing.*;
import javax.swing.SwingConstants;
import java.awt.*;

/**
 * The ChessSquareComponent class is responsible for creating the chessboard squares.
 */

public class ChessSquareComponent extends JButton {
    private int row;
    private int col;

    public ChessSquareComponent(int row, int col) {
        this.row = row;
        this.col = col;
        setFocusPainted(false);
        initButton();
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    // set the background colour of every square
    private void initButton(){
        setPreferredSize(new Dimension(64, 64));

        if ((row + col) % 2 == 0){
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(new Color(205, 133, 63));
        }

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        setFont(new Font("Serif", Font.BOLD, 36));
    }

    public void setPieceSymbol(String symbol, Colour colour){
        this.setText(symbol);
        Color symbolColour = (colour == Colour.WHITE) ? Color.WHITE : Color.BLACK;
        this.setForeground(symbolColour);
    }

    public void clearPieceSymbol(){
        this.setText(" ");
    }
}
