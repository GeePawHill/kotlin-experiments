package org.geepawhill.experiments

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EvilHangmanTest {
    @Test
    fun `pick a word length`() {
        val hangman = EvilHangman(10)

        assertThat(hangman.wordLength).isEqualTo(10)
    }

    @Test
    fun `guess a letter and it's not found`() {
        val hangman = EvilHangman(1, mutableListOf("a", "i"))

        hangman.guess('a')

        assertThat(hangman.hungMon).isEqualTo("_")
    }

    @Test
    fun `guess a letter and it is found`() {
        val hangman = EvilHangman(1, mutableListOf("a", "i"))

        hangman.guess('a')
        hangman.guess('i')

        assertThat(hangman.hungMon).isEqualTo("i")
    }

    @Test
    fun `guess one letter in the other order`() {
        val hangman = EvilHangman(1, mutableListOf("a", "i"))

        hangman.guess('i')
        hangman.guess('a')

        assertThat(hangman.hungMon).isEqualTo("a")
    }

    @Test
    fun `guess letters not in the dictionary`() {
        val hangman = EvilHangman(1, mutableListOf("a", "i"))

        hangman.guess('x')

        assertThat(hangman.hungMon).isEqualTo("_")
    }

//    @Test
//    fun `two letter word`() {
//        val hangman = EvilHangman(wordLength = 2, listOf("ma", "me"))
//
//        hangman.guess('x')
//
//        assertThat(hangman.hungMon).isEqualTo("__")
//    }
}