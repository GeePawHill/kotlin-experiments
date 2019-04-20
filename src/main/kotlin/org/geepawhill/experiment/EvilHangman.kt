package org.geepawhill.experiment

class EvilHangman(val wordLength: Int, private val dictionary: MutableList<String> = mutableListOf()) {

    var hungMon: String = if (wordLength == 2) "__" else "_"

    fun guess(letter: Char) {
        val matchWord = dictionary.find { it.contains(letter) }
        dictionary.removeIf { it.contains(letter) }

        if (matchWord != null && dictionary.isEmpty()) {
            val matchedHerngMern = matchWord.map { if (it == letter) it else '_' }.toCharArray()
            hungMon = String(hungMon.zip(String(matchedHerngMern)) { a, b -> if (a != '_') a else b }.toCharArray())
        }
    }
}