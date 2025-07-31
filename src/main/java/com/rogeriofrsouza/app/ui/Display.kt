package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessMatch
import com.rogeriofrsouza.app.chess.ChessPiece
import com.rogeriofrsouza.app.chess.Color

class Display {

    fun printMatch(chessMatch: ChessMatch) {
        printBoard(chessMatch.getPieces(), arrayOfNulls(8))
        print(renderMatch(chessMatch))
    }

    fun printBoard(pieces: Array<Array<ChessPiece?>>, possibleMoves: Array<BooleanArray?>) {
        clearScreen()
        print(renderBoard(pieces, possibleMoves))
    }

    private fun clearScreen() {
        print(AnsiEscapeCode.MOVE_CURSOR_HOME + AnsiEscapeCode.CLEAR_SCREEN)
    }

    private fun renderBoard(pieces: Array<Array<ChessPiece?>>, possibleMoves: Array<BooleanArray?>): String =
        buildString {
            pieces.forEachIndexed { i, row ->
                append("${8 - i} ")

                row.forEachIndexed { j, piece ->
                    val isPossible = possibleMoves.getOrNull(i)?.getOrNull(j) == true
                    if (isPossible) append(AnsiEscapeCode.BLUE_BACKGROUND)

                    append(piece?.let { formatWithAnsiColor(it.color, it) } ?: "-")

                    if (isPossible) append(AnsiEscapeCode.RESET)
                    append(" ")
                }

                appendLine()
            }

            appendLine("  a b c d e f g h").appendLine()
        }

    private fun renderMatch(chessMatch: ChessMatch): String =
        buildString {
            val capturedWhitePieces = chessMatch.getCapturedPieces().filter { it.color == Color.WHITE }
            val capturedBlackPieces = chessMatch.getCapturedPieces().filter { it.color == Color.BLACK }

            appendLine("Captured pieces")
                .appendLine("White: ${formatWithAnsiColor(Color.WHITE, capturedWhitePieces)}")
                .appendLine("Black: ${formatWithAnsiColor(Color.BLACK, capturedBlackPieces)}")
                .appendLine()

            val currentPlayer = chessMatch.currentPlayer

            if (chessMatch.isCheckMate) {
                appendLine("CHECKMATE!")
                    .appendLine("Winner: ${formatWithAnsiColor(currentPlayer, currentPlayer)}")
            } else {
                appendLine("Turn: ${chessMatch.turn}")
                    .appendLine("Waiting player: ${formatWithAnsiColor(currentPlayer, currentPlayer)}")

                if (chessMatch.isCheck) {
                    appendLine("CHECK!")
                }
            }
        }

    private fun <T> formatWithAnsiColor(color: Color, obj: T): String =
        "${getColorCode(color)}$obj${AnsiEscapeCode.RESET}"

    private fun getColorCode(color: Color): String {
        return if (color == Color.WHITE)
            AnsiEscapeCode.WHITE
        else
            AnsiEscapeCode.YELLOW
    }
}
