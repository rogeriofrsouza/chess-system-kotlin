package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessMoveDirection;
import com.rogeriofrsouza.app.chess.ChessPiece;

import java.util.List;

public class Rook extends ChessPiece {

    private static final List<ChessMoveDirection> CHESS_MOVE_DIRECTIONS = List.of(
        ChessMoveDirection.UP, ChessMoveDirection.RIGHT,
        ChessMoveDirection.DOWN, ChessMoveDirection.LEFT);

    public Rook(Board board, Color color) {
        super(board, color, Name.ROOK, CHESS_MOVE_DIRECTIONS);
    }
}
