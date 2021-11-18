package storage

import domane.Move
import domane.Piece

class BoardAccessException(cause: Exception): Exception(cause)

interface Board {

    /**
     * Function that moves pieces on the board
     * @param string the move
     * @return the [Board] moved
     */
    fun makeMove(move: Move):Board

    /**
     * Function that return the piece at specified coordinates
     * @param x coordinate
     * @param y coordinate
     * @return [Piece] at x an y
     */
    fun getPieceAt(x:Int,y:Int): Piece?

    fun open(id:String):Commands

    fun join(id:String):Commands

    fun refresh(board:Board):Commands

    fun moves():String
}