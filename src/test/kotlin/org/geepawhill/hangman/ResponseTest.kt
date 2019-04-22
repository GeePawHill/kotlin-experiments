package org.geepawhill.hangman

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ResponseTest {

    private val badGuessesAllowed=10

    @Test
    fun `failed guess means no change to revealed`()
    {
        val response = Response("COW", "___", Response.Status.ONGOING).guess('A', badGuessesAllowed)
        assertThat(response.revealed).isEqualTo("___")
    }

    @Test
    fun `failed guesses are recorded`()
    {
        val response = Response("COW", "___", Response.Status.ONGOING).guess('A', badGuessesAllowed)
        assertThat(response.badGuesses).isEqualTo("A")
    }

    @Test
    fun `failed guesses are appended`()
    {
        val response = Response("COW", "___", Response.Status.ONGOING, "A").guess('B', badGuessesAllowed)
        assertThat(response.guess('B', badGuessesAllowed).badGuesses).isEqualTo("AB")
    }

    @Test
    fun `last failed guess loses`() {
        val response = Response("COW", "___", Response.Status.ONGOING, "123456789").guess('A', badGuessesAllowed)
        assertThat(response.status).isEqualTo(Response.Status.LOST)
    }

    @Test
    fun `success guess`() {
        val response = Response("ABC", "___", Response.Status.ONGOING, "").guess('A', badGuessesAllowed)
        assertThat(response.status).isEqualTo(Response.Status.ONGOING)
        assertThat(response.revealed).isEqualTo("A__")
    }

    @Test
    fun `success guess is last character`()
    {
        val response = Response("ABC", "___", Response.Status.ONGOING).guess('C', badGuessesAllowed)
        assertThat(response.revealed).isEqualTo("__C")
    }

    @Test
    fun `reveals duplicate success letters`() {
        val response = Response("MAMA", "____", Response.Status.ONGOING).guess('A', badGuessesAllowed)
        assertThat(response.revealed).isEqualTo("_A_A")
    }

    @Test
    fun `success guess wins`()
    {
        val response = Response("ABC", "AB_", Response.Status.ONGOING).guess('C', badGuessesAllowed)
        assertThat(response.status).isEqualTo(Response.Status.WON)
        assertThat(response.revealed).isEqualTo("ABC")
    }







    @Test
    fun `reduce list by hit letters`()
    {
        val response = Response(listOf("MA","ME"), "__", Response.Status.ONGOING).guess('M', badGuessesAllowed).guess('A',10)
        assertThat(response.badGuesses).contains("A")
        assertThat(response.guess('E',10).status).isEqualTo(Response.Status.WON)
    }

    @Test
    fun `eliminates letters`()
    {
        val response = Response(listOf("ME","MA"), "__", Response.Status.ONGOING).guess('M', badGuessesAllowed).guess('E',10)
        assertThat(response.badGuesses).contains("E")
    }







    @Test
    fun `it is a win if this guess makes the revealed string the same as the word`()
    {
        val response = Response("ABC", "_BC", Response.Status.ONGOING).guess('A', badGuessesAllowed)
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
        val response = Response("ABC", "___", Response.Status.ONGOING).guess('A', badGuessesAllowed)
        val duplicate = response.guess('A', badGuessesAllowed)
        assertThat(duplicate).isEqualToComparingFieldByFieldRecursively(response)
    }

    @Test
    fun `duplicate failures are idempotent`() {
        val response = Response("ABC", "___", Response.Status.ONGOING).guess('Q', badGuessesAllowed)
        val duplicate = response.guess('Q', badGuessesAllowed)
        assertThat(duplicate).isEqualToComparingFieldByFieldRecursively(response)
    }




}
