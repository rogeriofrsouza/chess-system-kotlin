package com.rogeriofrsouza.app.boardgame

import com.rogeriofrsouza.app.chess.ChessMoveDirection
import com.rogeriofrsouza.app.chess.ChessPosition

data class Position(var row: Int, var column: Int) {

    fun setValues(row: Int, column: Int) {
        this.row = row
        this.column = column
    }

    fun toChessPosition() = ChessPosition('a' + column, 8 - row)

    @Deprecated("Use offsetDirection(direction) for a pure alternative", ReplaceWith("offset(direction)"))
    fun offset(direction: ChessMoveDirection) {
        when (direction) {
            ChessMoveDirection.UP -> row--
            ChessMoveDirection.DOWN -> row++
            ChessMoveDirection.LEFT -> column--
            ChessMoveDirection.RIGHT -> column++
            ChessMoveDirection.UP_LEFT -> { row--; column-- }
            ChessMoveDirection.UP_RIGHT -> { row--; column++ }
            ChessMoveDirection.DOWN_LEFT -> { row++; column-- }
            ChessMoveDirection.DOWN_RIGHT -> { row++; column++ }
            ChessMoveDirection.KNIGHT_UP_LEFT -> { row -= 2; column--}
            ChessMoveDirection.KNIGHT_UP_RIGHT -> { row -= 2; column++}
            ChessMoveDirection.KNIGHT_DOWN_LEFT ->  { row += 2; column--}
            ChessMoveDirection.KNIGHT_DOWN_RIGHT -> { row += 2; column++}
            ChessMoveDirection.KNIGHT_LEFT_UP -> { row--; column -= 2}
            ChessMoveDirection.KNIGHT_LEFT_DOWN -> { row++; column -= 2}
            ChessMoveDirection.KNIGHT_RIGHT_UP -> { row--; column += 2}
            ChessMoveDirection.KNIGHT_RIGHT_DOWN -> { row++; column += 2}
        }
    }

    fun offsetDirection(direction: ChessMoveDirection): Position {
        val (deltaRow, deltaCol) = when (direction) {
            ChessMoveDirection.UP -> -1 to 0
            ChessMoveDirection.DOWN -> 1 to 0
            ChessMoveDirection.LEFT -> 0 to -1
            ChessMoveDirection.RIGHT -> 0 to 1
            ChessMoveDirection.UP_LEFT -> -1 to -1
            ChessMoveDirection.UP_RIGHT -> -1 to 1
            ChessMoveDirection.DOWN_LEFT -> 1 to -1
            ChessMoveDirection.DOWN_RIGHT -> 1 to 1
            ChessMoveDirection.KNIGHT_UP_LEFT -> -2 to -1
            ChessMoveDirection.KNIGHT_UP_RIGHT -> -2 to 1
            ChessMoveDirection.KNIGHT_DOWN_LEFT -> 2 to -1
            ChessMoveDirection.KNIGHT_DOWN_RIGHT -> 2 to 1
            ChessMoveDirection.KNIGHT_LEFT_UP -> -1 to -2
            ChessMoveDirection.KNIGHT_LEFT_DOWN -> 1 to -2
            ChessMoveDirection.KNIGHT_RIGHT_UP -> -1 to 2
            ChessMoveDirection.KNIGHT_RIGHT_DOWN -> 1 to 2
        }

        return Position(row + deltaRow, column + deltaCol)
    }
}
