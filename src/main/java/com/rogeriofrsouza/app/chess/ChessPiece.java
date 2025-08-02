package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.boardgame.Position;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class ChessPiece extends Piece {

    private final Name name;
    private final Color color;
    private int moveCount;
    private List<ChessMoveDirection> chessMoveDirections;

    @Deprecated(since = "1.0-2025-08-02", forRemoval = true)
    protected ChessPiece(Board board, Color color) {
        super(board);
        this.name = null;
        this.color = color;
    }

    protected ChessPiece(
            Board board, Color color, Name name, List<ChessMoveDirection> chessMoveDirections) {
        super(board);
        this.color = color;
        this.name = name;
        this.chessMoveDirections = chessMoveDirections;
    }

    public ChessPosition getChessPosition() {
        return getPosition().toChessPosition();
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);

        return piece != null && piece.getColor() != color;
    }

    protected void increaseMoveCount() {
        moveCount++;
    }

    protected void decreaseMoveCount() {
        moveCount--;
    }

    @Override
    public String toString() {
        return getName().getLetter();
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Optional.ofNullable(getChessMoveDirections())
                .filter(directions -> !directions.isEmpty())
                .ifPresent(directions -> directions.forEach(
                        direction -> checkMoves(possibleMoves, direction)));

        return possibleMoves;
    }

    protected void checkMoves(boolean[][] possibleMoves, ChessMoveDirection direction) {
        Position targetPosition = new Position(getPosition().getRow(), getPosition().getColumn());

        while (true) {
            changeTargetPosition(targetPosition, direction);

            if (!getBoard().positionExists(targetPosition)) {
                return;
            }

            if (getBoard().thereIsAPiece(targetPosition)) {
                possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] =
                        isThereOpponentPiece(targetPosition);
                return;
            }

            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }
    }

    private void changeTargetPosition(Position targetPosition, ChessMoveDirection direction) {
        switch (direction) {
            case UP -> targetPosition.setRow(targetPosition.getRow() - 1);
            case DOWN -> targetPosition.setRow(targetPosition.getRow() + 1);
            case LEFT -> targetPosition.setColumn(targetPosition.getColumn() - 1);
            case RIGHT -> targetPosition.setColumn(targetPosition.getColumn() + 1);
            case UP_LEFT -> targetPosition.setValues(
                    targetPosition.getRow() - 1, targetPosition.getColumn() - 1);
            case UP_RIGHT -> targetPosition.setValues(
                    targetPosition.getRow() - 1, targetPosition.getColumn() + 1);
            case DOWN_LEFT -> targetPosition.setValues(
                    targetPosition.getRow() + 1, targetPosition.getColumn() - 1);
            case DOWN_RIGHT -> targetPosition.setValues(
                    targetPosition.getRow() + 1, targetPosition.getColumn() + 1);
            default -> throw new IllegalArgumentException("Invalid direction");
        }
    }

    public Name getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public int getMoveCount() {
        return this.moveCount;
    }

    public List<ChessMoveDirection> getChessMoveDirections() {
        return this.chessMoveDirections;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ChessPiece other)) return false;
        if (!other.canEqual(this)) return false;
        if (!super.equals(o)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (!Objects.equals(this$name, other$name)) return false;
        final Object this$color = this.getColor();
        final Object other$color = other.getColor();
        if (!Objects.equals(this$color, other$color)) return false;
        if (this.getMoveCount() != other.getMoveCount()) return false;
        final Object this$chessMoveDirections = this.getChessMoveDirections();
        final Object other$chessMoveDirections = other.getChessMoveDirections();
        return Objects.equals(this$chessMoveDirections, other$chessMoveDirections);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ChessPiece;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $color = this.getColor();
        result = result * PRIME + ($color == null ? 43 : $color.hashCode());
        result = result * PRIME + this.getMoveCount();
        final Object $chessMoveDirections = this.getChessMoveDirections();
        result = result * PRIME + ($chessMoveDirections == null ? 43 : $chessMoveDirections.hashCode());
        return result;
    }
}
