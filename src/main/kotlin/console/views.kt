package console

import domane.Commands
import domane.Team
import storage.Board

/**
 * A command view is merely a function that renders the command execution result.
 */
typealias View = (input: Any?) -> Unit

/**
 * Function that prints the open command output
 * @param input, the commands returned from the open command
 */
fun printOpen(input: Any?) {
    input as Commands
    val success = (input != Commands.INVALID)
    println(
        if (success) "Opened a game"
        else "Couldn't open a game"
    )
}

/**
 * Function that prints the jin command output
 * @param input, the commands returned from the join command
 */
fun printJoin(input: Any?) {
    input as Commands
    val success = (input != Commands.INVALID)
    println(
        if (success) "Joined game"
        else "Couldn't join the game"
    )
}

/**
 * Function that prints the play command output
 * @param input, the board returned from the play command
 */
fun printPlay(input: Any?) {
    input as Board
    if(input.actionState != Commands.WIN){
        if (input.actionState != Commands.INVALID) drawBoard(input)
        else println("Invalid move")
    }
    else printWin(input)
}

/**
 * Function that prints the refresh command output
 * @param input, the board returned from the refresh command
 */
fun printRefresh(input: Any?) {
    input as Board
    if(input.actionState != Commands.WIN){
        if (input.actionState != Commands.INVALID) drawBoard(input)
        else println("No updates yet")
    }
    else println("${input.turn} wins the Game!")
}

/**
 * Function that prints the moves command output
 * @param input, the string returned from the moves command
 */
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

/**
 * Function that prints the help command output
 * @param input, the input returned from the help command
 */
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

/**
 * Function that draws the board
 * @param input, the board
 */
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

/**
 * Function that draws the welcome screen
 */
fun printWelcome() {
    printHelp("a")
}

/**
 * Function that win
 * @param input, the board.turn for the wining team
 */
fun printWin(input: Board){
    println("${input.turn} wins the Game!")
}
