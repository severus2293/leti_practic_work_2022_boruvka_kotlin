package com.example.project

import com.example.project.graph.ZoomableScrollPane
import com.example.project.graph.cells.Cell
//import com.example.project.graph.cells.CircleCell
import com.example.project.graph.model.Model
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Modality
import javafx.stage.Stage
import java.io.File

class MainController {


    private var cell_number: Int = 1 //имена вершин
    private var edge: MutableList<Cell> = mutableListOf()
    var dragContext: DragContext = DragContext()
    var weight: Int = 0
    @FXML
    private lateinit var paint: AnchorPane
    @FXML
    private  lateinit var  scroll_pane: ScrollPane  // создать сцену
    @FXML
    private var holst: Pane = Pane()
    @FXML
    private lateinit var my_console: TextArea
    @FXML
    private lateinit var add_vert_button: Button
    @FXML
    private lateinit var open_file_button: Button
    @FXML
    private lateinit var save_file_button: Button
    @FXML
    private  var node: AnchorPane = AnchorPane()
    private val model: Model = Model()
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
    fun initialize() {
    }
    fun set_default(){
        for(cell in model.getallCells()){
            cell.onMousePressed = onMousePressedEventHandler // определить функцию нажатия //
            cell.onMouseDragged = onMouseDraggedEventHandler // определить функцию перетаскивания
            cell.onMouseReleased = onMouseReleasedEventHandler // определить функцию отпускания мыши
        }
         //   scroll_pane.onMouseClicked = null
            scroll_pane.onMouseDragged = null
            scroll_pane.onMouseReleased = null

           // holst.onMouseClicked = null
            //holst.onMouseDragged = null
            //holst.onMouseReleased = null

    }
    fun off_default() {
        for (cell in model.getallCells()) {
          //  cell.localToParent((cell.view as Circle).layoutBounds)
         //   cell.layoutX = 20.0
           // cell.layoutY = 10.0

            cell.onMousePressed = null // определить функцию нажатия //
            cell.onMouseDragged = null // определить функцию перетаскивания
            cell.onMouseReleased = null // определить функцию отпускания мыши
        }
    }
    class DragContext {
        // текущие координаты
        var x = 0.0
        var y = 0.0
    }
    var onMousePressedEventHandler =
        EventHandler<MouseEvent> { event ->
            val node = event.source as Node
            val scale = 1.0 // получить скалирование?
            dragContext.x = node.boundsInParent.minX * scale - event.screenX  // присвоение координат
            dragContext.y = node.boundsInParent.minY * scale - event.screenY
           // dragContext.x = event.x
            //dragContext.y = event.y
        }
    var onMouseDraggedEventHandler =
        EventHandler<MouseEvent> { event ->
            val node = event.source as Node
            var offsetX = dragContext.x + event.screenX //event.screenX + dragContext.x // изменить координату
            var offsetY = dragContext.y + event.screenY// event.screenY + dragContext.y // изменить координату
            // adjust the offset in case we are zoomed
            val scale = 1.0
                //offsetX /= scale
            //offsetY /= scale
            node.relocate(offsetX, offsetY) //переместить элемент на соответствующие координаты
        }
    var onMouseReleasedEventHandler: EventHandler<MouseEvent> = EventHandler { }
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
    private fun clear_holst(){

    }
    @FXML
    private fun del_vert(){
    }
    @FXML
    private fun add_edge(){
       for(cell in model.getallCells()){
          // cell.onMousePressed = onMousePressedEventHandler_add_edge
       }
    }
    @FXML
    private fun del_edge(){

    }
    @FXML
    private fun step_forward(){

    }

    //###################################################################################
    // ивент добавления вершины по нажатию
    var onMousePressedEventHandler_add_vert =
        EventHandler<MouseEvent> { event ->
            val node = event.source as Node
            val x = event.x  // присвоение координат со смещением радиуса
            val y = event.y
            val cell: Cell = Cell(cell_number.toString(),x,y)
            cell_number += 1 // новое имя для другой вершины
            holst.children.add(cell) // добавить на холст
            model.addCell(cell) // добавить в структуру
            model.merge()
            set_default()
        }
    //###################################################################################
    // ивент добавления ребра по нажатию
   /* var onMousePressedEventHandler_add_edge =
        EventHandler<MouseEvent> { event ->
            val node = event.source as CircleCell
            edge.add(node)
            println(edge.size)
            if(edge.size == 2){
                if(edge[0].getcellId().toInt() != edge[1].getcellId().toInt()){
                    val stage = Stage()
                    val loader = FXMLLoader(javaClass.getResource("edge_weight_dialog.fxml"))
                    val root = loader.load<Parent>()
                    val lc: NewFolderController = loader.getController<Any>() as NewFolderController
                    lc.main = this
                    stage.title = "Create folder"
                    stage.scene = Scene(root, 400.0, 200.0)
                    stage.initModality(Modality.APPLICATION_MODAL)
                    stage.showAndWait()
                    holst.children.add(model.addEdge(edge[0].getcellId(),edge[1].getcellId(),weight))

                }
                edge.clear()
            }
            event.consume()
        }*/
    //###################################################################################
    @FXML
    private fun add_vert(event: ActionEvent) {
        event.consume()
        if (add_vert_button.style == "-fx-background-color: green" ){
            add_vert_button.style = "-fx-background-color: #ffa000"
            scroll_pane.onMouseClicked = null
           // holst.onMouseClicked = null
        }
        else{
            add_vert_button.style = "-fx-background-color: green"
            // отключтить функционал остальных кнопок
            //scroll_pane.onMouseClicked = onMousePressedEventHandler_add_vert
            scroll_pane.onMouseClicked = onMousePressedEventHandler_add_vert
        }
    }

    fun change(mouseEvent: MouseEvent) {
         println("df")
    }

    //private lateinit var welcomeText: Label

   // @FXML
    /*private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
    }*/
}