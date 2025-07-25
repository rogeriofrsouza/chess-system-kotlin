package com.rogeriofrsouza.app.ui;

import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;

import java.util.List;

public class Display {

    public void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces(), null);

        List<ChessPiece> white = captured.stream()
            .filter(piece -> piece.getColor() == ChessPiece.Color.WHITE)
            .toList();

        List<ChessPiece> black = captured.stream()
            .filter(piece -> piece.getColor() == ChessPiece.Color.BLACK)
            .toList();

        System.out.println("\nCaptured pieces");

        System.out.printf("White: %s%s%n%sBlack: %s%s%n%s",
            AnsiEscapeCode.WHITE, white, AnsiEscapeCode.RESET, AnsiEscapeCode.YELLOW, black, AnsiEscapeCode.RESET);

        System.out.println("\nTurn: " + chessMatch.getTurn());

        if (chessMatch.isCheckMate()) {
            System.out.println("CHECKMATE!\nWinner: " + chessMatch.getCurrentPlayer());
            return;
        }

        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());

        if (chessMatch.isCheck()) {
            System.out.println("CHECK!");
        }
    }

    public void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        clearScreen();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < pieces.length; i++) {
            stringBuilder.append((8 - i)).append(" ");

            for (int j = 0; j < pieces[i].length; j++) {
                if (possibleMoves != null && possibleMoves[i][j]) {
                    stringBuilder.append(AnsiEscapeCode.BLUE_BACKGROUND);
                }

                if (pieces[i][j] == null) {
                    stringBuilder.append("-");
                } else {
                    String color = pieces[i][j].getColor() == ChessPiece.Color.WHITE
                        ? AnsiEscapeCode.WHITE
                        : AnsiEscapeCode.YELLOW;
                    stringBuilder.append(color).append(pieces[i][j]);
                }

                stringBuilder.append(AnsiEscapeCode.RESET + " ");
            }

            stringBuilder.append("\n");
        }

        stringBuilder.append("  a b c d e f g h");
        System.out.println(stringBuilder);
    }

    private void clearScreen() {
        System.out.print(AnsiEscapeCode.MOVE_CURSOR_HOME + AnsiEscapeCode.CLEAR_SCREEN);
    }
}
