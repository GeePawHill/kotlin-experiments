package org.geepawhill.hangman

class Game {

    val revealed get() = response.revealed
    val status get() = response.status

    var response = Response("BEARD", "_____", Response.Status.ONGOING, badGuessesAllowed = 10)

    fun guess(letter:Char) {
        response = response.guess(letter)
    }
}