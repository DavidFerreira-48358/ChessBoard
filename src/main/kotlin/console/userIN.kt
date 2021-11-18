package console

/*/**
 * Reads from the console the local user information.
 */
fun readLocalUserInfo(): User {
    while (true) {
        print("Please enter your id: ")
        val user = readln().trim().toUserID()
        if (user == null) println("Error: user ids cannot contain whitespace characters")
        else return user
    }
}*/

/**
 * Reads a line from the console and parses it to obtain the corresponding command.
 * @return a pair bearing the command text and its parameter
 */
fun readCommand(): Pair<String, String?> {
    print("> ")
    val input = readln()
    val command = input.substringBefore(delimiter = ' ')
    val argument = input.substringAfter(delimiter = ' ', missingDelimiterValue = "").trim()
    return Pair(command.trim().toUpperCase(), argument.ifBlank { null })
}

/**
 * Let's use this while we don't get to Kotlin v1.6
 */
fun readln() = readLine()!!