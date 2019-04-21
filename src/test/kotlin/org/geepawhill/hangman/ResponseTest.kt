package org.geepawhill.hangman

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ResponseTest {

    private val badGuessesAllowed=10

    @Test
    fun `failed guess`()
    {
        val response = Response("COW", revealed = "___", status = Response.Status.ONGOING).guess('A', badGuessesAllowed)
        assertThat(response.isWin).isFalse()
        assertThat(response.revealed).isEqualTo("___")
    }

    @Test
    fun `last failed guess loses`() {
        val response = Response("COW", "___", Response.Status.ONGOING, "123456789").guess('A', badGuessesAllowed)
        assertThat(response.status).isEqualTo(Response.Status.LOST)
    }

    @Test
    fun `duplicate success letters in word`() {
        val response = Response("MAMA", revealed = "____", status = Response.Status.ONGOING).guess('A', badGuessesAllowed)
        assertThat(response.revealed).isEqualTo("_A_A")
    }

    @Test
    fun `Failed guesses are recorded`()
    {
        val response = Response("COW", revealed = "___", status = Response.Status.ONGOING).guess('A', badGuessesAllowed)
        assertThat(response.badGuesses).isEqualTo("A")
        assertThat(response.guess('B', badGuessesAllowed).badGuesses).isEqualTo("AB")
    }

    @Test
    fun `it is a win if this guess makes the revealed string the same as the word`()
    {
        val response = Response("ABC", revealed = "_BC", status = Response.Status.ONGOING).guess('A', badGuessesAllowed)
        assertThat(response.isWin).isTrue()
        assertThat(response.revealed).isEqualTo("ABC")
    }

    @Test
    fun `it is a loss if you run out of bad guesses`()
    {
        val response = Response("ABC", "_BC", Response.Status.ONGOING).guess('Z', 1)
        assertThat(response.status).isEqualTo(Response.Status.LOST)
    }

    @Test
    fun `duplicate successes are idempotent`() {
        val response = Response("ABC", revealed = "___", status = Response.Status.ONGOING).guess('A', badGuessesAllowed)
        val duplicate = response.guess('A', badGuessesAllowed)
        assertThat(duplicate).isEqualToComparingFieldByFieldRecursively(response)
    }

    @Test
    fun `duplicate failures are idempotent`() {
        val response = Response("ABC", revealed = "___", status = Response.Status.ONGOING).guess('Q', badGuessesAllowed)
        val duplicate = response.guess('Q', badGuessesAllowed)
        assertThat(duplicate).isEqualToComparingFieldByFieldRecursively(response)
    }


    @Test
    fun `accurate guess is last character`()
    {
        val response = Response("ABC", revealed = "AB_", status = Response.Status.ONGOING).guess('C', badGuessesAllowed)
        assertThat(response.isWin).isTrue()
    }

}
