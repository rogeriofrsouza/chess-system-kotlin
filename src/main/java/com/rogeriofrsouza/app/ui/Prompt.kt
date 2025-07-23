package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessMatch
import com.rogeriofrsouza.app.chess.ChessPiece
import com.rogeriofrsouza.app.chess.ChessPosition
import java.util.*

class Prompt {

    private val promotionRegex = Regex("[BNRQ]")

    fun readChessPosition(): ChessPosition {
        val input = readln()
        require(input.length == 2) { "Invalid input. Expected 2 characters" }

        val column = input[0]
        val row = Character.getNumericValue(input[1])

        return ChessPosition(column, row)
    }

    fun readPromotedPiece(): ChessPiece.Name {
        while (true) {
            print("Enter piece for promotion (B/N/R/Q): ")
            val input = readln().trim().uppercase(Locale.US)

            if (promotionRegex.matches(input)) {
                return ChessMatch.possiblePromotedPieces.first { it.letter == input }
            }

            println("Invalid piece letter")
        }
    }
}
