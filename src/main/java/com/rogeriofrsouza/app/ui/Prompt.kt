package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessMatch
import com.rogeriofrsouza.app.chess.ChessPiece
import com.rogeriofrsouza.app.chess.ChessPosition
import java.util.*

class Prompt {

    fun readChessPosition(): ChessPosition {
        while (true) {
            try {
                print("Enter position: ")
                val input = readln().trim().lowercase(Locale.US)
                require(input.length == 2) { "Invalid input. Expected 2 characters" }

                val column = input[0]
                val row = Character.getNumericValue(input[1])

                return ChessPosition(column, row)
            } catch (e: RuntimeException) {
                println(e.message)
            }
        }
    }

    fun readPromotedPiece(): ChessPiece.Name {
        while (true) {
            print("Enter piece for promotion (B/N/R/Q): ")
            val input = readln().trim().uppercase(Locale.US)

            if (Regex("[BNRQ]").matches(input)) {
                return ChessMatch.possiblePromotedPieces.first { it.letter == input }
            }

            println("Invalid piece letter")
        }
    }
}
