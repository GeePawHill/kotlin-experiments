package hangman

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ResponseTest {

//    fun mainLoop() {
//        response = new Response()
//        while(true) {
//            get a key
//            if it isn't quit or new game'
//            response = response.guess(key)
//        }
//    }

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

    @Test
    fun `if it was a win, it is a win after any guess`()
    {
        val response = Response("BDEF", "BDE_", Response.Status.WON).guess('F').guess('A')
        assertThat(response.isWin).isTrue()
    }

    @Test
    fun `failed guess`()
    {
        val response = Response("COW", "___", Response.Status.ONGOING).guess('A')
        assertThat(response.isWin).isFalse()
        assertThat(response.revealed).isEqualTo("___")
    }

    @Test
    fun `duplicate success letters in word`() {
        val response = Response("MAMA", "____",Response.Status.ONGOING).guess('A')
        assertThat(response.revealed).isEqualTo("_A_A")
    }

    @Test
    fun `Failed guesses are recorded`()
    {
        val response = Response("COW", "___",Response.Status.ONGOING).guess('A')
        assertThat(response.badGuesses).isEqualTo("A")
        assertThat(response.guess('B').badGuesses).isEqualTo("AB")
    }

    @Test
    fun `it is a win if this guess makes the revealed string the same as the word`()
    {
        val response = Response("ABC", "_BC",Response.Status.ONGOING).guess('A')
        assertThat(response.isWin).isTrue()
        assertThat(response.revealed).isEqualTo("ABC")
    }

    @Test
    fun `duplicate successes are idempotent`() {
        val response = Response("ABC", "___",Response.Status.ONGOING).guess('A')
        val duplicate = response.guess('A')
        assertThat(duplicate).isEqualToComparingFieldByFieldRecursively(response)
    }

    @Test
    fun `duplicate failures are idempotent`() {
        val response = Response("ABC", "___",Response.Status.ONGOING).guess('Q')
        val duplicate = response.guess('Q')
        assertThat(duplicate).isEqualToComparingFieldByFieldRecursively(response)
    }


    @Test
    fun `accurate guess is last character`()
    {
        val response = Response("ABC", "AB_",Response.Status.ONGOING).guess('C')
        assertThat(response.isWin).isTrue()
    }

}
