package storage

import com.mongodb.client.MongoDatabase
import mongoDB.*

/**
 * Data class that holds the value of the game state
 * @param _id, the id of the game
 * @param movement, the movement string
 */
data class GameState(val _id:String,val movement:String)

class DbOperations(private val db:MongoDatabase) {

    /**
     * Function that creates the collection form the given game name
     * @param collectionId, the name of the collection
     * @param move, the game state
     */
    fun post(collectionId:String, move:GameState){
        db.createDocument(collectionId,move)
    }

    /**
     * Function that reads the game for new strings
     * @param id,the collection id
     * @param gameid, the gameid
     * @return a [GameState] with the game id and the movement
     */
    fun read(id:String,gameid:String):GameState?{
        return db.getCollectionWithId<GameState>(id).getDocument(gameid)
    }

    /**
     * Function that reads the game for new strings
     * @param id,the collection id
     * @param move, the the move to replace the file
     */
    fun put(id:String,move:GameState){
        db.getCollectionWithId<GameState>(id).updateDocument(move)
    }
}