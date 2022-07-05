package com.example.project

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import java.io.File


class MainController {
    @FXML
    private lateinit var my_console: TextArea
    @FXML
    private lateinit var  add_vert_button: Button
    @FXML
    private lateinit var open_file_button: Button
    @FXML
    private lateinit var save_file_button: Button
    @FXML
    private lateinit var node: AnchorPane
    @FXML
    private fun open_file(event: ActionEvent){
        val ex1: ExtensionFilter = ExtensionFilter("Text Files","*.txt")
        event.consume()
         val filechooser: FileChooser = FileChooser()
         filechooser.extensionFilters.add(ex1)
         val selectedfile: File? = filechooser.showOpenDialog(node.scene.window)
         if(selectedfile != null){
             println(selectedfile.path)
         }
    }
    @FXML
    private fun result(event: ActionEvent){
        my_console.appendText("Hello World!\n")
    }
    @FXML
    private fun save_file(event:ActionEvent){
        val ex1: ExtensionFilter = ExtensionFilter("Text Files","*.txt")
        event.consume()
        val filechooser: FileChooser = FileChooser()
        filechooser.extensionFilters.add(ex1)
        val selectedfile: File? = filechooser.showSaveDialog(node.scene.window)
        if(selectedfile != null){
            println(selectedfile.path)
        }
    }
    @FXML
    private fun add_vert(event: ActionEvent) {
        event.consume()
        println("Hello, World!")
        if (add_vert_button.style == "-fx-background-color: green" ){
            add_vert_button.style = "-fx-background-color: #ffa000"
        }
        else{
            add_vert_button.style = "-fx-background-color: green"
        }
    }

    //private lateinit var welcomeText: Label

   // @FXML
    /*private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
    }*/
}