package console



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