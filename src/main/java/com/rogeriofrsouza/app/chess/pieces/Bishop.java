package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessMoveDirection;
import com.rogeriofrsouza.app.chess.ChessPiece;

import java.util.List;

public class Bishop extends ChessPiece {

    private static final List<ChessMoveDirection> CHESS_MOVE_DIRECTIONS = List.of(
        ChessMoveDirection.UP_LEFT, ChessMoveDirection.UP_RIGHT,
        ChessMoveDirection.DOWN_LEFT, ChessMoveDirection.DOWN_RIGHT);

    public Bishop(Board board, Color color) {
        super(board, color, Name.BISHOP, CHESS_MOVE_DIRECTIONS);
    }
}
