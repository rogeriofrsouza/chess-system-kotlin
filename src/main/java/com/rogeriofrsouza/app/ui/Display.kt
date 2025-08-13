package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.boardgame.Board
import com.rogeriofrsouza.app.chess.ChessMatch
import com.rogeriofrsouza.app.chess.ChessPiece
import com.rogeriofrsouza.app.chess.Color

class Display {

    fun printMatch(chessMatch: ChessMatch) {
        printBoard(chessMatch.board)
        print(renderMatch(chessMatch))
    }

    fun printBoard(board: Board) {
        clearScreen()
        print(renderBoard(board))
    }

    private fun clearScreen() {
        print(AnsiEscapeCode.MOVE_CURSOR_HOME + AnsiEscapeCode.CLEAR_SCREEN)
    }

    private fun renderBoard(board: Board): String =
        buildString {
            board.squares.forEachIndexed { i, row ->
                append("${8 - i} ")

                row.forEachIndexed { _, square ->
                    if (square.isPossibleMove) {
                        append(AnsiEscapeCode.BLUE_BACKGROUND)
                    }

                    val renderedPiece = (square.piece as? ChessPiece)?.let {
                        formatWithAnsiColor(it.color, it)
                    } ?: "-"

                    append(renderedPiece)

                    if (square.isPossibleMove && renderedPiece == "-") {
                        append(AnsiEscapeCode.RESET)
                    }

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

    private fun <T> formatWithAnsiColor(color: Color, obj: T): String {
        val ansiColor = if (color == Color.WHITE)
            AnsiEscapeCode.WHITE
        else
            AnsiEscapeCode.YELLOW

        return "$ansiColor$obj${AnsiEscapeCode.RESET}"
    }
}
