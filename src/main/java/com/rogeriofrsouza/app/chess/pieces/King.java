package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch; // Dependência para a partida

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch; // Associação entre os objetos
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // above
        p.setValues(getPosition().getRow() - 1, getPosition().getColumn());

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // below
        p.setValues(getPosition().getRow() + 1, getPosition().getColumn());

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // left
        p.setValues(getPosition().getRow(), getPosition().getColumn() - 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // right
        p.setValues(getPosition().getRow(), getPosition().getColumn() + 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // north-west
        p.setValues(getPosition().getRow() - 1, getPosition().getColumn() - 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // north-east
        p.setValues(getPosition().getRow() - 1, getPosition().getColumn() + 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // south-west
        p.setValues(getPosition().getRow() + 1, getPosition().getColumn() - 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // south-east
        p.setValues(getPosition().getRow() + 1, getPosition().getColumn() + 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Special move: Castling
        if (getMoveCount() == 0 && !chessMatch.isCheck()) {

            // Kingside rook
            Position posT1 = new Position(getPosition().getRow(), getPosition().getColumn() + 3);

            if (testRookCastling(posT1)) {
                Position p1 = new Position(getPosition().getRow(), getPosition().getColumn() + 1);
                Position p2 = new Position(getPosition().getRow(), getPosition().getColumn() + 2);

                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    mat[getPosition().getRow()][getPosition().getColumn() + 2] = true;
                }
            }

            // Queenside rook
            Position posT2 = new Position(getPosition().getRow(), getPosition().getColumn() - 4);

            if (testRookCastling(posT2)) {
                Position p1 = new Position(getPosition().getRow(), getPosition().getColumn() - 1);
                Position p2 = new Position(getPosition().getRow(), getPosition().getColumn() - 2);
                Position p3 = new Position(getPosition().getRow(), getPosition().getColumn() - 3);

                if (getBoard().piece(p1) == null
                        && getBoard().piece(p2) == null
                        && getBoard().piece(p3) == null) {
                    mat[getPosition().getRow()][getPosition().getColumn() - 2] = true;
                }
            }
        }

        return mat;
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);

        return p == null || p.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);

        return p != null
                && p instanceof Rook
                && p.getColor() == getColor()
                && p.getMoveCount() == 0;
    }
}
