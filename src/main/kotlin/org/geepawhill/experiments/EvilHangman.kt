package org.geepawhill.experiments

class EvilHangman(val wordLength: Int) {
    val dictionary = mutableListOf("a", "i")

    var hungMon: String = "_"

    fun guess(letter: Char) {
        if (dictionary.size == 1 && dictionary.contains(letter.toString())) {
            hungMon = letter.toString()
        } else {
            dictionary -= letter.toString()
        }
    }

}
