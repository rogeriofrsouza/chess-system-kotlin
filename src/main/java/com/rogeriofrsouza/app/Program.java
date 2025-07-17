package com.rogeriofrsouza.app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import com.rogeriofrsouza.app.chess.ChessException;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.ChessPosition;
import com.rogeriofrsouza.app.ui.Display;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        Display display = new Display();
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.isCheckMate()) {
            try {
                display.clearScreen();
                display.printMatch(chessMatch, captured);

                System.out.print("\nSource: ");
                ChessPosition source = display.readChessPosition(scanner);
                boolean[][] possibleMoves = chessMatch.computePossibleMoves(source);

                display.clearScreen();
                display.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.print("\nTarget: ");
                ChessPosition target = display.readChessPosition(scanner);

                Optional.ofNullable(chessMatch.performChessMove(source, target))
                        .ifPresent(captured::add);

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
                    ChessPiece.Name pieceName = display.readPromotedPiece(scanner);

                    while (pieceName == null) {
                        System.err.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
                        pieceName = display.readPromotedPiece(scanner);
                    }

                    chessMatch.replacePromotedPiece(pieceName);
                }
            } catch (ChessException | InputMismatchException exception) {
                System.err.println(exception.getMessage());
                scanner.nextLine();
            }
        }

        display.clearScreen();
        display.printMatch(chessMatch, captured);
    }
}
