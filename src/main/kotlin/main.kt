import com.mongodb.client.MongoClient
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
    //cria a instacia das oepraçoes q vamos usar
    try {
        val board = MongoDbBoard(dbOperations,driver.first)//a board
        val dispatcher = Handlers(board,driver.first)//constroi os commandos com a board
        while(true){
            val (command, parameter) = readCommand() //le o comando
            val handler = dispatcher[command]   //vai ver o commando
            if (handler == null) println("Invalid command")
            else {
                when (val result = handler.action(parameter)) { //vai fazer a action
                    is ExitResult -> break //sai
                    is CommandResult<*> -> handler.display(result.data) //faz display do resultado
                }
            }
        }
    }
    catch(e:BoardAccessException){//erros da database
        println("Error with DataBase services." +
                if (dbInfo.mode == DbMode.REMOTE) "Check your network connection."
                else "Is your local database started?")
    }
    catch (e:BoardErrorException){//errors do software
        println("An unknown error occurred.\n" + "${e.cause}")
    }
    finally {
        println("\uD83D\uDE2D \uD83D\uDE0E \uD83E\uDD2B \uD83D\uDE41 \uD83E\uDD0F \uD83D\uDC3B ")
        println("Closing driver ...")
        driver.second.close()
    }
}
