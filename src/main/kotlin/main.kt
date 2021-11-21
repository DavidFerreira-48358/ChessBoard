import console.readCommand
import domane.CommandResult
import domane.ExitResult
import mongoDB.createMongoClient
import storage.*

fun main() {
    val dbInfo = getDBConnectionInfo()
    val driver =
        if (dbInfo.mode == DbMode.REMOTE) createMongoClient(dbInfo.connectionString)
        else createMongoClient()
    val dbOperations = DbOperations(driver.getDatabase(dbInfo.dbName))
    //cria a instacia das oepraçoes q vamos usar
    try {
        val board = MongoDbBoard(dbOperations)//a board
        val dispatcher = Handlers(board)//constroi os commandos com a board
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
        println("Closing driver ...")
        driver.close()
    }
}
