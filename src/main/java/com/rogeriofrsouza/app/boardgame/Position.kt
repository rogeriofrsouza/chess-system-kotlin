package com.rogeriofrsouza.app.boardgame

data class Position(var row: Int, var column: Int) {

    fun setValues(row: Int, column: Int) {
        this.row = row
        this.column = column
    }
}
