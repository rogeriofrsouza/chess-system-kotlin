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
import com.rogeriofrsouza.app.ui.UI;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        UI ui = new UI();
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.isCheckMate()) {
            try {
                ui.clearScreen();
                ui.printMatch(chessMatch, captured);

                System.out.print("\nSource: ");
                ChessPosition source = ui.readChessPosition(scanner);
                boolean[][] possibleMoves = chessMatch.computePossibleMoves(source);

                ui.clearScreen();
                ui.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.print("\nTarget: ");
                ChessPosition target = ui.readChessPosition(scanner);

                Optional.ofNullable(chessMatch.performChessMove(source, target))
                        .ifPresent(captured::add);

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
                    ChessPiece.Name pieceName = ui.readPromotedPiece(scanner);

                    while (pieceName == null) {
                        System.err.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
                        pieceName = ui.readPromotedPiece(scanner);
                    }

                    chessMatch.replacePromotedPiece(pieceName);
                }
            } catch (ChessException | InputMismatchException exception) {
                System.err.println(exception.getMessage());
                scanner.nextLine();
            }
        }

        ui.clearScreen();
        ui.printMatch(chessMatch, captured);
    }
}
