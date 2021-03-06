package domane

import storage.*

interface CommandInterface{
    /**
     * Executes this command passing it the given parameter
     * @param parameter the commands parameter, or null, if no parameter has been passed
     */
    fun execute(parameter: String?): Any?

    /**
     * Overload of invoke operator, for convenience.
     */
    operator fun invoke(parameter: String? = null) = execute(parameter)

}
class OpenCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?) = CommandResult(board.open(parameter))
}

class JoinCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?) = CommandResult(board.join(parameter))
}

class PlayCommand(
    private val board: Board,
    private val dbMode: DbMode
):CommandInterface{
    override fun execute(parameter: String?): Any {
        if (!board.hasJoined())return CommandResult(board)
        if(board.actionState==Commands.WIN)return CommandResult(board)
        if(parameter == null){
            board.actionState = Commands.INVALID
            return CommandResult(board)
        }
      if(dbMode== DbMode.REMOTE && board.myTeam!= board.turn ){
          board.actionState = Commands.INVALID
          return CommandResult(board)
      }
        val sanitized = sanitiseString(parameter,board)
        return if(sanitized == null){
            board.actionState = Commands.INVALID
            return CommandResult(board)
        }
        else CommandResult(board.makeMove(sanitized,callFunc.PLAY))
    }
}

class MoveCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?) = CommandResult(board.moves())
}

class RefreshCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?): CommandResult<Board> {
        if (!board.hasJoined())return CommandResult(board)
        return if(board.actionState==Commands.WIN) CommandResult(board)
        else CommandResult(board.refresh())
    }
}

class ExitCommand:CommandInterface{
    override fun execute(parameter: String?) = ExitResult
}

class HelpCommand:CommandInterface{
    override fun execute(parameter: String?) = CommandResult(Commands.VALID)
}
