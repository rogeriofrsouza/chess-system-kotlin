package com.rogeriofrsouza.app;

import com.rogeriofrsouza.app.chess.ChessException;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.ChessPosition;
import com.rogeriofrsouza.app.ui.Display;
import com.rogeriofrsouza.app.ui.Prompt;

import java.util.*;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        Display display = new Display();
        Prompt prompt = new Prompt();

        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.isCheckMate()) {
            try {
                display.clearScreen();
                display.printMatch(chessMatch, captured);

                ChessPosition source = prompt.readChessPosition();
                boolean[][] possibleMoves = chessMatch.computePossibleMoves(source);

                display.clearScreen();
                display.printBoard(chessMatch.getPieces(), possibleMoves);

                ChessPosition target = prompt.readChessPosition();

                Optional.ofNullable(chessMatch.performChessMove(source, target))
                        .ifPresent(captured::add);

                if (chessMatch.getPromoted() != null) {
                    ChessPiece.Name pieceName = prompt.readPromotedPiece();
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
