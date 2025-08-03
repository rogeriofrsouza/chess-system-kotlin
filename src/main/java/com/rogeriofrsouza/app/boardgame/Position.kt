package com.rogeriofrsouza.app.boardgame

import com.rogeriofrsouza.app.chess.ChessMoveDirection
import com.rogeriofrsouza.app.chess.ChessPosition

data class Position(var row: Int, var column: Int) {

    fun setValues(row: Int, column: Int) {
        this.row = row
        this.column = column
    }

    fun toChessPosition() = ChessPosition('a' + column, 8 - row)

    fun offset(direction: ChessMoveDirection) {
        when (direction) {
            ChessMoveDirection.UP -> row--
            ChessMoveDirection.DOWN -> row++
            ChessMoveDirection.LEFT -> column--
            ChessMoveDirection.RIGHT -> column++
            ChessMoveDirection.UP_LEFT -> { row--; column-- }
            ChessMoveDirection.UP_RIGHT -> { row--;column++ }
            ChessMoveDirection.DOWN_LEFT -> { row++;column-- }
            ChessMoveDirection.DOWN_RIGHT -> { row++;column++ }
        }
    }
}
