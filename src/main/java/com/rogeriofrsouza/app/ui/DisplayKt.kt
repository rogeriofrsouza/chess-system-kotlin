package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessMatch
import com.rogeriofrsouza.app.chess.ChessPiece

class DisplayKt {

    fun printMatch(chessMatch: ChessMatch) {
        printBoard(chessMatch.getPieces(), arrayOfNulls(8))

        val matchDisplay = renderMatch(chessMatch)
        println(matchDisplay)
    }

    private fun renderMatch(chessMatch: ChessMatch): String =
        buildString {
            val capturedWhitePieces = chessMatch.getCapturedPieces().filter { it.color == ChessPiece.Color.WHITE }
            val capturedBlackPieces = chessMatch.getCapturedPieces().filter { it.color == ChessPiece.Color.BLACK }

            appendLine()
                .appendLine("Captured pieces")
                .appendLine("White: ${AnsiEscapeCode.WHITE}${capturedWhitePieces}${AnsiEscapeCode.RESET}")
                .appendLine("Black: ${AnsiEscapeCode.YELLOW}${capturedBlackPieces}${AnsiEscapeCode.RESET}")
                .appendLine("Turn: ${chessMatch.turn}")

            if (chessMatch.isCheckMate) {
                appendLine("CHECKMATE!")
                    .append("Winner: ${chessMatch.currentPlayer}")
            } else {
                appendLine("Waiting player: ${chessMatch.currentPlayer}")

                if (chessMatch.isCheck) {
                    append("CHECK!")
                }
            }
        }

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
