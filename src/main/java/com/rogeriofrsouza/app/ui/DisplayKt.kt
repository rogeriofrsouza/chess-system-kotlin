package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessPiece

class DisplayKt {

    fun printBoard(pieces: Array<Array<ChessPiece?>>, possibleMoves: Array<BooleanArray?>) {
        clearScreen()

        val boardDisplay = renderBoard(pieces, possibleMoves)
        println(boardDisplay)
    }

    private fun clearScreen() {
        print(AnsiEscapeCode.MOVE_CURSOR_HOME + AnsiEscapeCode.CLEAR_SCREEN)
    }

    private fun renderBoard(pieces: Array<Array<ChessPiece?>>, possibleMoves: Array<BooleanArray?>): String =
        buildString {
            pieces.forEachIndexed { i, row ->
                append((8 - i)).append(" ")

                row.forEachIndexed { j, piece ->
                    if (possibleMoves.getOrNull(i)?.getOrNull(j) == true) {
                        append(AnsiEscapeCode.BLUE_BACKGROUND)
                    }

                    when (piece) {
                        null -> append("-")
                        else -> {
                            val colorCode = getPieceColorCode(piece)
                            append(colorCode).append(piece)
                        }
                    }

                    append(AnsiEscapeCode.RESET).append(" ")
                }

                appendLine()
            }

            append("  a b c d e f g h")
        }

    private fun getPieceColorCode(piece: ChessPiece): String {
        return if (piece.color == ChessPiece.Color.WHITE)
            AnsiEscapeCode.WHITE
        else
            AnsiEscapeCode.YELLOW
    }
}
