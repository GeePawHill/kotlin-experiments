package org.geepawhill.experiment

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage

import tornadofx.*

class Main : Application() {

    override fun start(stage: Stage) {
        try {
            val scene = Scene(Button("This is a button."))
            stage.scene = scene
            stage.isMaximized = true
            stage.fullScreenExitHint = ""
            stage.show()
        } catch (e: Exception) {
            e.printStackTrace()
            Platform.exit()
        }

    }
}