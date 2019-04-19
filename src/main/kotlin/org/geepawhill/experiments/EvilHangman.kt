package org.geepawhill.experiments

class EvilHangman(val wordLength: Int, private val dictionary: MutableList<String> = mutableListOf()) {

    var hungMon: String = "_"

    fun guess(letter: Char) {
        dictionary -= letter.toString()

        if (dictionary.isEmpty()) {
            hungMon = letter.toString()
        }
    }
}