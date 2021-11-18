import console.*
import domane.Commands
import storage.Board
import storage.DbOperations

/**
 * todo
 */
data class CommandHandler(
    val action: Commands,
    val display: View
)
//REVER COMENTARIO
/**
 * Gets the container bearing the associations between user entered strings and the corresponding command handlers.
 * @param billboard the [Billboard] to be used by all commands
 * @param author    the [Author] instance to be used when posting messages
 * @return the container with the command handler mappings
 */
fun CommandHandlers(board: Board, db: DbOperations, s:String): Map<String, CommandHandler> {
    return mapOf(
        "OPEN"  to CommandHandler(OpenCommand(db,s), ::printOpen),
        "JOIN"   to CommandHandler(JoinCommand(db,s), ::printJoin),
        "PLAY"   to CommandHandler(PlayCommand(board,s), ::drawBoard),
        "REFRESH"   to CommandHandler(RefreshCommand(board,s), ::printRefresh),
        "MOVES"   to CommandHandler(GetCommand(board), ::printMoves),
        "EXIT"  to CommandHandler(ExitCommand(), { }),
        "?"   to CommandHandler({}, ::printHelp)
    )
}