package com.example.project

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class MainApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(MainApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 900.0, 600.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(MainApplication::class.java)
}