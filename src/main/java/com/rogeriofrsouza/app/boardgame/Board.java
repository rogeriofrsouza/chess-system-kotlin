package com.rogeriofrsouza.app.boardgame;

public class Board {

    private final int rows;
    private final int columns;

    private final BoardSquare[][] squares;

    public Board() {
        rows = 8;
        columns = 8;

        squares = new BoardSquare[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                squares[i][j] = new BoardSquare();
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public BoardSquare[][] getSquares() {
        return squares;
    }

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Position not on the board");
        }

        return squares[row][column].getPiece();
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        return squares[position.getRow()][position.getColumn()].getPiece();
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }

        squares[position.getRow()][position.getColumn()].setPiece(piece);
        piece.setPosition(position);
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        if (piece(position) == null) {
            return null;
        }

        Piece aux = piece(position);
        aux.setPosition(null); // Peça retirada do tabuleiro, não possui posição

        squares[position.getRow()][position.getColumn()].setPiece(null);

        return aux;
    }

    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        return piece(position) != null;
    }
}
