package com.rogeriofrsouza.app.ui

object AnsiEscapeCode {

    const val RESET = "\u001B[0m"
    const val YELLOW = "\u001B[33m"
    const val WHITE = "\u001B[37m"

    const val BLUE_BACKGROUND = "\u001B[44m"

    const val MOVE_CURSOR_HOME = "\\033[H"
    const val CLEAR_SCREEN = "\\033[2J"
}
