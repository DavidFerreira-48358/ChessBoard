import console.*
import domane.*
import storage.Board
import storage.DbOperations

/**
 * todo
 */
data class CommandHandler(
    val action: CommandInterface,
    val display: View
)
//REVER COMENTARIO
/**
 * Gets the container bearing the associations between user entered strings and the corresponding command handlers.
 * @param billboard the [Billboard] to be used by all commands
 * @param author    the [Author] instance to be used when posting messages
 * @return the container with the command handler mappings
 */
fun Handlers(board: Board): Map<String, CommandHandler> {
    return mapOf(
        "OPEN"  to CommandHandler(OpenCommand(board), ::printOpen),
        "JOIN"   to CommandHandler(JoinCommand(board), ::printJoin),
        "PLAY"   to CommandHandler(PlayCommand(board), ::printPlay),
        "REFRESH"   to CommandHandler(RefreshCommand(board), ::printRefresh),
        "MOVES"   to CommandHandler(MoveCommand(board), ::printMoves),
        "EXIT"  to CommandHandler(ExitCommand(), { }),
        "?"   to CommandHandler(HelpCommand(), ::printHelp)
    )
}