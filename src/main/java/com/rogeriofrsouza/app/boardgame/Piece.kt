package com.rogeriofrsouza.app.boardgame

abstract class Piece(val board: Board) {

    var position: Position? = null

    abstract fun computePossibleMoves(): Array<BooleanArray>

    fun isTargetPossibleMove(target: Position): Boolean {
        return computePossibleMoves()[target.row][target.column]
    }

    fun isThereAnyPossibleMove(): Boolean =
        computePossibleMoves().any { row -> row.any { it } }
}
