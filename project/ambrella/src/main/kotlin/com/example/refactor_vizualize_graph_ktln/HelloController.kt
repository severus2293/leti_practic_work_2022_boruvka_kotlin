package com.example.refactor_vizualize_graph_ktln

import com.fxgraph.graph.Edge
import com.fxgraph.graph.Model
import com.fxgraph.vizualization.VXCell
import com.fxgraph.vizualization.VXEdge
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage

class HelloController {
 private var cell_number: Int = 1
 var weight: Int = 0
 private var edge: MutableList<VXCell> = mutableListOf()
 private var model: Model = Model()
 private var vxcells: MutableList<VXCell> = mutableListOf()
 @FXML
 private lateinit var add_vert_button: Button
 @FXML
 private lateinit var add_edge_button: Button
 @FXML
 private lateinit var scroll_pane: ScrollPane
 @FXML
 private lateinit var holst: Pane
 @FXML
 private fun save_file() {

 }

 @FXML
 private fun open_file() {
 }

 @FXML
 fun clear_holst() {

 }

 @FXML
 private fun add_vert(event: ActionEvent) {
  event.consume()
  if (add_vert_button.style == "-fx-background-color: green") {
   add_vert_button.style = "-fx-background-color: #ffa000"
   scroll_pane.onMouseClicked = null
   // holst.onMouseClicked = null
  } else {
   add_vert_button.style = "-fx-background-color: green"
   scroll_pane.onMouseClicked =  onMousePressedEventHandler_add_vert
   // отключтить функционал остальных кнопок
   //scroll_pane.onMouseClicked = onMousePressedEventHandler_add_vert
  // scroll_pane.onMouseClicked = onMousePressedEventHandler_add_vert
  }
 }
 var onMousePressedEventHandler_add_vert =
  EventHandler<MouseEvent> { event ->
   val node = event.source as Node
   val x = event.x  // присвоение координат со смещением радиуса
   val y = event.y
   val cell: VXCell = VXCell(cell_number.toString(),x,y)
   vxcells.add(cell)
   cell_number += 1 // новое имя для другой вершины
   holst.children.add(cell) // добавить на холст
   model.addCell(cell.cell) // добавить в структуру
   model.merge()
   //set_default()
  }
 @FXML
 private fun del_vert() {

 }

 @FXML
 private fun add_edge(event: ActionEvent) {
  event.consume()
  if (add_edge_button.style == "-fx-background-color: green") {
   add_edge_button.style = "-fx-background-color: #ffa000"
   scroll_pane.onMouseClicked = null
   for(cell in vxcells) {
    cell.enableDrag()
   }
   // holst.onMouseClicked = null
  } else {
   add_edge_button.style = "-fx-background-color: green"
  for(cell in vxcells){
   cell.disableDrag()
   cell.onMousePressed = onMousePressed_add_edge
  }
 }
 }
 var onMousePressed_add_edge = EventHandler<MouseEvent> {event ->
  val node = event.source as VXCell
  node.stroke = Color.FIREBRICK
  edge.add(node)
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
    val e: VXEdge = VXEdge(edge[0],edge[1],weight)
    holst.children.add(e)
    model.addEdge(e.getsource().getcellId(),e.gettarget().getcellId(),weight)
    model.merge()
   }
   edge.clear()
  }
  event.consume()
 }
 @FXML
 private fun del_edge() {

 }

 @FXML
 private fun result() {
    model.BoruvkaMST()
 }

 @FXML
 private fun step_forward() {

 }
}