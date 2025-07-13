package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.BoardException;

public class ChessException extends BoardException {

    private static final long serialVersionUID = 1L;

    public ChessException(String msg) {
        super(msg);
    }
}
