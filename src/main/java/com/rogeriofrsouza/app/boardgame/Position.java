package com.rogeriofrsouza.app.boardgame;

public class Position {

    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Position)) return false;
        final Position other = (Position) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getRow() != other.getRow()) return false;
        if (this.getColumn() != other.getColumn()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Position;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getRow();
        result = result * PRIME + this.getColumn();
        return result;
    }

    public String toString() {
        return "Position(row=" + this.getRow() + ", column=" + this.getColumn() + ")";
    }
}
