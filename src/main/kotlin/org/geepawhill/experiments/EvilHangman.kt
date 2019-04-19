package org.geepawhill.experiments

class EvilHangman(val wordLength: Int, private val dictionary: MutableList<String> = mutableListOf()) {

    var hungMon: String = if (wordLength == 2) "__" else "_"

    fun guess(letter: Char) {
        val matchWord = dictionary.find { it.contains(letter) }
        dictionary.removeIf { it.contains(letter) }

        if (matchWord != null && dictionary.isEmpty()) {
            hungMon = String(matchWord.map { if (it == letter) it else '_' }.toCharArray())
        }
    }
}