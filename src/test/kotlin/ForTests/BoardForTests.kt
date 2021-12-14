package ForTests



import domane.Commands
import domane.Piece
import domane.SpecialMoves
import domane.Team

class BoardForTests{

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
     * Function that initializes the Board
     */
    init {
        placePieces()
    }

    var actionState = Commands.INVALID

    private var enPassantList = linkedMapOf<Int,validateForTests.Move>()
    private var passantListIndex = 0

    /**
     * Function that places the pieces on the board
     */
    fun placePieces(){
        for(i in 0..7){
            val curr = arrayOfArrays[i]
            for(j in blackStartInterval){
                if(j == 0) curr[j] = Piece(pieceChar[i], Team.BLACK)
                else curr[j] = Piece('p', Team.BLACK, SpecialMoves.FIRST)
            }
            for(j in whiteStartInterval){
                if(j == 7) curr[j] = Piece(pieceChar[i].toUpperCase(), Team.WHITE)
                else curr[j] = Piece('P', Team.WHITE, SpecialMoves.FIRST)
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

    /**
     * Function that returns the piece at x and y
     * @param x the x coordenate of the piece
     * @param y the y coordenate of the piece
     * @return the [Piece]
     */
    fun getPieceAt(x:Int, y:Int): Piece? = this.arrayOfArrays[x][y]

    /**
     * Function that moves the specified piece on the board
     * @param string is the command of the move
     * @return Board, modified if the command given is valid
     */
    fun makeMove(string:String): BoardForTests {
        val s = validateForTests(string, this).sanitiseString()
        return if(s != null){
            val toMove: Piece? = arrayOfArrays[s.from.x][s.from.y]
            if(toMove == null) {
                actionState = Commands.INVALID
                return this
            }

            val ret = checkConditionValidate(s,toMove)
            actionState = ret
            if(ret == Commands.INVALID) return this

            clearEnPassantList()
            if(toMove.fristmove == SpecialMoves.EN_PASSANT && (toMove.piece=='P' || toMove.piece=='p'))toMove.fristmove= SpecialMoves.NORMAL
            if(toMove.fristmove == SpecialMoves.FIRST && (toMove.piece=='P' || toMove.piece=='p')){
                toMove.fristmove= SpecialMoves.EN_PASSANT
                enPassantList[passantListIndex] = s
            }

            if(ret == Commands.EN_PASSANT){
                enPassant(s,toMove)
                return this
            }

            if(this.arrayOfArrays[s.to.x][s.to.y]?.piece == 'K' ||
                this.arrayOfArrays[s.to.x][s.to.y]?.piece == 'k'){
                    actionState = Commands.WIN
                return this
            }

            this.arrayOfArrays[s.from.x][s.from.y] = null
            this.arrayOfArrays[s.to.x][s.to.y] = toMove
            this
        } else this
        }
    private fun clearEnPassantList(){
        if(enPassantList.isNotEmpty()){
            enPassantList.forEach {
                this.arrayOfArrays[it.value.to.x][it.value.to.y]!!.fristmove = SpecialMoves.NORMAL
            }
            enPassantList = linkedMapOf()
        }
    }
    private fun enPassant(s:validateForTests.Move,toMove: Piece){
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
    private fun checkConditionValidate(s: validateForTests.Move, toMove: Piece): Commands {
        if(s.from == s.to) return Commands.INVALID
        if (getPieceAt(s.to.x,s.to.y)?.team == getPieceAt(s.from.x,s.from.y)!!.team) return Commands.INVALID
        val pieceMoves = pieceMovesT(s.piece, toMove.team, s.from, s.to, this)
        if(pieceMoves == Commands.INVALID) return Commands.INVALID
        if(pieceMoves == Commands.EN_PASSANT) return Commands.EN_PASSANT
        return Commands.VALID
    }
}