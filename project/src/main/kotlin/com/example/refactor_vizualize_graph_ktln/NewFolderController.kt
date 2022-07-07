package com.example.refactor_vizualize_graph_ktln

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane

class NewFolderController {
    @FXML
    lateinit var dialog_edge: AnchorPane
    @FXML
    lateinit var weight_value: TextField
    lateinit var  main: HelloController // ссылка на основной контроллер
    @FXML
    private fun add_edge(){
        main.weight = weight_value.text.toInt()
        dialog_edge.scene.window.hide()
    }
}