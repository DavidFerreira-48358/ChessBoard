package domane

import storage.Board
import storage.Commands

interface CommandInterface{
    /**
     * Executes this command passing it the given parameter
     * @param parameter the commands parameter, or null, if no parameter has been passed
     */
    fun execute(parameter: String?): Any

    /**
     * Overload of invoke operator, for convenience.
     */
    operator fun invoke(parameter: String? = null) = execute(parameter)

}
class OpenCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?): Commands {
        requireNotNull(parameter)
        return board.open(parameter)
    }
}
class JoinCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?): Commands {
        requireNotNull(parameter)
        return board.join(parameter)
    }
}
class PlayCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?): Commands {
        requireNotNull(parameter)
        val sanitized = sanitiseString(parameter,board)?: return Commands.INVALID
        board.makeMove(sanitized)
        return board.actionState
    }
}
class MoveCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?): String {
        return board.moves()
    }
}

class RefreshCommand(
    private val board: Board
):CommandInterface{
    override fun execute(parameter: String?): Board = board.refresh()
}

class ExitCommand:CommandInterface{
    override fun execute(parameter: String?): Commands = Commands.EXIT
}

class HelpCommand:CommandInterface{
    override fun execute(parameter: String?): Commands = Commands.VALID
}


/*/**
     * Function that opens a new game
     */
    fun open(id:String)

    /**
     * Function joins a game
     */
    fun join(id:String)

    /**
     * Function plays a move in the board if it is valid
     */
    fun play()

    /**
     * Function sees if the DataBase as been updated with the opponents move
     */
    fun refresh()*/