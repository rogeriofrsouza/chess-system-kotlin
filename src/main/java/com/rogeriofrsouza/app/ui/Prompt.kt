package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessMatch
import com.rogeriofrsouza.app.chess.ChessPiece
import com.rogeriofrsouza.app.chess.ChessPosition
import java.util.*

class Prompt {

    fun readChessPosition(): ChessPosition = readUntilValid {
        print("Enter position: ")
        val input = readln().trim().lowercase(Locale.US)

        require(input.length == 2) { "Expected two characters" }

        val column = input[0]
        val row = Character.getNumericValue(input[1])

        return ChessPosition(column, row)
    }

    fun readPromotedPiece(): ChessPiece.Name = readUntilValid {
        print(
            "Enter piece for promotion (" +
                    "${ChessMatch.possiblePromotedPieces.joinToString("/") { it.letter }}): "
        )

        val input = readln().trim().uppercase(Locale.US)

        require(input.length == 1) { "Expected one character" }

        return ChessMatch.possiblePromotedPieces
            .firstOrNull { it.letter == input }
            ?: error("Invalid piece")
    }

    private inline fun <T> readUntilValid(block: () -> T): T {
        while (true) {
            try {
                block()
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}
