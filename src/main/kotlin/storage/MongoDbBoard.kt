package storage

import console.PairMove
import domane.Move
import domane.Piece
import domane.Team
import java.util.*

/**
 * TODO(muudar para ficheiro res)
 */
enum class Commands {
    VALID,
    INVALID,
    WIN,
    PROMOTE,
    EXIT
}

class MongoDbBoard(private val db: DbOperations): Board{

    /**
     * Function that initializes the Board
     */
    init {
        placePieces()
    }

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
    var turn:Team = Team.WHITE
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
    lateinit var myTeam:Team;

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
    var actionState = Commands.INVALID

    var list:List<PairMove> = LinkedList<PairMove>()
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

    override fun makeMove(move: Move): Board {
        val toMove:Piece? = arrayOfArrays[move.from.x][move.from.y]
        if(toMove == null) {
            actionState = Commands.INVALID
            return this
        }
        val ret = checkConditionValidate(move,toMove)
        actionState = ret
        if(ret == Commands.INVALID) return this

        if(toMove.fristmove && (toMove.piece=='P' || toMove.piece=='p'))toMove.fristmove=false

        if(this.arrayOfArrays[move.to.x][move.to.y]?.piece == 'K' ||
            this.arrayOfArrays[move.to.x][move.to.y]?.piece == 'k'){
            actionState = Commands.WIN
            return this
        }
        this.arrayOfArrays[move.from.x][move.from.y] = null
        this.arrayOfArrays[move.to.x][move.to.y] = toMove
        if(toMove.piece.toUpperCase() == 'P' && (move.to.y == 0 || move.to.y == 7)) actionState = Commands.PROMOTE//mudar
        return this
    }

    /**
     * Function that validates conditions for the command to be valid
     * @param move the move
     * @param toMove the piece to move
     * @return if the [Result] is valid or not
     */
    private fun checkConditionValidate(move: Move, toMove:Piece): Commands {
        if(move.from == move.to) return Commands.INVALID
        if(turn != getPieceAt(move.from.x,move.from.y)!!.team) return Commands.INVALID
        if(getPieceAt(move.to.x,move.to.y)?.team == getPieceAt(move.from.x,move.from.y)!!.team) return Commands.INVALID
        if(pieceMoves(move.piece,toMove.team,move.from,move.to,this) == Commands.INVALID) return Commands.INVALID
        return Commands.VALID
    }

    override fun getPieceAt(x: Int, y: Int): Piece? = this.arrayOfArrays[x][y]

     override fun open(id:String):Commands{
        return if(db.read("open",id)!=null) return Commands.INVALID
        else {
            db.post("open", GameState(id,""))
            currentGameid = id
            myTeam=Team.WHITE
            currentgame_state = "open"
            Commands.VALID
        }
    }
    override fun join(id:String):Commands{
        return if(db.read("open",id)!=null) {
            currentGameid = id
            myTeam=Team.BLACK
            currentgame_state = "open"
            Commands.VALID
        }
        else Commands.INVALID
    }
    override fun refresh(board:Board):Commands{
        if (currentGameid.isEmpty())return Commands.INVALID
        var a= db.read(currentgame_state,currentGameid)!!.movement
        val b= a.split(" ")
        a=b[b.size-2]
        return if(list[list.size-1].move!=a){
            board.makeMove(a)
            if(actionState!=Commands.INVALID) {
                currentGame_String += a
                turn = if (turn == Team.WHITE) Team.BLACK else Team.WHITE
            }
            Commands.VALID
        }
        else {
            Commands.INVALID
        }

    }

    override fun moves() = currentGame_String
}