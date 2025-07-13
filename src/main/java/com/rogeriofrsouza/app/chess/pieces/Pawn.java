package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch; // Dependência para a partida

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch; // Associação entre os objetos
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // White pawn
        if (getColor() == Color.WHITE) {
            p.setValues(position.getRow() - 1, position.getColumn());

            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            Position p2 = p;
            p.setValues(position.getRow() - 2, position.getColumn());

            if (getBoard().positionExists(p)
                    && !getBoard().thereIsAPiece(p)
                    && getBoard().positionExists(p2)
                    && !getBoard().thereIsAPiece(p2)
                    && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() - 1, position.getColumn() - 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() - 1, position.getColumn() + 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // Special move: En Passant
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);

                if (getBoard().positionExists(left)
                        && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    mat[left.getRow() - 1][left.getColumn()] = true;
                }

                Position right = new Position(position.getRow(), position.getColumn() + 1);

                if (getBoard().positionExists(right)
                        && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                    mat[right.getRow() - 1][right.getColumn()] = true;
                }
            }
        }
        // Black pawn
        else {
            p.setValues(position.getRow() + 1, position.getColumn());

            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            Position p2 = p;
            p.setValues(position.getRow() + 2, position.getColumn());

            if (getBoard().positionExists(p)
                    && !getBoard().thereIsAPiece(p)
                    && getBoard().positionExists(p2)
                    && !getBoard().thereIsAPiece(p2)
                    && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() + 1, position.getColumn() - 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() + 1, position.getColumn() + 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // Special move: En Passant
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);

                if (getBoard().positionExists(left)
                        && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    mat[left.getRow() + 1][left.getColumn()] = true;
                }

                Position right = new Position(position.getRow(), position.getColumn() + 1);

                if (getBoard().positionExists(right)
                        && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                    mat[right.getRow() + 1][right.getColumn()] = true;
                }
            }
        }

        return mat;
    }
}
