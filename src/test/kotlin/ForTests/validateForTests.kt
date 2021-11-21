package ForTests

import domane.Commands


val piecesSetString:HashSet<String> = hashSetOf("P","R","N","Q","K","B")
val piecesSet:HashSet<Char> = hashSetOf('P','R','N','Q','K','B')

class validateForTests(private val string:String?, private val board: BoardForTests) {
    /**
     * Class that contain the x and y value of a Position
     */
    data class Pos(val x:Int, val y:Int)

    /**
     * Class that contains the decoded move
     */
    data class Move(val piece:Char, val from:Pos, val to:Pos)

    /**
     * Function that sanitises the command given
     * @return null or the sanitized command
     */
    fun sanitiseString():Move?{ //string[1] = piece, string[2]=arrayFrom, string[3]= whereFrom, string[4] = arrayTo, string[5] = whereTo
        return if(string == null||string.length in 6..7) null //por enquanto, posteriormente adicionar hipotese de haver string com peça mas sem pos incial
        else decodeMove(string.split(""))
    }

    /**
     * Function that decodes the command given
     * @param s(sanitized command)
     * @return The command properly separated
     */
    private fun decodeMove(s:List<String>):Move?{
        return when(s.lastIndex){
            6 ->{//commando completo
                if((s[1][0] !in piecesSet)
                    ||s[3] !in "0".."7"
                    ||s[5] !in "0".."7"
                    ||isValid(s[1][0].toUpperCase(),s[2][0]-'a',8-s[3].toInt(),s[4][0]-'a',8-s[5].toInt()) == Commands.INVALID) null
                else Move(
                    piece = s[1][0].toUpperCase(),
                    from = Pos(s[2][0]-'a',8-s[3].toInt()),
                    to = Pos(s[4][0]-'a',8-s[5].toInt())
                )}
            5 ->{//commando onde peça é omitida
                if(s[3] !in "0".."7"
                    ||s[5] !in "0".."7"
                    ||isValid('P',s[1][0]-'a',8-s[2].toInt(),s[3][0]-'a',8-s[4].toInt()) == Commands.INVALID) null
                else Move(
                    piece = 'P',
                    from = Pos(s[1][0]-'a',8-s[2].toInt()),
                    to = Pos(s[3][0]-'a',8-s[4].toInt())
                )}
            else -> null
        }
    }
    /**
     * Function that validates the move command passed
     * @param piece the piece in question
     * @param columnPos the x coordinate of the piece to move
     * @param linePos the y coordinate of the piece to move
     * @return If the Action is valid or not
     */
    private fun isValid(piece:Char, xPosFrom:Int, yPosFrom:Int, xPosTo:Int, yPosTo:Int):Commands{
        return if (
            xPosFrom in 0 ..7 && yPosFrom in 0..7
            && xPosTo in 0..7 && yPosTo in 0..7
            && board.getPieceAt(xPosFrom,yPosFrom)!= null
            && piecesSet.contains(piece.toUpperCase())
            && (board.getPieceAt(xPosFrom,yPosFrom)!!.piece == piece ||
                    board.getPieceAt(xPosFrom,yPosFrom)!!.piece.toUpperCase() == piece)
        ) Commands.VALID
        else Commands.INVALID
    }
}