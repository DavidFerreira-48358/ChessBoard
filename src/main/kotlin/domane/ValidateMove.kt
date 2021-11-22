package domane

import storage.Board

val piecesSet:HashSet<Char> = hashSetOf('P','R','N','Q','K','B')
val columns:Array<Char> = arrayOf('a','b','c','d','e','f','g','h')


/**
 * Function that sanitises the command given
 * @return null or the sanitized command
 */
fun sanitiseString(string: String?,board: Board): Move? { //string[1] = piece, string[2]=arrayFrom, string[3]= whereFrom, string[4] = arrayTo, string[5] = whereTo
    return if (string == null || string.length in 6..7) null //por enquanto, posteriormente adicionar hipotese de haver string com peça mas sem pos incial
    else decodeMove(string.split(""), board)
}

/**
 * Function that decodes the command given
 * @param s(sanitized command)
 * @return The command properly separated
 */
private fun decodeMove(s: List<String>, board: Board): Move? {
    return when (s.lastIndex) {
        6 -> {//commando completo
            if (((s[1][0].toUpperCase() !in piecesSet ||
                        s[4][0] !in columns) ||
                        (s[3] !in "0123456789" || s[5] !in "0123456789") ||
                        !isValid(s[1][0].toUpperCase(), s[2][0] - 'a', 8 - s[3].toInt(), board))
            ) null
            else Move(
                piece = s[1][0].toUpperCase(),
                from = Pos(s[2][0] - 'a', 8 - s[3].toInt()),
                to = Pos(s[4][0] - 'a', 8 - s[5].toInt())
            )
        }
        5 -> {//commando onde peça é omitida
            if (s[2] !in "0123456789" || s[4] !in "0123456789" ||
                !isValid('P', s[1][0] - 'a', 8 - s[2].toInt(), board)
            ) null
                else Move(
                    piece = 'P',
                    from = Pos(s[1][0] - 'a', 8 - s[2].toInt()),
                    to = Pos(s[3][0] - 'a', 8 - s[4].toInt())
                )
            }
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
private fun isValid(piece: Char, columnPos: Int, linePos: Int, board: Board): Boolean {
    return (columnPos in 0..7 && linePos in 0..7 &&
            board.getPieceAt(columnPos, linePos) != null
            && piecesSet.contains(piece.toUpperCase())
            && (board.getPieceAt(columnPos, linePos)!!.piece == piece ||
            board.getPieceAt(columnPos, linePos)!!.piece.toUpperCase() == piece))
}