package domane

import storage.Board
import kotlin.system.exitProcess

/**
 * Contract to be supported by all commands. Notice that commands are mere functions =)
 */
typealias Command = (String?) -> Unit
//ver de id n ser passado ou ser ?idk?
fun buildCommands(board: Board): Map<String, Command> {
    return mapOf(
        "EXIT" to { exit() },
        "PLAY" to { play(board, it) },
        "OPEN"  to { open(board, it)},
        "JOIN" to { join(board, it) },
        "REFRESH" to { refresh(board) },
        "MOVES" to { moves(board) }
    )
}

private fun exit(){
    exitProcess(0)
}

private fun play(board: Board, parameter: String?):Board?{
    val sanitized = if(parameter != null) sanitiseString(parameter,board) else null
    return if(sanitized != null) board.makeMove(sanitized)
    else null
}

private fun open(board:Board, gameId: String?){
    if(gameId != null) board.open(gameId)
    else println("To open a game you need to provide an Id")
}

private fun join(board:Board, gameId: String?){
    if(gameId != null) board.join(gameId)
    else println("To join a game you need to provide an Id")
}

private fun moves(board: Board) = board.moves()

private fun refresh(board: Board) = board.refresh(board)
