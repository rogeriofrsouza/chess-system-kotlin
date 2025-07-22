package com.rogeriofrsouza.app;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.pieces.Rook;
import com.rogeriofrsouza.app.ui.AnsiEscapeCode;
import com.rogeriofrsouza.app.ui.Display;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DisplayTest {

    @InjectMocks
    @Spy
    private Display display;

    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream outputStream;

    private final InputStream systemIn = System.in;
    private ByteArrayInputStream inputStream;

    @BeforeEach
    void setUpOutput() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreSystemInputOutput() {
        System.setOut(systemOut);
        System.setIn(systemIn);
    }

    private void provideInput(String data) {
        inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }

    @Test
    @DisplayName("should clear the console")
    void clearScreen() {
        display.clearScreen();
        String expected = AnsiEscapeCode.MOVE_CURSOR_HOME + AnsiEscapeCode.CLEAR_SCREEN;
        assertEquals(expected, outputStream.toString());
    }

    @Test
    @DisplayName("should print the match information and status")
    void printMatch_notCheckNotCheckmate_logMatch() {
        ChessMatch chessMatch = new ChessMatch();

        Board board = new Board(8, 8);
        List<ChessPiece> captured = List.of(
            new Rook(board, ChessPiece.Color.WHITE), new Rook(board, ChessPiece.Color.WHITE),
            new Rook(board, ChessPiece.Color.BLACK), new Rook(board, ChessPiece.Color.BLACK));

        String outputExpected = String.format(
            "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s",
            AnsiEscapeCode.WHITE,
            captured.subList(0, 2),
            AnsiEscapeCode.RESET,
            AnsiEscapeCode.YELLOW,
            captured.subList(2, 4),
            AnsiEscapeCode.RESET) +
            "\nTurn: " + chessMatch.getTurn() + "\n" +
            "Waiting player: " + chessMatch.getCurrentPlayer() + "\n";

        doNothing().when(display).printBoard(any(ChessPiece[][].class), any());
        display.printMatch(chessMatch, captured);

        assertEquals(outputExpected, outputStream.toString());
        verify(display).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the match information and CHECK")
    void printMatch_isCheck_logMatchAndCheck() {
        ChessMatch chessMatch = new ChessMatch();
        chessMatch.setCheck(true);
        chessMatch.setTurn(50);
        chessMatch.setCurrentPlayer(ChessPiece.Color.BLACK);

        Board board = new Board(8, 8);
        List<ChessPiece> captured = List.of(
            new Rook(board, ChessPiece.Color.WHITE), new Rook(board, ChessPiece.Color.WHITE));

        String outputExpected = String.format(
            "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s",
            AnsiEscapeCode.WHITE,
            captured,
            AnsiEscapeCode.RESET,
            AnsiEscapeCode.YELLOW,
            List.of(),
            AnsiEscapeCode.RESET) +
            "\nTurn: " + chessMatch.getTurn() + "\n" +
            "Waiting player: " + chessMatch.getCurrentPlayer() + "\n" +
            "CHECK!\n";

        doNothing().when(display).printBoard(any(ChessPiece[][].class), any());
        display.printMatch(chessMatch, captured);

        assertEquals(outputExpected, outputStream.toString());
        verify(display).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the match information, CHECKMATE and the winner")
    void printMatch_isCheckMate_logMatchAndCheckMate() {
        ChessMatch chessMatch = new ChessMatch();
        chessMatch.setCheckMate(true);
        chessMatch.setTurn(10);
        chessMatch.setCurrentPlayer(ChessPiece.Color.BLACK);

        Board board = new Board(8, 8);
        List<ChessPiece> captured = List.of(
            new Rook(board, ChessPiece.Color.BLACK), new Rook(board, ChessPiece.Color.BLACK));

        String stringBuilder = String.format(
            "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s",
            AnsiEscapeCode.WHITE,
            List.of(),
            AnsiEscapeCode.RESET,
            AnsiEscapeCode.YELLOW,
            captured,
            AnsiEscapeCode.RESET) +
            "\nTurn: " + chessMatch.getTurn() + "\n" +
            "CHECKMATE!\nWinner: " + chessMatch.getCurrentPlayer() + "\n";

        doNothing().when(display).printBoard(any(ChessPiece[][].class), any());
        display.printMatch(chessMatch, captured);

        assertEquals(stringBuilder, outputStream.toString());
        verify(display).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the board, pieces and its corresponding colors")
    void printBoard_noPossibleMove_printBoardPiecesColors() {
        Board board = new Board(4, 1);

        ChessPiece[][] pieces = new ChessPiece[][]{
            {new Rook(board, ChessPiece.Color.BLACK)},
            {null}, {null},
            {new Rook(board, ChessPiece.Color.WHITE)}
        };

        String stringExpected = String.format(
            "8 %sR%s %n7 -%s %n6 -%s %n5 %sR%s %n  a b c d e f g h%n",
            AnsiEscapeCode.YELLOW, AnsiEscapeCode.RESET, AnsiEscapeCode.RESET,
            AnsiEscapeCode.RESET, AnsiEscapeCode.WHITE, AnsiEscapeCode.RESET);

        display.printBoard(pieces, null);
        assertEquals(stringExpected, outputStream.toString());
    }

    @Test
    @DisplayName("should print the board, pieces, its corresponding colors and possibleMoves")
    void printBoard_hasPossibleMoves_printBoardPiecesColorsMoves() {
        Board board = new Board(4, 1);

        ChessPiece[][] pieces = new ChessPiece[][]{
            {new Rook(board, ChessPiece.Color.BLACK)},
            {null}, {null},
            {new Rook(board, ChessPiece.Color.WHITE)}
        };

        boolean[][] possibleMoves = new boolean[][]{{true}, {true}, {false}, {false}};

        String stringExpected = String.format(
            "8 %s%sR%s %n7 %s-%s %n6 -%s %n5 %sR%s %n  a b c d e f g h%n",
            AnsiEscapeCode.BLUE_BACKGROUND, AnsiEscapeCode.YELLOW, AnsiEscapeCode.RESET,
            AnsiEscapeCode.BLUE_BACKGROUND, AnsiEscapeCode.RESET, AnsiEscapeCode.RESET,
            AnsiEscapeCode.WHITE, AnsiEscapeCode.RESET);

        display.printBoard(pieces, possibleMoves);
        assertEquals(stringExpected, outputStream.toString());
    }
}
