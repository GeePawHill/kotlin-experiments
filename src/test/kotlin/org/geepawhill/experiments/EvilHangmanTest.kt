package org.geepawhill.experiments

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EvilHangmanTest {
    @Test
    fun `pick a word length`() {
        val hangman = EvilHangman(wordLength = 10)

        assertThat(hangman.wordLength).isEqualTo(10)
    }
}
