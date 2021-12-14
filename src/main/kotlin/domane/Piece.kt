package domane


/**
 * Class that contain the x and y value of a Position
 */
data class Pos(val x:Int, val y:Int){
    override fun toString(): String {
        return "$x$y"
    }
}

/**
 * Class that contains the decoded move
 */
data class Move(val piece:Char, val from:Pos, val to:Pos){
    override fun toString(): String {
        return piece + from.toString() + to.toString()
    }
}

/**
 * enum class that defines the teams in chess
 */
enum class Team {
    BLACK,
    WHITE,
    NOT_DEFINED;
    fun next(): Team {
        return if(this == WHITE) BLACK
        else WHITE
    }
}

/**
 * Enum class that holds the values of the special moves
 */
enum class SpecialMoves {
    FIRST,
    EN_PASSANT,
    NORMAL
}

/**
 * data class that defines the type Piece
 * @param piece the char of the piece
 * @param team the team with the piece belongs
 * @param fristmove dictates if its the piece first move
 */
data class Piece(val piece:Char, val team:Team, var fristmove:SpecialMoves = SpecialMoves.NORMAL)