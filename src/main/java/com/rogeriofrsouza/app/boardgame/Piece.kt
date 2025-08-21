package com.rogeriofrsouza.app.boardgame

abstract class Piece(val board: Board) {

    var position: Position? = null

    abstract fun computePossibleMoves(): Array<BooleanArray>

    fun isTargetPossibleMove(target: Position): Boolean {
        return board.squares[target.row][target.column].isPossibleMove
    }

    fun isThereAnyPossibleMove(): Boolean {
        return board.squares.any { row -> row.any { it.isPossibleMove } }
    }
}
