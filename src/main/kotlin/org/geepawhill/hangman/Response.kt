package org.geepawhill.hangman

open class Response(
        protected val dictionary: List<String>,
        val revealed: String,
        val status: Status,
        val badGuesses: String = ""
) {

    constructor(word: String,
                revealed: String,
                status: Status,
                badGuesses: String = "" ) : this(listOf(word),revealed,status,badGuesses)


    enum class Status  {
        WON,
        LOST,
        ONGOING
    }

    open fun make(dictionary: List<String>,revealed: String, status: Status, badGuesses: String) = Response(dictionary,revealed,status,badGuesses)

    fun guess(letter: Char, badGuessesAllowed: Int): Response {
        if(isHit(letter)) return makeHitResponse(letter)
        if(missIsDuplicate(letter)) return this
        return makeMissResponse(letter, badGuessesAllowed)
    }

    private fun isHit(letter: Char): Boolean {
        return dictionary.filter { !it.contains(letter) }.isEmpty()
    }

    private fun missIsDuplicate(letter: Char) = badGuesses.contains(letter)

    private fun makeMissResponse(letter: Char, badGuessesAllowed: Int): Response {
        val newStatus = if (badGuesses.length == badGuessesAllowed - 1) Status.LOST
        else Status.ONGOING
        return make(dictionary.filter { !it.contains(letter) }, revealed, newStatus, badGuesses + letter)
    }

    private fun makeHitResponse(letter: Char): Response {
        val newRevealed = replaceAllCorrectLetters(letter)
        val newStatus = when (newRevealed) {
            dictionary[0] -> Status.WON
            else -> Status.ONGOING
        }
        return make(dictionary, newRevealed, newStatus, badGuesses)
    }

    private fun replaceAllCorrectLetters(letter: Char): String {
        var result = ""
        for (index in 0 until dictionary[0].length) {
            if (dictionary[0][index] == letter) result += letter
            else result += revealed[index]
        }
        return result
    }
}