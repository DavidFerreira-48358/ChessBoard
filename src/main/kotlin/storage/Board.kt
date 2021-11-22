package storage

import domane.Commands
import domane.Move
import domane.Piece
import domane.Team

class BoardAccessException(cause: Exception): Exception(cause)
class BoardErrorException(cause: Exception): Exception(cause)

interface Board {

    /**
     * Function that moves pieces on the board
     * @param string the move
     * @return the [Board] moved
     */
    fun makeMove(move: Move,func: callFunc):Board

    /**
     * Function that return the piece at specified coordinates
     * @param x coordinate
     * @param y coordinate
     * @return [Piece] at x an y
     */
    fun getPieceAt(x:Int,y:Int): Piece?

    /**
     * Function that opens a game in the db
     * @param id, the id of the string
     */
    fun open(id:String?): Commands

    /**
     * Function that joins a game in the db
     * @param id, the id of the string
     */
    fun join(id:String?):Commands

    /**
     * Function searches for the opponents in the  db
     */
    fun refresh():Board

    /**
     * Function returns the list of moves made until now
     */
    fun moves():String

    /**
     * Function that returns true if the users as joined the game
     */
    fun hasJoined():Boolean

    /**
     * Hold the current game state
     */
    var actionState:Commands

    /**
     * holds the current player turn
     */
    var myTeam: Team;

    /**
     * Team associated to opening or joining game
     */
    var turn:Team ;
}