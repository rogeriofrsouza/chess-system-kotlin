package com.rogeriofrsouza.app.boardgame

class BoardSquare(val position: Position) {

    var piece: Piece? = null
    var isPossibleMove: Boolean = false
}
