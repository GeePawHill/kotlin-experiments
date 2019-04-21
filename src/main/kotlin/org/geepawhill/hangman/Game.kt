package org.geepawhill.hangman

class Game {

    val revealed get() = response.revealed
    val status get() = response.status

    val badGuessesAllowed = 10

    var response = Response("BEARD", "_____", Response.Status.ONGOING)

    fun guess(letter:Char) {
        response = response.guess(letter, badGuessesAllowed)
    }
}