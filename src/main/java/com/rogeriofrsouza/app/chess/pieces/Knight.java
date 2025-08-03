package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessMoveDirection;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.Color;
import com.rogeriofrsouza.app.chess.Name;

import java.util.List;

public class Knight extends ChessPiece {

    private static final List<ChessMoveDirection> CHESS_MOVE_DIRECTIONS = ChessMoveDirection.getEntries()
            .stream()
            .filter(c -> c.name().startsWith("KNIGHT"))
            .toList();

    public Knight(Board board, Color color) {
        super(board, color, Name.KNIGHT, CHESS_MOVE_DIRECTIONS);
    }
}
