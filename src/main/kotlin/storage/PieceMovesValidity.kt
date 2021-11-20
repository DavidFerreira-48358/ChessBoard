package storage

import domane.Pos
import domane.Team

typealias Command = () -> Commands
/**
 * Function that selects movements functions based on the piece given
 * @param c the piece
 * @param team the team that made the move
 * @param from the current location of the piece
 * @param to the location the user wants to move the piece to
 * @param board the board at play
 * @return [Result] with dictate if the move is valid or not
 */
fun pieceMoves(c:Char, team: Team, from: Pos, to:Pos, board:Board): Map<Char, Commands> {

/*return when(c){
    'P'-> movePawnTo(team, from, to, board)

    'B'-> moveBishopTo(from, to, board)

    'Q' ->  moveQueenTo(from, to, board)

    'K' ->  moveKingTo(from,to)

    'R' -> moveTowerTo(from, to, board)

    'N' -> moveHorseTo(from, to)

    else-> Commands.INVALID
}*/
    return mapOf<Char,Commands>(
        'P' to (::movePawnTo)(team, from, to, board),//ver o q isto faz

        'B' to moveBishopTo(from, to, board),

        'Q' to moveQueenTo(from, to, board),

        'K' to moveKingTo(from,to),

        'R' to moveTowerTo(from, to, board),

        'N' to moveHorseTo(from, to)
    )
}

/**
 * Function that moves the Pawn piece
 * @param team the team that made the move
 * @param from the current location of the piece
 * @param to the location the user wants to move the piece to
 * @param board the board at play
 * @return [Result] with dictate if the move is valid or not
 */
private fun movePawnTo(team: Team, from: Pos, to: Pos, board: Board): Commands {
    val Michael_Jackson = when(team){
        Team.BLACK -> if(board.getPieceAt(from.x,from.y)?.fristmove == true) +2 else +1
        Team.WHITE -> if(board.getPieceAt(from.x,from.y)?.fristmove == true) -2 else -1
    }
    return when(true){
        to == Pos(from.x, from.y + Michael_Jackson) && board.getPieceAt(to.x , to.y) == null -> Commands.VALID

        to == Pos(from.x, from.y + Michael_Jackson/2) && board.getPieceAt(to.x , to.y) == null -> Commands.VALID

        to == Pos(from.x + 1, from.y + Michael_Jackson) &&
                null != board.getPieceAt(to.x , to.y)?.piece -> Commands.VALID

        to == Pos(from.x - 1, from.y + Michael_Jackson) &&
                null != board.getPieceAt(to.x , to.y)?.piece -> Commands.VALID

        else -> Commands.INVALID
    }
}

/**
 * Function that moves the Tower piece
 * @param from the current location of the piece
 * @param to the location the user wants to move the piece to
 * @param board the board at play
 * @return [Result] with dictate if the move is valid or not
 */
private fun moveTowerTo(from: Pos, to: Pos, board: Board): Commands {
    if(from.x!=to.x && from.y!=to.y)return Commands.INVALID
    if (from.y!=to.y){
        var incrementY=if (from.y<to.y) 1 else -1
        val temp= incrementY
        for (i in 0..7){
            incrementY= temp*i
            if(to.y==from.y+incrementY)  return Commands.VALID
            if(board.getPieceAt(from.x,from.y+incrementY)!=null&& incrementY!=0) return Commands.INVALID
        }
    }
    else{
        var incrementX=if (from.x<to.x) 1 else -1
        val temp= incrementX
        for (i in 0..7){
            incrementX= temp*i
            if(to.x==from.x +incrementX) return Commands.VALID
            if(board.getPieceAt(from.x +incrementX,from.y)!=null && incrementX!=0) return Commands.INVALID
        }
    }
    return Commands.INVALID

}

/**
 * Function that moves the Horse piece
 * @param from the current location of the piece
 * @param to the location the user wants to move the piece to
 * @return [Result] with dictate if the move is valid or not
 */
private fun moveHorseTo(from: Pos, to: Pos): Commands {
    if (from.x == to.x || from.y == to.y) return Commands.INVALID
    val horseLeap = 2
    val halfLeap = 1

    return when (true) {
        (to.x == from.x + horseLeap && to.y == from.y + halfLeap) -> Commands.VALID
        (to.x == from.x + horseLeap && to.y == from.y - halfLeap) -> Commands.VALID
        (to.x == from.x - horseLeap && to.y == from.y + halfLeap) -> Commands.VALID
        (to.x == from.x - horseLeap && to.y == from.y - halfLeap) -> Commands.VALID
        (to.x == from.x - halfLeap && to.y == from.y + horseLeap) -> Commands.VALID
        (to.x == from.x - halfLeap && to.y == from.y - horseLeap) -> Commands.VALID
        (to.x == from.x + halfLeap && to.y == from.y + horseLeap) -> Commands.VALID
        (to.x == from.x + halfLeap && to.y == from.y - horseLeap) -> Commands.VALID
        else -> Commands.INVALID
    }
}

/**
 * Function that moves the Bishop piece
 * @param from the current location of the piece
 * @param to the location the user wants to move the piece to
 * @param board the board at play
 * @return [Result] with dictate if the move is valid or not
 */
private fun moveBishopTo(from: Pos, to: Pos, board: Board): Commands {
    if(from.x==to.x || from.y==to.y)return Commands.INVALID
    var l = from.y
    var c = from.x
    val line = if(l > to.y) -1 else 1
    val column = if(c > to.x) -1 else 1

    for (i in 1..7){
        l+=line;c+=column
        if(c>7||l>7)return Commands.INVALID
        if(Pos(c,l) == to) return Commands.VALID
        if(board.getPieceAt(c,l)!=null) return Commands.INVALID

    }
    return Commands.INVALID
}

/**
 * Function that moves the Queen piece
 * @param from the current location of the piece
 * @param to the location the user wants to move the piece to
 * @param board the board at play
 * @return [Result] with dictate if the move is valid or not
 */
private fun moveQueenTo(from: Pos, to: Pos, board: Board): Commands {
    return if(from.x==to.x||to.y==from.y) moveTowerTo(from,to,board)
    else moveBishopTo(from,to,board)
}

/**
 * Function that moves the Tower puece
 * @param from the current location of the piece
 * @param to the location the user wants to move the piece to
 * @return [Result] with dictate if the move is valid or not
 */
private fun moveKingTo(from: Pos, to: Pos): Commands {
    return if (to.x in from.x-1 .. from.x+1 && to.y in from.y-1 .. from.y+1) Commands.VALID
    else Commands.INVALID
}