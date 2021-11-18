package storage

import com.mongodb.client.MongoDatabase
import mongoDB.*

data class GameState(val _id:String,val movement:String)

class DbOperations(private val db:MongoDatabase) {
    fun post(collectionId:String, move:GameState){
        db.createDocument(collectionId,move)
    }
    fun read(id:String,gameid:String):GameState?{
        return db.getCollectionWithId<GameState>(id).getDocument(gameid)
    }
    fun put(id:String,move:GameState){
        db.getCollectionWithId<GameState>(id).updateDocument(move)
    }
}