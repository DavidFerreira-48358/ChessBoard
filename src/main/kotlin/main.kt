import com.mongodb.client.MongoClient
import console.printWelcome
import console.readCommand
import domane.CommandResult
import domane.ExitResult
import mongoDB.createMongoClient
import storage.*

fun selectPath(dbInfo:DBConnectionInfo):Pair<DbMode,MongoClient>{
    return if (dbInfo.mode == DbMode.REMOTE) Pair(DbMode.REMOTE,createMongoClient(dbInfo.connectionString))
    else Pair(DbMode.LOCAL,createMongoClient())
}
fun main() {
    val dbInfo = getDBConnectionInfo()
    val driver = selectPath(dbInfo)

    val dbOperations = DbOperations(driver.second.getDatabase(dbInfo.dbName))
    try {
        val board = MongoDbBoard(dbOperations,driver.first)
        val dispatcher = Handlers(board,driver.first)
        printWelcome()
        while(true){
            try {
                val (command, parameter) = readCommand()
                val handler = dispatcher[command]
                if (handler == null) println("Invalid command")
                else {
                    when (val result = handler.action(parameter)) {
                        is ExitResult -> break
                        is CommandResult<*> -> handler.display(result.data)
                    }
                }
            }catch (e:BoardErrorException){
                println("An unknown error occurred.\n" + "${e.cause}")
            }
        }
    }
    catch(e:BoardAccessException){
        println("Error with DataBase services." +
                if (dbInfo.mode == DbMode.REMOTE) "Check your network connection."
                else "Is your local database started?")
    }
    finally {
        println("\uD83D\uDE2D \uD83D\uDE0E \uD83E\uDD2B \uD83D\uDE41 \uD83E\uDD0F \uD83D\uDC3B ")
        println("Closing driver ...")
        driver.second.close()
    }
}
