package com.rogeriofrsouza.app;

import com.rogeriofrsouza.app.chess.ChessException;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPosition;
import com.rogeriofrsouza.app.chess.Name;
import com.rogeriofrsouza.app.ui.Display;
import com.rogeriofrsouza.app.ui.Prompt;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        Display display = new Display();
        Prompt prompt = new Prompt();
        ChessMatch chessMatch = new ChessMatch();

        do {
            try {
                chessMatch.getBoard().cleanPossibleMoves();
                display.printMatch(chessMatch);

                ChessPosition source = prompt.readChessPosition();
                chessMatch.computePossibleMoves(source);

                display.printBoard(chessMatch.getBoard());

                ChessPosition target = prompt.readChessPosition();

                chessMatch.performChessMove(source, target);

                if (chessMatch.getPromoted() != null) {
                    Name pieceName = prompt.readPromotedPiece();
                    chessMatch.replacePromotedPiece(pieceName);
                }
            } catch (ChessException | InputMismatchException exception) {
                System.err.println(exception.getMessage());
                scanner.nextLine();
            }
        } while (!chessMatch.isCheckMate());

        display.printMatch(chessMatch);
    }
}
