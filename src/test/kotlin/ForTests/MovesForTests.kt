package ForTests

import domane.Commands
import domane.SpecialMoves
import domane.Team

fun pieceMovesT(c:Char, team: Team, from: validateForTests.Pos, to: validateForTests.Pos, board: BoardForTests): Commands {
    /*return mapOf(
        'P' to movePawnTo(team, from, to,board),
        'N' to moveHorseTo(from, to),
        'B' to moveBishopTo(from, to, board),
        'Q' to moveQueenTo(from, to, board),
        'K' to moveKingTo(from, to, board),
        'R' to moveTowerTo(from, to, board),
    )*/
    return when(c){
        'P'-> movePawnToT(team,from,to,board)

        'B'-> moveBishopToT(from,to,board)

        'Q' ->  moveQueenToT(from,to,board)

        'K' ->  moveKingToT(from,to)

        'R' -> moveTowerToT(from,to,board)

        'N' -> moveHorseToT(from,to)

        else-> Commands.INVALID
    }
}

private fun movePawnToT(team: Team, from: validateForTests.Pos, to: validateForTests.Pos, board: BoardForTests): Commands {
    val vector = when(team){
        Team.BLACK -> if(board.getPieceAt(from.x,from.y)?.fristmove == SpecialMoves.FIRST) +2 else +1
        Team.WHITE -> if(board.getPieceAt(from.x,from.y)?.fristmove == SpecialMoves.FIRST) -2 else -1
        else -> 0
    }
    return when(true){
        to == validateForTests.Pos(from.x, from.y + vector) && board.getPieceAt(to.x , to.y) == null -> Commands.VALID

        to == validateForTests.Pos(from.x, from.y + vector/2) && board.getPieceAt(to.x , to.y) == null -> Commands.VALID

        enPassant(board, from) -> Commands.EN_PASSANT

        to == validateForTests.Pos(from.x + 1, from.y + vector) &&
                board.getPieceAt(from.x + 1 , from.y + vector)?.piece != null && board.getPieceAt(from.x + 1 , from.y + vector)?.piece !=
                board.getPieceAt(from.x , from.y )?.piece -> Commands.VALID

        to == validateForTests.Pos(from.x - 1, from.y + vector) &&
                board.getPieceAt(from.x - 1 , from.y + vector)?.piece != null && board.getPieceAt(from.x - 1 , from.y + vector)?.piece !=
                board.getPieceAt(from.x , from.y )?.piece -> Commands.VALID
        else -> Commands.INVALID
    }
}
private fun enPassant(board: BoardForTests, from:validateForTests.Pos):Boolean{
    val piece = if((from.x+1 in 0..7) && board.getPieceAt(from.x+1,from.y) != null) board.getPieceAt(from.x+1,from.y)
    else if((from.x-1 in 0..7) && board.getPieceAt(from.x-1,from.y) != null) board.getPieceAt(from.x-1,from.y)
    else null
    if(piece == null) return false
    return piece.fristmove == SpecialMoves.EN_PASSANT
}

private fun moveTowerToT(from:validateForTests.Pos, to:validateForTests.Pos, bored: BoardForTests):Commands{
    if(from.x!=to.x && from.y!=to.y)return Commands.INVALID
    if (from.y!=to.y){
        var incrementY=if (from.y<to.y) 1 else -1
        val temp= incrementY
        for (i in 0..7){
            incrementY= temp*i
            if(to.y==from.y+incrementY)  return Commands.VALID
            if(bored.getPieceAt(from.x,from.y+incrementY)!=null&& incrementY!=0) return Commands.INVALID
        }
    }
    else{
        var incrementX=if (from.x<to.x) 1 else -1
        val temp= incrementX
        for (i in 0..7){
            incrementX= temp*i
            if(to.x==from.x +incrementX) return Commands.VALID
            if(bored.getPieceAt(from.x +incrementX,from.y)!=null && incrementX!=0) return Commands.INVALID
        }
    }
    return Commands.INVALID
}

private fun moveHorseToT(from: validateForTests.Pos, to: validateForTests.Pos): Commands {
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

private fun moveBishopToT(from:validateForTests.Pos, to:validateForTests.Pos, bored: BoardForTests):Commands{
    if(from.x==to.x || from.y==to.y)return Commands.INVALID
    var l = from.y
    var c = from.x
    val line = if(l > to.y) -1 else 1
    val column = if(c > to.x) -1 else 1

    for (i in 1..7){
        l+=line;c+=column
        if(c>7||l>7)return Commands.INVALID
        if(validateForTests.Pos(c,l) == to) return Commands.VALID
        if(bored.getPieceAt(c,l)!=null) return Commands.INVALID

    }
    return Commands.INVALID
}

private fun moveQueenToT(from:validateForTests.Pos, to:validateForTests.Pos, bored: BoardForTests):Commands{
    if(from.x==to.x||to.y==from.y) return moveTowerToT(from,to,bored)
    else return moveBishopToT(from,to,bored)
}

private fun moveKingToT(from:validateForTests.Pos, to:validateForTests.Pos):Commands{
    return if (to.x in from.x-1 .. from.x+1 && to.y in from.y-1 .. from.y+1)Commands.VALID
    else Commands.INVALID
}