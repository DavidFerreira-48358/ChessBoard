import console.*
import domane.*
import storage.Board
import storage.BoardErrorException
import storage.DbMode
import storage.DbOperations

/**
 * Data class that links the commands to its representation
 * @param action, the command action
 * @param display, the command representation
 */
data class CommandHandler(
    val action: CommandInterface,
    val display: View,
    val errorDisplay: (BoardErrorException) -> Unit = { }
)

/**
 * Gets the container bearing the associations between user entered strings and the corresponding command handlers.
 * @param board, the board to be used the commands
 * @param db, the mode in which the client is running on
 * @return the container with the command handler mappings
 */
fun Handlers(board: Board,db:DbMode): Map<String, CommandHandler> {
    return mapOf(
        "OPEN"  to CommandHandler(
            action = OpenCommand(board),
            display = ::printOpen),
        "JOIN"   to CommandHandler(
            action = JoinCommand(board),
            display = ::printJoin),
        "PLAY"   to CommandHandler(
            action = PlayCommand(board,db),
            display = ::printPlay),
        "REFRESH"   to CommandHandler(
            action = RefreshCommand(board),
            display = ::printRefresh),
        "MOVES"   to CommandHandler(
            action = MoveCommand(board),
            display = ::printMoves),
        "EXIT"  to CommandHandler(
            action = ExitCommand(),
            display = { }),
        "?"   to CommandHandler(
            action = HelpCommand(),
            display = ::printHelp)
    )
}