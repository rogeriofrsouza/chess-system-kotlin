package com.rogeriofrsouza.app.chess

import com.rogeriofrsouza.app.boardgame.Position

class ChessPosition(val column: Char, val row: Int) {

    init {
        require(column in 'a'..'h' && row in 1..8) {
            "Invalid chess position. Should be a1 - h8"
        }
    }

    fun toPosition() = Position(8 - row, column.code - 'a'.code)

    override fun toString() = "$column$row"
}
