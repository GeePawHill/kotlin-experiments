package org.geepawhill.hangman

class Response(
        val word: String,
        val revealed: String,
        val status: Status,
        val badGuessesAllowed: Int,
        val badGuesses: String = ""
) {

    enum class Status  {
        WON,
        LOST,
        ONGOING
    }

    val isWin: Boolean get()= status== Status.WON

    fun guess(letter: Char): Response {
        if(isHit(letter)) return makeHitResponse(letter)
        if(missIsDuplicate(letter)) return this
        return makeMissResponse(letter)
    }

    private fun isHit(letter: Char): Boolean = word.contains(letter)

    private fun missIsDuplicate(letter: Char) = badGuesses.contains(letter)

    private fun makeMissResponse(letter: Char): Response {
        val newStatus = if (badGuesses.length == badGuessesAllowed - 1) Status.LOST
        else Status.ONGOING
        return Response(word, revealed, newStatus, 10, badGuesses + letter)
    }

    private fun makeHitResponse(letter: Char): Response {
        val newRevealed = replaceAllCorrectLetters(letter)
        val newStatus = when {
            newRevealed == word -> Status.WON
            else -> Status.ONGOING
        }
        return Response(word, newRevealed, status = newStatus, badGuessesAllowed = 10)
    }

    private fun replaceAllCorrectLetters(letter: Char): String {
        var result = ""
        for (index in 0..word.length - 1) {
            if (word[index] == letter) result += letter
            else result += revealed[index]
        }
        return result
    }
}