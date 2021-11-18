package domane

import storage.Commands

//class CommandsException(cause: Exception): Exception(cause)

interface Commands{
    /**
     * Executes this command passing it the given parameter
     * @param parameter the commands parameter, or null, if no parameter has been passed
     */
    fun execute(parameter: String?): Commands

    /**
     * Overload of invoke operator, for convenience.
     */
    operator fun invoke(parameter: String? = null) = execute(parameter)

}
class OpenCommand(){
    // TODO: 18/11/2021
}
class JoinCommand(){
    // TODO: 18/11/2021
}
class PlayCommand(){
    // TODO: 18/11/2021
}
class MoveCommand(){
    // TODO: 18/11/2021
}
class RefreshCommand(){
    // TODO: 18/11/2021
}
class helpCommand(){
    // TODO: 18/11/2021
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