package storage

import console.PairMove

import domane.*
import java.util.*

/**
 * TODO(muudar para ficheiro res)
 */

/**
 * Sum type used to represent the execution result of the existing commands
 */
sealed class Result

/**
 * Result produced when the command execution determines that the application should terminate.
 * See https://kotlinlang.org/docs/object-declarations.html#object-declarations-overview
 */
object ExitResult : Result()
enum class callFunc{
    REFRESH,
    PLAY
}
/**
 * Result produced when the command execution yields a value
 */
class CommandResult<T>(val data: T) : Result()

class MongoDbBoard(private val db: DbOperations,private val dbInfo:DbMode): Board{

    /**
     * Val that creates the matrix used in the Board
     */
    private val arrayOfArrays:Array<Array<Piece?>> = arrayOf(arrayOfNulls<Piece?>(8),
        arrayOfNulls<Piece?>(8),
        arrayOfNulls<Piece?>(8),
        arrayOfNulls<Piece?>(8),
        arrayOfNulls<Piece?>(8),
        arrayOfNulls<Piece?>(8),
        arrayOfNulls<Piece?>(8),
        arrayOfNulls<Piece?>(8),
    )

    /**
     * Val that contains the pieces to place
     */
    private val pieceChar: Array<Char> = arrayOf('r','n','b','q','k','b','n','r')

    /**
     * Val that contains the black team's start positions
     */
    private val blackStartInterval = 0..1

    /**
     * Val that contains the white team's start positions
     */
    private val whiteStartInterval = 6..7

    /**
     * holds the current player turn
     */
    override var turn:Team = Team.WHITE
    /**
     * holds the current game id
     */
    var currentGameid=""

    /**
     * list of moves
     */
    var currentGame_String=""

    /**
     * Team associated to opening or joining game
     */
    override lateinit var myTeam:Team;

    /**
     * indicates the first move of the game
     */
    var firstmove=true

    /**
     * variable that holds the game_state
     * Possible values [open], [currentgames], [closedgames]
     */
    var currentgame_state=""

    /**
     * Hold the current game state
     */
    override var actionState = Commands.INVALID


    /**
     * Function that initializes the Board
     */
    init {
        placePieces()
    }

    /**
     * Function that places the pieces on the board
     */
    private fun placePieces(){
        for(i in 0..7){
            val curr = arrayOfArrays[i]
            for(j in blackStartInterval){
                if(j == 0) curr[j] = Piece(pieceChar[i],Team.BLACK)
                else curr[j] = Piece('p', Team.BLACK, true)
            }
            for(j in whiteStartInterval){
                if(j == 7) curr[j] = Piece(pieceChar[i].toUpperCase(),Team.WHITE)
                else curr[j] = Piece('P',Team.WHITE, true)
            }
        }
    }

    /**
     * Function that adds move to the global list of moves
     * @param move, the move to be added
     */
    private fun addToGameString(move:Move){
        turn= turn.next()
        if(firstmove==true){
            currentGame_String += "${move.piece}${'a'.plus(move.from.x)}${8-move.from.y}${'a'.plus(move.to.x)}${8-move.to.y} "
            firstmove=false
            currentgame_state="currentgames"
            db.post(currentgame_state,GameState(currentGameid,currentGame_String))
        }
        else {currentGame_String += "${move.piece}${'a'.plus(move.from.x)}${8-move.from.y}${'a'.plus(move.to.x)}${8-move.to.y} "
            db.put(currentgame_state,GameState(currentGameid,currentGame_String))}
    }

    /**
     * Override of the function toString
     * @return The current state of the Board in a String format
     */
    override fun toString(): String {
        var string = ""
        for(i in 0 .. 7){
            for(j in 0 .. 7){
                string+=arrayOfArrays[j][i]?.piece ?: ' '
            }
        }
        return string
    }

    override fun makeMove(move: Move,func: callFunc): Board {
        val toMove:Piece? = arrayOfArrays[move.from.x][move.from.y]
        if(toMove == null) {
            actionState = Commands.INVALID
            return this
        }
        val ret = checkConditionValidate(move,toMove,func)
        actionState = ret
        if(ret == Commands.INVALID) return this
        if(toMove.fristmove && (toMove.piece=='P' || toMove.piece=='p'))toMove.fristmove=false
        if(func==callFunc.PLAY && turn!=myTeam) return this
        if(func==callFunc.REFRESH && turn==myTeam) return this
        if(this.arrayOfArrays[move.to.x][move.to.y]?.piece == 'K' ||
            this.arrayOfArrays[move.to.x][move.to.y]?.piece == 'k'){
            addToGameString(move)
            actionState = Commands.WIN
            return this
        }

        this.arrayOfArrays[move.from.x][move.from.y] = null
        this.arrayOfArrays[move.to.x][move.to.y] = toMove
        if(toMove.piece.toUpperCase() == 'P' && (move.to.y == 0 || move.to.y == 7)) actionState = Commands.PROMOTE//mudar
        try {
            addToGameString(move)//put
        }catch (e:BoardAccessException){
            throw BoardAccessException(e)
        }
        return this
    }

    /**
     * Function that validates conditions for the command to be valid
     * @param move the move
     * @param toMove the piece to move
     * @return if the [Result] is valid or not
     */
    private fun checkConditionValidate(move: Move, toMove:Piece,func: callFunc): Commands {
        if (func==callFunc.REFRESH) {
            if (move.from == move.to)
                return Commands.INVALID
            if (turn== getPieceAt(move.from.x, move.from.y)!!.team)
                return Commands.INVALID
            if (getPieceAt(move.to.x, move.to.y)?.team == getPieceAt(move.from.x, move.from.y)!!.team)
                return Commands.INVALID
            if (pieceMoves(move.piece, toMove.team, move.from, move.to, this)[move.piece] == Commands.INVALID)
                return Commands.INVALID
        }
        else{

            if (move.from == move.to)
                return Commands.INVALID
            if (turn != getPieceAt(move.from.x, move.from.y)!!.team)
                return Commands.INVALID
            if (getPieceAt(move.to.x, move.to.y)?.team == getPieceAt(move.from.x, move.from.y)!!.team)
                return Commands.INVALID
            if (pieceMoves(move.piece, toMove.team, move.from, move.to, this)[move.piece] == Commands.INVALID)
                return Commands.INVALID

        }
        return Commands.VALID
    }

    override fun getPieceAt(x: Int, y: Int): Piece? = this.arrayOfArrays[x][y]

     override fun open(id:String?):Commands{
         try {
             return if(id == null || db.read("open",id)!=null) return Commands.INVALID
             else {
                 db.post("open", GameState(id,""))
                 currentGameid = id
                 myTeam=Team.WHITE
                 currentgame_state = "open"
                 Commands.VALID
             }
         }catch (e:BoardAccessException){
             throw  BoardAccessException(e)
         }
    }
    override fun join(id:String?):Commands{
        try{
            return if(id != null && db.read("open",id)!=null) {
                currentGameid = id
                myTeam=if(dbInfo==DbMode.LOCAL)Team.WHITE else Team.BLACK
                currentgame_state = "currentgames"
                Commands.VALID
            }
            else Commands.INVALID
        }catch (e:BoardAccessException){
            throw BoardAccessException(e)
        }
    }
    override fun refresh():Board{
        if (dbInfo==DbMode.LOCAL) {
            this.actionState = Commands.INVALID
            return this
        }
        if (currentGameid.isEmpty()){
            this.actionState = Commands.INVALID
            return this
        }
        val a = db.read(currentgame_state,currentGameid)!!.movement
        val string = a.split(" ")
        val b = sanitiseString(string[string.size-2],this)
        if(b == null){
            this.actionState = Commands.INVALID
            return this
        }
        return if(string[string.size-2] != a ){
            this.makeMove(b,callFunc.REFRESH)
            if(actionState!=Commands.INVALID) {//ja se sabe a partida que é um move valido
                currentGame_String += "$a "
            }
            this
        }
        else {
            this
        }

    }

    override fun moves() = currentGame_String
}