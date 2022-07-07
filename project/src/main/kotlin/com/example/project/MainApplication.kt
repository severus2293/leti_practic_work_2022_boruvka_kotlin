package com.example.project

import com.example.project.graph.ZoomableScrollPane
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class MainApplication : Application() {
    override fun start(stage: Stage) {

        // root.center = graph.getScrollPane(); // разместить по центру холст вероятно
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