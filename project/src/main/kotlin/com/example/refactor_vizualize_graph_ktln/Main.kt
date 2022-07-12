package com.example.refactor_vizualize_graph_ktln


import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

 class Main : Application() {

     override fun start(stage: Stage) {
         val fxmlLoader = FXMLLoader(Main::class.java.getResource("hello-view.fxml"))
         val scene = Scene(fxmlLoader.load(), 900.0, 600.0)
         stage.title = "Hello!"
         stage.scene = scene
         stage.show()
     }
     fun main(){
         Application.launch(Main::class.java)
     }

}
