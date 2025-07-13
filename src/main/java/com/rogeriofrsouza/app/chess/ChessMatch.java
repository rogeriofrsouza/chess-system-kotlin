package com.rogeriofrsouza.app.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.pieces.Bishop;
import com.rogeriofrsouza.app.chess.pieces.King;
import com.rogeriofrsouza.app.chess.pieces.Knight;
import com.rogeriofrsouza.app.chess.pieces.Pawn;
import com.rogeriofrsouza.app.chess.pieces.Queen;
import com.rogeriofrsouza.app.chess.pieces.Rook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChessMatch {

    private int turn;
    private ChessPiece.Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    private Board board;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public static final List<ChessPiece.Name> possiblePromotedPieces =
            List.of(
                    ChessPiece.Name.BISHOP, ChessPiece.Name.KNIGHT,
                    ChessPiece.Name.ROOK, ChessPiece.Name.QUEEN);

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = ChessPiece.Color.WHITE;

        initialSetup();
    }

    private void initialSetup() {
        ChessPiece.Color color = ChessPiece.Color.BLACK;

        for (int row : new int[] {8, 7, 2, 1}) {
            if (row <= 2) {
                color = ChessPiece.Color.WHITE;
            }

            for (char column = 'a'; column < 'i'; column++) {
                ChessPiece piece =
                        (row == 7 || row == 2)
                                ? new Pawn(board, color, this)
                                : switchPiece(column, board, color);

                board.placePiece(piece, new ChessPosition(column, row).toPosition());
                piecesOnTheBoard.add(piece);
            }
        }
    }

    private ChessPiece switchPiece(char column, Board board, ChessPiece.Color color) {
        return switch (column) {
            case 'a', 'h' -> new Rook(board, color);
            case 'b', 'g' -> new Knight(board, color);
            case 'c', 'f' -> new Bishop(board, color);
            case 'd' -> new Queen(board, color);
            case 'e' -> new King(board, color, this);
            default -> throw new IllegalStateException("Column not supported.");
        };
    }

    public ChessPiece[][] getPieces() {
        int rows = board.getRows();
        int columns = board.getColumns();

        ChessPiece[][] pieces = new ChessPiece[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                pieces[i][j] = (ChessPiece) board.piece(i, j);
            }
        }

        return pieces;
    }

    public boolean[][] computePossibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);

        return board.piece(position).computePossibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateSourcePosition(source);
        validateTargetPosition(source, target);

        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        check = testCheck(getOpponentPlayer(currentPlayer));

        if (testCheckMate(getOpponentPlayer(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        promoted = null;
        enPassantVulnerable = null;

        if (movedPiece instanceof Pawn) {
            // Special move: Promotion
            if (movedPiece.getColor() == ChessPiece.Color.WHITE
                    && target.getRow() == 0
                    || movedPiece.getColor() == ChessPiece.Color.BLACK
                            && target.getRow() == 7) {
                promoted = movedPiece;
                promoted = replacePromotedPiece("Q");
            }

            // Special move: En Passant
            if (List.of(source.getRow() - 2, source.getRow() + 2).contains(target.getRow())) {
                enPassantVulnerable = movedPiece;
            }
        }

        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }

        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }

        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).isTargetPossibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece movingPiece = (ChessPiece) board.removePiece(source);
        movingPiece.increaseMoveCount();

        Piece capturedPiece = board.removePiece(target);
        board.placePiece(movingPiece, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // Special move: Castling - kingside rook
        if (movingPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);

            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // Special move: Castling - queenside rook
        if (movingPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);

            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // Special move: En Passant
        if (movingPiece instanceof Pawn
                && source.getColumn() != target.getColumn()
                && capturedPiece == null) {
            int targetRow =
                    movingPiece.getColor() == ChessPiece.Color.WHITE
                            ? target.getRow() + 1
                            : target.getRow() - 1;

            Position pawnPosition = new Position(targetRow, target.getColumn());

            capturedPiece = board.removePiece(pawnPosition);
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece movingPiece = (ChessPiece) board.removePiece(target);
        movingPiece.decreaseMoveCount();

        board.placePiece(movingPiece, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            piecesOnTheBoard.add(capturedPiece);
            capturedPieces.remove(capturedPiece);
        }

        // Special move: Castling - kingside rook
        if (movingPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);

            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // Special move: Castling - queenside rook
        if (movingPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);

            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // Special move: En Passant
        if (movingPiece instanceof Pawn
                && source.getColumn() != target.getColumn()
                && capturedPiece == enPassantVulnerable) {
            ChessPiece pawn = (ChessPiece) board.removePiece(target);

            int targetRow = movingPiece.getColor() == ChessPiece.Color.WHITE ? 3 : 4;
            Position pawnPosition = new Position(targetRow, target.getColumn());

            board.placePiece(pawn, pawnPosition);
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = getOpponentPlayer(currentPlayer);
    }

    private boolean testCheck(ChessPiece.Color color) {
        Position kingPosition = searchKing(color).getChessPosition().toPosition();
        ChessPiece.Color opponentPlayer = getOpponentPlayer(color);

        return piecesOnTheBoard.stream()
                .filter(piece -> ((ChessPiece) piece).getColor() == opponentPlayer)
                .anyMatch(piece -> {
                    boolean[][] possibleMoves = piece.computePossibleMoves();
                    return possibleMoves[kingPosition.getRow()][kingPosition.getColumn()];
                });
    }

    private ChessPiece searchKing(ChessPiece.Color color) {
        return piecesOnTheBoard.stream()
                .filter(piece -> piece instanceof King
                        && ((ChessPiece) piece).getColor() == color)
                .findFirst()
                .map(ChessPiece.class::cast)
                .orElseThrow(() -> new IllegalStateException(
                        "There is no " + color + " king on the board"));
    }

    private ChessPiece.Color getOpponentPlayer(ChessPiece.Color color) {
        return color == ChessPiece.Color.WHITE
                ? ChessPiece.Color.BLACK
                : ChessPiece.Color.WHITE;
    }

    private boolean testCheckMate(ChessPiece.Color color) {
        if (!testCheck(color)) {
            return false;
        }

        List<Piece> piecesFiltered =
                piecesOnTheBoard.stream()
                        .filter(piece -> ((ChessPiece) piece).getColor() == color)
                        .collect(Collectors.toList());

        for (Piece piece : piecesFiltered) {
            boolean[][] possibleMoves = piece.computePossibleMoves();

            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (possibleMoves[i][j]) {
                        Position source = ((ChessPiece) piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);

                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public ChessPiece replacePromotedPiece(ChessPiece.Name pieceName) {
        return replacePromotedPiece(pieceName.getLetter());
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("There is no piece to be promoted");
        }

        if (!List.of("B", "N", "R", "Q").contains(type)) {
            return promoted;
        }

        Position promotedPosition = promoted.getChessPosition().toPosition();
        Piece promotedPiece = board.removePiece(promotedPosition);
        piecesOnTheBoard.remove(promotedPiece);

        ChessPiece newPiece = switch (type) {
            case "B" -> new Bishop(board, promoted.getColor());
            case "N" -> new Knight(board, promoted.getColor());
            case "R" -> new Rook(board, promoted.getColor());
            default -> new Queen(board, promoted.getColor());
        };

        board.placePiece(newPiece, promotedPosition);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }
}
