package com.rogeriofrsouza.app.boardgame

import com.rogeriofrsouza.app.chess.ChessPosition

data class Position(var row: Int, var column: Int) {

    fun setValues(row: Int, column: Int) {
        this.row = row
        this.column = column
    }

    fun toChessPosition() = ChessPosition('a' + column, 8 - row)
}
