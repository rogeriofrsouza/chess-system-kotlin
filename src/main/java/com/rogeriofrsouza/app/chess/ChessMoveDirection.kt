package com.rogeriofrsouza.app.chess

enum class ChessMoveDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    KNIGHT_UP_LEFT,
    KNIGHT_UP_RIGHT,
    KNIGHT_DOWN_LEFT,
    KNIGHT_DOWN_RIGHT,
    KNIGHT_LEFT_UP,
    KNIGHT_LEFT_DOWN,
    KNIGHT_RIGHT_UP,
    KNIGHT_RIGHT_DOWN,
    EN_PASSANT_LEFT,
    EN_PASSANT_RIGHT;

    fun isKnightMove(): Boolean = this in KNIGHT_UP_LEFT..KNIGHT_RIGHT_DOWN

    fun isEnPassantMove(): Boolean = this in EN_PASSANT_LEFT..EN_PASSANT_RIGHT
}
