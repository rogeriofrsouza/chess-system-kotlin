package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.pieces.Pawn;
import com.rogeriofrsouza.app.chess.pieces.Queen;
import com.rogeriofrsouza.app.chess.pieces.Rook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    @Test()
    @DisplayName("should place pieces on the board")
    void chessMatchInitialSetup() {
        ChessMatch chessMatch = new ChessMatch();

        assertEquals(1, chessMatch.getTurn());
        assertEquals(Color.WHITE, chessMatch.getCurrentPlayer());
    }

    @Test
    @DisplayName("should throw ChessException, there is no possible moves")
    void possibleMoves_noPossibleMoves_throwChessException() {
        ChessPosition chessPosition = new ChessPosition('a', 4);
        Position position = new Position(4, 0);
        Rook chessPiece = new Rook(new Board(), Color.WHITE);

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
        Rook chessPiece = new Rook(new Board(), Color.BLACK);

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

        when(chessPieceMock.getChessPosition()).thenReturn(chessPosition);
        when(boardMock.removePiece(promotedPosition)).thenReturn(new Rook(boardMock, Color.WHITE));
        when(chessPieceMock.getColor()).thenReturn(Color.WHITE);
        doNothing().when(boardMock).placePiece(any(Piece.class), eq(promotedPosition));

        assertDoesNotThrow(() -> chessMatchMock.replacePromotedPiece(type));
    }

    @Test
    @DisplayName("should return the current promoted piece if type isn't valid")
    void replacePromotedPiece_typeNonValid_returnPromotedPiece() {
        Pawn promoted = new Pawn(boardMock, Color.BLACK, chessMatchMock);
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
                {new Rook(boardMock, Color.BLACK),
                        new Queen(boardMock, Color.BLACK)},
                {new Rook(boardMock, Color.WHITE),
                        new Queen(boardMock, Color.WHITE)}};

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
