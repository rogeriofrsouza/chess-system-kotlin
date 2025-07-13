package com.rogeriofrsouza.app.boardgame;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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
}
