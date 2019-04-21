package org.geepawhill.hangman

import kotlin.system.exitProcess


fun main() {
    println("Let's play hangman!")
    var response = newGame()
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
            "1" -> response = newGame()
            else -> {
                if(response.status!=Response.Status.ONGOING) {
                    println("This game is over, use 1 to start a new one, or 0 to quit.")
                }
                else {
                    response = response.guess(cased[0])
                    println(response.revealed)
                }
            }
        }


    }
}

private fun newGame():Response {
    println("Cool, a new game!")
    val result = Response("BEARD", "_____", Response.Status.ONGOING)
    println(result.revealed)
    return result
}
