import console.*
import domane.*
import storage.Board
import storage.DbMode
import storage.DbOperations

/**
 * Data class that links the commands to its representation
 * @param action, the command action
 * @param display, the command representation
 */
data class CommandHandler(
    val action: CommandInterface,
    val display: View
)

/**
 * Gets the container bearing the associations between user entered strings and the corresponding command handlers.
 * @param board, the board to be used the commands
 * @param db, the mode in which the client is running on
 * @return the container with the command handler mappings
 */
fun Handlers(board: Board,db:DbMode): Map<String, CommandHandler> {
    return mapOf(
        "OPEN"  to CommandHandler(OpenCommand(board), ::printOpen),
        "JOIN"   to CommandHandler(JoinCommand(board), ::printJoin),
        "PLAY"   to CommandHandler(PlayCommand(board,db), ::printPlay),
        "REFRESH"   to CommandHandler(RefreshCommand(board), ::printRefresh),
        "MOVES"   to CommandHandler(MoveCommand(board), ::printMoves),
        "EXIT"  to CommandHandler(ExitCommand(), { }),
        "?"   to CommandHandler(HelpCommand(), ::printHelp)
    )
}