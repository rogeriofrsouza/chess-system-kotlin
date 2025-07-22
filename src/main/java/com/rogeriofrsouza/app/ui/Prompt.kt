package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessMatch
import com.rogeriofrsouza.app.chess.ChessPiece
import com.rogeriofrsouza.app.chess.ChessPosition
import java.util.*

class Prompt {

    fun readChessPosition(): ChessPosition {
        val input = readln()
        require(input.length == 2) { "Invalid input. Expected 2 characters" }

        val column = input[0]
        val row = Character.getNumericValue(input[1])

        return ChessPosition(column, row)
    }

    fun readPromotedPiece(): ChessPiece.Name {
        val input = readln()

        if ("[RNBQ]".toRegex(RegexOption.IGNORE_CASE).matches(input).not()) {
            throw InputMismatchException("Invalid piece letter")
        }

        return ChessMatch.possiblePromotedPieces
            .find { it.letter.equals(input, ignoreCase = true) }
            ?: throw NoSuchElementException("Unknown promoted piece: $input")
    }
}
