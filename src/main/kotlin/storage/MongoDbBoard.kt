package storage


import com.mongodb.MongoException
import domane.*
import java.util.*

/**
 * enum class that holds the values of the called functions for future conditions
 */
enum class callFunc{
    REFRESH,
    PLAY
}

fun <T> tryCommand(block: () -> T) =
    try { block() }
    catch (ex: MongoException) { throw BoardErrorException(ex) }


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

    override var actionState = Commands.INVALID
    private var enPassantList = linkedMapOf<Int,Move>()
    private var passantListIndex = 0

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
                else curr[j] = Piece('p', Team.BLACK, SpecialMoves.FIRST)
            }
            for(j in whiteStartInterval){
                if(j == 7) curr[j] = Piece(pieceChar[i].toUpperCase(),Team.WHITE)
                else curr[j] = Piece('P',Team.WHITE, SpecialMoves.FIRST)
            }
        }
    }

    /**
     * Function that adds move to the global list of moves
     * @param move, the move to be added
     */
    private fun addToGameString(move:Move,func: callFunc){
        if(actionState != Commands.WIN) turn= turn.next()
        if(firstmove==true){
            currentGame_String += "${move.piece}${'a'.plus(move.from.x)}${8-move.from.y}${'a'.plus(move.to.x)}${8-move.to.y} "
            firstmove=false
            currentgame_state="currentgames"
            if(func==callFunc.PLAY){
                db.post(currentgame_state,GameState(currentGameid,currentGame_String))
            }

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

        clearEnPassantList()
        if((toMove.fristmove == SpecialMoves.FIRST) && (toMove.piece=='P' || toMove.piece=='p'))toMove.fristmove = SpecialMoves.EN_PASSANT
        if(toMove.fristmove == SpecialMoves.FIRST && (toMove.piece=='P' || toMove.piece=='p')){
            toMove.fristmove= SpecialMoves.EN_PASSANT
            enPassantList[passantListIndex] = move
        }

        if(ret == Commands.EN_PASSANT){
            enPassant(move,toMove)
            return this
        }

        if(func==callFunc.REFRESH && turn==myTeam) return this
        if(this.arrayOfArrays[move.to.x][move.to.y]?.piece == 'K' ||
            this.arrayOfArrays[move.to.x][move.to.y]?.piece == 'k'){
            try {
                actionState = Commands.WIN
                addToGameString(move,func)
                return this
            }catch (e:BoardAccessException){
                throw BoardAccessException(e)
            }
        }

        this.arrayOfArrays[move.from.x][move.from.y] = null
        this.arrayOfArrays[move.to.x][move.to.y] = toMove
        if(toMove.piece.toUpperCase() == 'P' && (move.to.y == 0 || move.to.y == 7)) actionState = Commands.PROMOTE//mudar
        try {
            addToGameString(move,func)//put
        }catch (e:BoardAccessException){
            throw BoardAccessException(e)
        }
        return this
    }
    private fun clearEnPassantList(){
        if(enPassantList.isNotEmpty()){
            enPassantList.forEach {
                this.arrayOfArrays[it.value.to.x][it.value.to.y]!!.fristmove = SpecialMoves.NORMAL
            }
            enPassantList = linkedMapOf()
        }
    }
    private fun enPassant(s:Move,toMove: Piece){
        if(getPieceAt(s.from.x+1,s.from.y) != null){
            this.arrayOfArrays[s.from.x+1][s.from.y] = null
            this.arrayOfArrays[s.from.x][s.from.y] = null
            this.arrayOfArrays[s.to.x][s.to.y] = toMove
        }
        else{
            this.arrayOfArrays[s.from.x-1][s.from.y] = null
            this.arrayOfArrays[s.from.x][s.from.y] = null
            this.arrayOfArrays[s.to.x][s.to.y] = toMove
        }
    }

    /**
     * Function that validates conditions for the command to be valid
     * @param move the move
     * @param toMove the piece to move
     * @return if the [Result] is valid or not
     */
    private fun checkConditionValidate(move: Move, toMove:Piece,func: callFunc): Commands {
        if (func!=callFunc.REFRESH) {
            if (move.from == move.to)
                return Commands.INVALID
            if (turn != getPieceAt(move.from.x, move.from.y)!!.team)
                return Commands.INVALID
            if (getPieceAt(move.to.x, move.to.y)?.team == getPieceAt(move.from.x, move.from.y)!!.team)
                return Commands.INVALID
            if (pieceMoves(move.piece, toMove.team, move.from, move.to, this)== Commands.INVALID)
                return Commands.INVALID

        }
        return Commands.VALID
    }

    override fun getPieceAt(x: Int, y: Int): Piece? =
        tryCommand {  this.arrayOfArrays[x][y] }

    override fun open(id:String?):Commands{
         return tryCommand{
             return@tryCommand if(id == null || db.read("open",id)!=null) Commands.INVALID
             else {
                 db.post("open", GameState(id,""))
                 currentGameid = id
                 myTeam=Team.WHITE
                 currentgame_state = "open"
                 Commands.VALID
             }
         }
    }

    override fun join(id:String?):Commands{
        return tryCommand{
            return@tryCommand if(id != null && db.read("open",id)!=null) {
                currentGameid = id
                myTeam=if(dbInfo==DbMode.LOCAL)Team.WHITE else Team.BLACK
                currentgame_state = "currentgames"
                Commands.VALID
            }
            else Commands.INVALID
        }
    }

    override fun refresh():Board{
        return tryCommand {
            if (dbInfo==DbMode.LOCAL) {
                this.actionState = Commands.INVALID
                return@tryCommand this
            }
            if (currentGameid.isEmpty()){
                this.actionState = Commands.INVALID
                return@tryCommand this
            }
            val a = db.read(currentgame_state,currentGameid)!!.movement
            val string = a.split(" ")
            val b = sanitiseString(string[string.size-2],this)
            if(b == null){
                this.actionState = Commands.INVALID
                return@tryCommand this
            }
            this.makeMove(b,callFunc.REFRESH)
            return@tryCommand this
        }
    }

    override fun hasJoined():Boolean{
        return currentGameid.isNotEmpty()
    }

    override fun moves() = currentGame_String
}