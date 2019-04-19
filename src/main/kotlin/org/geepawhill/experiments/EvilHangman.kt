package org.geepawhill.experiments

class EvilHangman(val wordLength: Int) {
    val dictionary = mutableListOf("a", "i")

    var hungMon: String = "_"

    fun guess(letter: Char) {
        dictionary -= letter.toString()

        if (dictionary.isEmpty()) {
            hungMon = letter.toString()
        }
    }

}
