package com.rogeriofrsouza.app.chess;

public enum Name {

    ROOK("R"),
    KNIGHT("N"),
    BISHOP("B"),
    QUEEN("Q"),
    KING("K"),
    PAWN("P");

    private final String letter;

    Name(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }
}
