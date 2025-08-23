package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.Color;

public class Pawn extends ChessPiece {

    private final ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] computePossibleMoves() {
        Position position = new Position(0, 0);
        Board board = getBoard();

        // White pawn
        if (getColor() == Color.WHITE) {
            position.setValues(getPosition().getRow() - 1, getPosition().getColumn());

            if (board.positionExists(position) && !board.thereIsAPiece(position)) {
                board.makeSquarePossibleMove(position);

                position.setValues(getPosition().getRow() - 2, getPosition().getColumn());

                if (board.positionExists(position) && !board.thereIsAPiece(position) && getMoveCount() == 0) {
                    board.makeSquarePossibleMove(position);
                }
            }

            position.setValues(getPosition().getRow() - 1, getPosition().getColumn() - 1);

            if (board.positionExists(position) && isThereOpponentPiece(position)) {
                board.makeSquarePossibleMove(position);
            }

            position.setValues(getPosition().getRow() - 1, getPosition().getColumn() + 1);

            if (board.positionExists(position) && isThereOpponentPiece(position)) {
                board.makeSquarePossibleMove(position);
            }

            // Special move: En Passant
            if (getPosition().getRow() == 3) {
                position.setValues(getPosition().getRow(), getPosition().getColumn() - 1);

                if (board.positionExists(position)
                        && isThereOpponentPiece(position)
                        && board.piece(position) == chessMatch.getEnPassantVulnerable()) {
                    position.setRow(position.getRow() - 1);
                    board.makeSquarePossibleMove(position);
                }

                position.setValues(getPosition().getRow(), getPosition().getColumn() + 1);

                if (board.positionExists(position)
                        && isThereOpponentPiece(position)
                        && board.piece(position) == chessMatch.getEnPassantVulnerable()) {
                    position.setRow(position.getRow() - 1);
                    board.makeSquarePossibleMove(position);
                }
            }
        }
        // Black pawn
        else {
            position.setValues(getPosition().getRow() + 1, getPosition().getColumn());

            if (board.positionExists(position) && !board.thereIsAPiece(position)) {
                board.makeSquarePossibleMove(position);

                position.setValues(getPosition().getRow() + 2, getPosition().getColumn());

                if (board.positionExists(position) && !board.thereIsAPiece(position) && getMoveCount() == 0) {
                    board.makeSquarePossibleMove(position);
                }
            }

            position.setValues(getPosition().getRow() + 1, getPosition().getColumn() - 1);

            if (board.positionExists(position) && isThereOpponentPiece(position)) {
                board.makeSquarePossibleMove(position);
            }

            position.setValues(getPosition().getRow() + 1, getPosition().getColumn() + 1);

            if (board.positionExists(position) && isThereOpponentPiece(position)) {
                board.makeSquarePossibleMove(position);
            }

            // Special move: En Passant
            if (getPosition().getRow() == 4) {
                position.setValues(getPosition().getRow(), getPosition().getColumn() - 1);

                if (board.positionExists(position)
                        && isThereOpponentPiece(position)
                        && board.piece(position) == chessMatch.getEnPassantVulnerable()) {
                    position.setRow(position.getRow() + 1);
                    board.makeSquarePossibleMove(position);
                }

                position.setValues(getPosition().getRow(), getPosition().getColumn() + 1);

                if (board.positionExists(position)
                        && isThereOpponentPiece(position)
                        && board.piece(position) == chessMatch.getEnPassantVulnerable()) {
                    position.setRow(position.getRow() + 1);
                    board.makeSquarePossibleMove(position);
                }
            }
        }

        return new boolean[0][0];
    }
}
