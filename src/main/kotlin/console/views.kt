package console

import domane.Team
import storage.Board
import storage.Commands


data class PairMove(val team:String,val move: String){
    override fun toString(): String {
        return "$team -> $move"
    }
}

/**
 * A command view is merely a function that renders the command execution result.
 */
typealias View = (input: Any?) -> Unit

/*interface Views {

    /**
     * Function that opens a new game
     * @param input, the input that says if the action was successful
     */
    fun open(input: Any?)

    /**
     * Function joins a game
     * @param input, the input that says if the action was successful
     */
    fun join(input: Any?)

    /**
     * Function sees if the DataBase as been updated with the opponents move
     * @param input, the input that says if the action was successful
     */
    fun refresh(input: Any?)

    /**
     * Function plays a move in the board if it is valid
     * @param input, the input that says if the action was successful
     */
    fun play(input: Any?)

    /**
     * Function prints the moves done until now
     * @param list, the list of moves
     */
    fun printMoves(list: List<PairMove>)

    /**
     * Function that prints the help command
     */
    fun help()

    /**
     * Function that print the board at play
     */
    fun drawBoard(board: Board)

    /**
     * Function that print the introduction to the game when it starts
     */
    fun welcome()
}*/

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
    println(
        if (input.actionState != Commands.INVALID) drawBoard(input)
        else "Invalid move"
    )
}
//se for succes ent é para fazer draw
fun printRefresh(input: Any?) {
    val success = input as Boolean
    println(
        if (success) drawBoard(input)
        else "No updates yeet"
    )
}
//faz print da string
 fun printMoves(input: Any?) {
     if(input as String != null){
         println("Moves:")
         var i = 1
         var team = Team.WHITE
         input.split(" ").forEach {
             println("Play ${i++}: $team -> $it")
             team = team.next()
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

fun welcome() {
    TODO("Not yet implemented")
}

fun printWin(){
    TODO("Not yet implemented")
}
