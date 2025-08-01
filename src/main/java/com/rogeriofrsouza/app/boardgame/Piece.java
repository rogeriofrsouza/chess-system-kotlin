package com.rogeriofrsouza.app.boardgame;

import java.util.Objects;

public abstract class Piece {

    protected Position position;
    private final Board board;

    protected Piece(Board board) {
        this.board = board;
    }

    public abstract boolean[][] computePossibleMoves();

    public boolean isTargetPossibleMove(Position target) {
        return computePossibleMoves()[target.getRow()][target.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] possibleMoves = computePossibleMoves();

        for (boolean[] row : possibleMoves) {
            for (boolean move : row) {
                if (move) {
                    return true;
                }
            }
        }

        return false;
    }

    public Position getPosition() {
        return this.position;
    }

    public Board getBoard() {
        return this.board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
