package com.rogeriofrsouza.app.chess;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.pieces.Bishop;
import com.rogeriofrsouza.app.chess.pieces.Knight;
import com.rogeriofrsouza.app.chess.pieces.Pawn;
import com.rogeriofrsouza.app.chess.pieces.Queen;
import com.rogeriofrsouza.app.chess.pieces.Rook;

@ExtendWith(MockitoExtension.class)
class ChessMatchTest {

    @InjectMocks
    private ChessMatch chessMatchMock;

    @Mock
    private Board boardMock;

    @Mock
    private Piece pieceMock;

    @Mock
    private ChessPiece chessPieceMock;

    @Mock
    private ChessPosition chessPositionMock;

    @Test()
    @DisplayName("should place pieces on the board")
    void chessMatchInitialSetup() {
        ChessMatch chessMatch = new ChessMatch();

        assertEquals(1, chessMatch.getTurn());
        assertEquals(ChessPiece.Color.WHITE, chessMatch.getCurrentPlayer());
        assertEquals(32, chessMatch.getPiecesOnTheBoard().size());
    }

    @Test
    @DisplayName("should validate the source position and return possible moves")
    void possibleMoves_validatePosition_returnPossibleMoves() {
        ChessPosition chessPosition = new ChessPosition('a', 4);
        Position position = new Position(4, 0);

        Rook chessPiece = new Rook(new Board(3, 3), ChessPiece.Color.WHITE);
        boolean[][] possibleMovesExpected = new boolean[][] {{true, true, false}};

        doReturn(true).when(boardMock).thereIsAPiece(position);

        doReturn(chessPiece).doReturn(pieceMock).doReturn(pieceMock)
                .when(boardMock).piece(position);

        doReturn(true).when(pieceMock).isThereAnyPossibleMove();
        doReturn(possibleMovesExpected).when(pieceMock).computePossibleMoves();

        assertEquals(
                possibleMovesExpected,
                chessMatchMock.computePossibleMoves(chessPosition));

        verify(pieceMock).computePossibleMoves();
    }

    @Test
    @DisplayName("should throw ChessException, there is no possible moves")
    void possibleMoves_noPossibleMoves_throwChessException() {
        ChessPosition chessPosition = new ChessPosition('a', 4);
        Position position = new Position(4, 0);
        Rook chessPiece = new Rook(new Board(3, 3), ChessPiece.Color.WHITE);

        doReturn(true).when(boardMock).thereIsAPiece(position);
        doReturn(chessPiece).doReturn(pieceMock).when(boardMock).piece(position);
        doReturn(false).when(pieceMock).isThereAnyPossibleMove();

        assertThrowsExactly(
                ChessException.class,
                () -> chessMatchMock.computePossibleMoves(chessPosition));
    }

    @Test
    @DisplayName("should throw ChessException, the chosen piece is not yours")
    void possibleMoves_chosenPieceNotYours_throwChessException() {
        ChessPosition chessPosition = new ChessPosition('a', 4);
        Position position = new Position(4, 0);
        Rook chessPiece = new Rook(new Board(3, 3), ChessPiece.Color.BLACK);

        doReturn(true).when(boardMock).thereIsAPiece(position);
        doReturn(chessPiece).when(boardMock).piece(position);

        assertThrowsExactly(
                ChessException.class,
                () -> chessMatchMock.computePossibleMoves(chessPosition));
    }

    @Test
    @DisplayName("should throw ChessException, there is no piece on source position")
    void possibleMoves_noPieceOnPosition_throwChessException() {
        ChessPosition chessPosition = new ChessPosition('a', 4);
        Position position = new Position(4, 0);

        doReturn(false).when(boardMock).thereIsAPiece(position);

        assertThrowsExactly(
                ChessException.class,
                () -> chessMatchMock.computePossibleMoves(chessPosition));
    }

    @ParameterizedTest
    @ValueSource(strings = {"B", "N", "R", "Q"})
    @DisplayName("should return the new piece if promoted piece is valid")
    void replacePromotedPiece_promotedPieceIsValid_returnNewPiece(String type) {
        chessMatchMock.setPromoted(chessPieceMock);

        ChessPosition chessPosition = new ChessPosition('a', 1);
        Position promotedPosition = chessPosition.toPosition();

        ChessPiece newPiece =
                switch (type) {
                    case "B" -> new Bishop(boardMock, ChessPiece.Color.WHITE);
                    case "N" -> new Knight(boardMock, ChessPiece.Color.WHITE);
                    case "R" -> new Rook(boardMock, ChessPiece.Color.WHITE);
                    default -> new Queen(boardMock, ChessPiece.Color.WHITE);
                };

        when(chessPieceMock.getChessPosition()).thenReturn(chessPosition);
        when(boardMock.removePiece(promotedPosition)).thenReturn(pieceMock);
        when(chessPieceMock.getColor()).thenReturn(ChessPiece.Color.WHITE);
        doNothing().when(boardMock).placePiece(newPiece, promotedPosition);

        assertEquals(newPiece, chessMatchMock.replacePromotedPiece(type));

        verify(chessPieceMock).getChessPosition();
        verify(boardMock).removePiece(promotedPosition);
        verify(chessPieceMock).getColor();
        verify(boardMock).placePiece(newPiece, promotedPosition);
    }

    @Test
    @DisplayName("should return the current promoted piece if type isn't valid")
    void replacePromotedPiece_typeNonValid_returnPromotedPiece() {
        Pawn promoted = new Pawn(boardMock, ChessPiece.Color.BLACK, chessMatchMock);
        chessMatchMock.setPromoted(promoted);

        assertEquals(promoted, chessMatchMock.replacePromotedPiece("A"));
    }

    @Test
    @DisplayName("should throw IllegalStateException, no piece to be promoted")
    void replacePromotedPiece_typeNonValid_throwIllegalStateException() {
        assertThrowsExactly(
                IllegalStateException.class,
                () -> chessMatchMock.replacePromotedPiece("A"));
    }

    @Test
    @DisplayName("should return the pieces on the board")
    void getPieces() {
        ChessPiece[][] piecesExpected = new ChessPiece[][] {
                {new Rook(boardMock, ChessPiece.Color.BLACK),
                        new Queen(boardMock, ChessPiece.Color.BLACK)},
                {new Rook(boardMock, ChessPiece.Color.WHITE),
                        new Queen(boardMock, ChessPiece.Color.WHITE)}};

        when(boardMock.getRows()).thenReturn(2);
        when(boardMock.getColumns()).thenReturn(2);

        when(boardMock.piece(anyInt(), anyInt()))
                .thenReturn(piecesExpected[0][0])
                .thenReturn(piecesExpected[0][1])
                .thenReturn(piecesExpected[1][0])
                .thenReturn(piecesExpected[1][1]);

        ChessPiece[][] piecesActual = chessMatchMock.getPieces();

        for (int i = 0; i < piecesExpected.length; i++) {
            assertArrayEquals(piecesExpected[i], piecesActual[i]);
        }

        verify(boardMock).getRows();
        verify(boardMock).getColumns();
        verify(boardMock, times(4)).piece(anyInt(), anyInt());
    }
}
