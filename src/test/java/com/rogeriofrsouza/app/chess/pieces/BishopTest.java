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
class BishopTest {

    @Test
    @DisplayName("possible moves for a Bishop given specific board setup")
    void computePossibleMoves() {
        Board board = new Board(8, 8);
        Bishop bishop = new Bishop(board, ChessPiece.Color.WHITE);
        Rook rook = new Rook(board, ChessPiece.Color.WHITE);
        Knight knight = new Knight(board, ChessPiece.Color.BLACK);

        board.placePiece(bishop, new ChessPosition('b', 2).toPosition());
        board.placePiece(rook, new ChessPosition('a', 1).toPosition());
        board.placePiece(knight, new ChessPosition('f', 6).toPosition());

        boolean[][] possibleMovesExpected = new boolean[8][8];
        for (boolean[] arr : possibleMovesExpected) {
            Arrays.fill(arr, false);
        }

        possibleMovesExpected[7][2] = true;
        possibleMovesExpected[5][0] = true;
        possibleMovesExpected[5][2] = true;
        possibleMovesExpected[4][3] = true;
        possibleMovesExpected[3][4] = true;
        possibleMovesExpected[2][5] = true;

        assertArrayEquals(possibleMovesExpected, bishop.computePossibleMoves());
    }
}
