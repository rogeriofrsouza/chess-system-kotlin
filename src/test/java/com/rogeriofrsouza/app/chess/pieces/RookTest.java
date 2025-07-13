package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.ChessPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(MockitoExtension.class)
class RookTest {

    @Test
    @DisplayName("possible moves for a Rook given specific board setup")
    void computePossibleMoves() {
        Board board = new Board(8, 8);
        Rook rook = new Rook(board, ChessPiece.Color.WHITE);
        Knight knight = new Knight(board, ChessPiece.Color.WHITE);
        Bishop bishop = new Bishop(board, ChessPiece.Color.BLACK);

        board.placePiece(rook, new ChessPosition('b', 3).toPosition());
        board.placePiece(knight, new ChessPosition('b', 1).toPosition());
        board.placePiece(bishop, new ChessPosition('b', 7).toPosition());

        boolean[][] possibleMovesExpected = new boolean[8][8];
        for (boolean[] arr : possibleMovesExpected) {
            Arrays.fill(arr, false);
        }

        Arrays.fill(possibleMovesExpected[5], true);
        possibleMovesExpected[5][1] = false;
        possibleMovesExpected[6][1] = true;
        possibleMovesExpected[4][1] = true;
        possibleMovesExpected[3][1] = true;
        possibleMovesExpected[2][1] = true;
        possibleMovesExpected[1][1] = true;

        assertArrayEquals(possibleMovesExpected, rook.computePossibleMoves());
    }
}
