package org.geepawhill.hangman

class Response(val word: String, val revealed: String, val status: Status, val badGuessesAllowed: Int, val badGuesses: String = "") {

    enum class Status  {
        WON,
        LOST,
        ONGOING
    }

    val isWin: Boolean get()= status== Status.WON

    fun guess(letter: Char): Response {
        // search for valid new letters, if we find them, get outta here
        for (index in 0..word.length - 1) {
            if (word[index] == letter) {
                val newRevealed = replaceAllCorrectLetters(letter)
                val newStatus = when {
                    newRevealed==word -> Status.WON
                    else -> Status.ONGOING
                }
                return Response(word, newRevealed, status = newStatus, badGuessesAllowed = 10)
            }
        }

        // if they already guessed this letter, get outta here with no change
        if(badGuesses.contains(letter)) return this

        // this is a bad guess
        val newStatus = if(badGuesses.length==badGuessesAllowed-1) Status.LOST
        else Status.ONGOING

        return Response(word, revealed, newStatus, 10, badGuesses + letter)
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