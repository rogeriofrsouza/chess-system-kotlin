package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessMatch
import com.rogeriofrsouza.app.chess.ChessPiece

class Display {

    fun printMatch(chessMatch: ChessMatch) {
        printBoard(chessMatch.getPieces(), arrayOfNulls(8))

        val matchDisplay = renderMatch(chessMatch)
        print(matchDisplay)
    }

    fun printBoard(pieces: Array<Array<ChessPiece?>>, possibleMoves: Array<BooleanArray?>) {
        clearScreen()

        val boardDisplay = renderBoard(pieces, possibleMoves)
        print(boardDisplay)
    }

    private fun clearScreen() {
        print(AnsiEscapeCode.MOVE_CURSOR_HOME + AnsiEscapeCode.CLEAR_SCREEN)
    }

    private fun renderBoard(pieces: Array<Array<ChessPiece?>>, possibleMoves: Array<BooleanArray?>): String =
        buildString {
            pieces.forEachIndexed { i, row ->
                append("${8 - i} ")

                row.forEachIndexed { j, piece ->
                    if (possibleMoves.getOrNull(i)?.getOrNull(j) == true) {
                        append(AnsiEscapeCode.BLUE_BACKGROUND)
                    }

                    if (piece == null) append("-")
                    else append("${getColorCode(piece.color)}$piece")

                    append("${AnsiEscapeCode.RESET} ")
                }

                appendLine()
            }

            appendLine("  a b c d e f g h").appendLine()
        }

    private fun renderMatch(chessMatch: ChessMatch): String =
        buildString {
            val capturedWhitePieces = chessMatch.getCapturedPieces().filter { it.color == ChessPiece.Color.WHITE }
            val capturedBlackPieces = chessMatch.getCapturedPieces().filter { it.color == ChessPiece.Color.BLACK }

            appendLine("Captured pieces")
                .appendLine("White: ${AnsiEscapeCode.WHITE}${capturedWhitePieces}${AnsiEscapeCode.RESET}")
                .appendLine("Black: ${AnsiEscapeCode.YELLOW}${capturedBlackPieces}${AnsiEscapeCode.RESET}")
                .appendLine()

            if (chessMatch.isCheckMate) {
                appendLine("CHECKMATE!")
                    .appendLine("Winner: ${chessMatch.currentPlayer}")
            } else {
                appendLine("Turn: ${chessMatch.turn}")
                    .appendLine("Waiting player: ${chessMatch.currentPlayer}")

                if (chessMatch.isCheck) {
                    appendLine("CHECK!")
                }
            }
        }

    private fun getColorCode(color: ChessPiece.Color): String {
        return if (color == ChessPiece.Color.WHITE)
            AnsiEscapeCode.WHITE
        else
            AnsiEscapeCode.YELLOW
    }
}
