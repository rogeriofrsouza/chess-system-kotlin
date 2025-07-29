package com.rogeriofrsouza.app;

import com.rogeriofrsouza.app.chess.ChessException;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.ChessPosition;
import com.rogeriofrsouza.app.ui.Display;
import com.rogeriofrsouza.app.ui.DisplayKt;
import com.rogeriofrsouza.app.ui.Prompt;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        DisplayKt displayKt = new DisplayKt();
        Prompt prompt = new Prompt();
        ChessMatch chessMatch = new ChessMatch();

        while (!chessMatch.isCheckMate()) {
            try {
                displayKt.printMatch(chessMatch);

                ChessPosition source = prompt.readChessPosition();
                boolean[][] possibleMoves = chessMatch.computePossibleMoves(source);

                displayKt.printBoard(chessMatch.getPieces(), possibleMoves);

                ChessPosition target = prompt.readChessPosition();

                chessMatch.performChessMove(source, target);

                if (chessMatch.getPromoted() != null) {
                    ChessPiece.Name pieceName = prompt.readPromotedPiece();
                    chessMatch.replacePromotedPiece(pieceName);
                }
            } catch (ChessException | InputMismatchException exception) {
                System.err.println(exception.getMessage());
                scanner.nextLine();
            }
        }

        displayKt.printMatch(chessMatch);
    }
}
