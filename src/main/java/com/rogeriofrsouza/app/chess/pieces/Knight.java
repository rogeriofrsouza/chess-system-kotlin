package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // above
        p.setValues(getPosition().getRow() - 2, getPosition().getColumn() - 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(getPosition().getRow() - 2, getPosition().getColumn() + 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // below
        p.setValues(getPosition().getRow() + 2, getPosition().getColumn() - 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(getPosition().getRow() + 2, getPosition().getColumn() + 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // left
        p.setValues(getPosition().getRow() - 1, getPosition().getColumn() - 2);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(getPosition().getRow() + 1, getPosition().getColumn() - 2);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // right
        p.setValues(getPosition().getRow() - 1, getPosition().getColumn() + 2);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(getPosition().getRow() + 1, getPosition().getColumn() + 2);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);

        return p == null || p.getColor() != getColor();
    }
}
