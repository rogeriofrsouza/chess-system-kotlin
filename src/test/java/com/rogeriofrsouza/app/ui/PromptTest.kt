package com.rogeriofrsouza.app.ui

import com.rogeriofrsouza.app.chess.ChessMatch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayInputStream

class PromptTest {

    val prompt = Prompt()

    @Test
    fun `should return a ChessPosition when input is within range a1 to h8`() {
        val input = "e4\n"
        System.setIn(ByteArrayInputStream(input.toByteArray()))

        val result = prompt.readChessPosition()
        assertEquals('e', result.column)
        assertEquals(4, result.row)
    }

    @Test
    fun `should throw when chess position input is invalid`() {
        val input = "9\n"
        System.setIn(ByteArrayInputStream(input.toByteArray()))

        assertThrows<IllegalArgumentException> { prompt.readChessPosition() }
    }

    @Test
    fun `should return a promoted piece when input is valid`() {
        ChessMatch.possiblePromotedPieces
            .forEach { piece ->
                val input = "${piece.letter}\n"
                System.setIn(ByteArrayInputStream(input.toByteArray()))
                assertEquals(piece, prompt.readPromotedPiece())
            }
    }
}
