package com.rogeriofrsouza.app.boardgame;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Position {

    private int row;
    private int column;

    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
