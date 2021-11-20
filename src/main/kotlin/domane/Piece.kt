package domane


/**
 * Class that contain the x and y value of a Position
 */
data class Pos(val x:Int, val y:Int)

/**
 * Class that contains the decoded move
 */
data class Move(val piece:Char, val from:Pos, val to:Pos)

/**
 * enum class that defines the teams in chess
 */
enum class Team {
    BLACK,
    WHITE;
    fun next(): Team {
        return if(this == WHITE) BLACK
        else WHITE
    }
}


/**
 * data class that defines the type Piece
 * @param piece the char of the piece
 * @param team the team with the piece belongs
 * @param fristmove dictates if its the piece first move
 */
data class Piece(val piece:Char, val team:Team, var fristmove:Boolean = false)