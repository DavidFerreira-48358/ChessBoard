package console

import domane.Commands
import domane.Team
import storage.Board


/*data class PairMove(val team:String,val move: String){
    override fun toString(): String {
        return "$team -> $move"
    }
}*/

/**
 * A command view is merely a function that renders the command execution result.
 */
typealias View = (input: Any?) -> Unit

fun printOpen(input: Any?) {
    input as Commands
    val success = (input != Commands.INVALID)
    println(
        if (success) "Opened a game"
        else "Couldn't open a game"
    )
}

fun printJoin(input: Any?) {
    input as Commands
    val success = (input != Commands.INVALID)
    println(
        if (success) "Joined game"
        else "Couldn't join the game"
    )
}

fun printPlay(input: Any?) {
    input as Board
    if(input.actionState != Commands.WIN){
        if (input.actionState != Commands.INVALID) drawBoard(input)
        else println("Invalid move")
    }
    else printWin(input)
}
//se for succes ent é para fazer draw
fun printRefresh(input: Any?) {
    input as Board
    if(input.actionState != Commands.WIN){
        if (input.actionState != Commands.INVALID) drawBoard(input)
        else println("No updates yet")
    }
    else println("${input.turn} wins the Game!")
}
//faz print da string
 fun printMoves(input: Any?) {
     if(input as String != null){
         println("Moves:")
         var i = 1
         var team = Team.WHITE
         input.split(" ").forEach {
             if(it.isNotEmpty()){
                 println("Play ${i++}: $team -> $it")
                 team = team.next()
             }
         }
     }
}

fun printHelp(input: Any?) {
    println("Commands list:\n" +
            "open <game number> -> this command allows you start a new game\n" +
            "join <game number> -> this command allows you to join an ongoing game\n" +
            "play <move> -> this command allows you to make a move in the board that you are playing\n" +
            "<move> should contain a piece, the current positions and the position of where to move EX: Pa2a4\n" +
            "refresh -> allows to refresh search in the DB for the opponent's move\n" +
            "moves -> this command allows you to see the list of moves done until now\n" +
            "exit -> allows you to exit the game\n" +
            "? -> allows you to get commands information"
    )
}

fun drawBoard(input: Any?) {
    if(input as Board != null) {
        println("    a b c d e f g h")
        println("   -----------------")
        for (i in 7 downTo 0) {
            var curr = 0
            println(
                "${i + 1} | ${input.getPieceAt(curr++, 7 - i)?.piece ?: ' '} " +
                        "${input.getPieceAt(curr++, 7 - i)?.piece ?: ' '} " +
                        "${input.getPieceAt(curr++, 7 - i)?.piece ?: ' '} " +
                        "${input.getPieceAt(curr++, 7 - i)?.piece ?: ' '} " +
                        "${input.getPieceAt(curr++, 7 - i)?.piece ?: ' '} " +
                        "${input.getPieceAt(curr++, 7 - i)?.piece ?: ' '} " +
                        "${input.getPieceAt(curr++, 7 - i)?.piece ?: ' '} " +
                        "${input.getPieceAt(curr, 7 - i)?.piece ?: ' '} " +
                        "|"
            )
        }
        println("   -----------------")
    }
}

fun printWelcome() {
    printHelp("a")
}

fun printWin(input: Board){
    println("${input.turn} wins the Game!")
}
