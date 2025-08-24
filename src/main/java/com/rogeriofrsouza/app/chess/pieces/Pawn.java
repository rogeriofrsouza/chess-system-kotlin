package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessMoveDirection;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pawn extends ChessPiece {

    private final List<ChessMoveDirection> moveDirections;
    private final ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);

        List<ChessMoveDirection> directions = new ArrayList<>(
                (color == Color.WHITE)
                        ? List.of(ChessMoveDirection.UP, ChessMoveDirection.UP_LEFT, ChessMoveDirection.UP_RIGHT)
                        : List.of(ChessMoveDirection.DOWN, ChessMoveDirection.DOWN_LEFT, ChessMoveDirection.DOWN_RIGHT));

        directions.addAll(List.of(ChessMoveDirection.EN_PASSANT_LEFT, ChessMoveDirection.EN_PASSANT_RIGHT));
        this.moveDirections = List.copyOf(directions);

        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] computePossibleMoves() {
        Position position = Optional.ofNullable(getPosition()).orElseThrow();
        Board board = getBoard();

        moveDirections.forEach(direction -> {
            Position target = position.offsetDirection(direction);

            if (!board.positionExists(target)) {
                return;
            }

            if (List.of(ChessMoveDirection.UP, ChessMoveDirection.DOWN).contains(direction)) {
                if (!board.thereIsAPiece(target)) {
                    board.makeSquarePossibleMove(target);

                    // Pawn can move two squares forward if it has not moved yet
                    if (getMoveCount() == 0) {
                        target = target.offsetDirection(direction);

                        if (!board.thereIsAPiece(target)) {
                            board.makeSquarePossibleMove(target);
                        }
                    }
                }

                return;
            }

            if (!isThereOpponentPiece(target)) {
                return;
            }

            if (direction.isEnPassantMove() && isEnPassantPosition(position)) {
                if (board.piece(target).equals(chessMatch.getEnPassantVulnerable())) {
                    Position enPassantTarget = getEnPassantTarget(direction, target);
                    board.makeSquarePossibleMove(enPassantTarget);
                }

                return;
            }

            board.makeSquarePossibleMove(target);
        });

        return new boolean[0][0];
    }

    private boolean isEnPassantPosition(Position position) {
        return (getColor() == Color.WHITE && position.getRow() == 3) ||
                (getColor() == Color.BLACK && position.getRow() == 4);
    }

    private Position getEnPassantTarget(ChessMoveDirection direction, Position target) {
        if (!direction.isEnPassantMove()) {
            throw new IllegalArgumentException("Direction is not an en passant move");
        }

        return target.offsetDirection(
                getColor() == Color.WHITE ? ChessMoveDirection.UP : ChessMoveDirection.DOWN);
    }
}
