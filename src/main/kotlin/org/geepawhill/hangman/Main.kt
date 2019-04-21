package org.geepawhill.hangman

import kotlin.system.exitProcess


fun main() {
    println("Let's play hangman!")
    var game = newGame()
    while(true) {
        print("> ")
        val input = readLine()
        if(input==null) {
            println("Didn't get a string")
            continue
        }
        val cased = input.toUpperCase()
        when(cased) {
            "" -> println("0 to quit, 1 to start a new game, or a letter to guess that letter ")
            "0" -> exitProcess(0)
            "1" -> game = newGame()
            else -> {
                if(game.status!=Response.Status.ONGOING) {
                    println("This game is over, use 1 to start a new one, or 0 to quit.")
                }
                else {
                    game.guess(cased[0])
                    println(game.revealed)
                }
            }
        }


    }
}

private fun newGame():Game {
    println("Cool, a new game!")
    val result = Game()
    println(result.revealed)
    return result
}
