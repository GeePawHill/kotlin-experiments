package org.geepawhill.hangman

class Game {

    val revealed get() = response.revealed
    val status get() = response.status

    var response = Response("BEARD", badGuessesAllowed = 10, revealed = "_____", status = Response.Status.ONGOING)

    fun guess(letter:Char) {
        response = response.guess(letter)
    }
}