package org.geepawhill.hangman

class Response(val word: String, val revealed: String, val status: Status, val badGuesses: String = "") {

    enum class Status  {
        WON,
        LOST,
        ONGOING
    }

    val isWin: Boolean get()= status== Status.WON

    fun guess(letter: Char): Response {
        for (index in 0..word.length - 1) {
            if (word[index] == letter) {
                val newRevealed = replaceAllCorrectLetters(letter)
                val newStatus = when {
                    newRevealed==word -> Status.WON
                    else -> Status.ONGOING
                }
                return Response(word, newRevealed, status = newStatus)
            }
        }
        if(badGuesses.contains(letter)) return this
        val newStatus = when {
            status == Status.WON -> Status.WON
            else -> Status.ONGOING
        }
        return Response(word, revealed, newStatus, badGuesses + letter)
        return this
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