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
import javafx.scene.text.Text
import javafx.stage.Modality
import javafx.stage.Stage

class HelloController {
 private var cell_number: Int = 1
 var weight: Int = 0
 private var edge: MutableList<VXCell> = mutableListOf()
 private var model: Model = Model()
 private var vxcells: MutableList<VXCell> = mutableListOf()
 private var vxedges: MutableList<VXEdge> = mutableListOf()
 @FXML
 private lateinit var del_edge_button: Button
 @FXML
 private lateinit var add_vert_button: Button
 @FXML
 private lateinit var add_edge_button: Button
 @FXML
 private lateinit var del_vert_button: Button
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
  holst.children.removeAll(vxedges)
  holst.children.removeAll(vxcells)
  for(e in vxedges){
   holst.children.remove(e.get_label())
  }
  vxedges.clear()
  for(cell in vxcells){
   holst.children.remove(cell.get_lable())
  }
  vxcells.clear()
  model.clear_all()
  off_parametrs()
 }

 private fun off_parametrs(){
  for (cell in vxcells){
   cell.disableDrag()
  }
  edge.clear()
  scroll_pane.onMouseClicked = null
 }
 @FXML
 private fun add_vert(event: ActionEvent) {
  event.consume()
  if (add_vert_button.style == "-fx-background-color: green") { // если включена
   add_vert_button.style = "-fx-background-color: #ffa000"
   off_parametrs() // сброс всего
   for(cell in vxcells){
    cell.enableDrag()
   }
  } else {                                                // включаем
   add_vert_button.style = "-fx-background-color: green"
   off_parametrs()                                                    //сброс всех настроек
   scroll_pane.onMouseClicked =  onMousePressedEventHandler_add_vert
  }
 }
 var onMousePressedEventHandler_add_vert =
  EventHandler<MouseEvent> { event ->
   val node = event.source as Node
   val x = event.x  // присвоение координат со смещением радиуса
   val y = event.y
   val cell: VXCell = VXCell(cell_number.toString(),x,y)
   cell.setLabel(Text(cell_number.toString()))
   vxcells.add(cell)
   cell_number += 1 // новое имя для другой вершины
   holst.children.add(cell) // добавить на холст
   holst.children.add(cell.get_lable())
   model.addCell(cell.cell) // добавить в структуру
   model.merge()
   //set_default()
  }
 ////////////////////////////////////////////////////////////////////////////////////
 @FXML
 private fun del_vert(event: ActionEvent) {
  event.consume()
  if(del_vert_button.style == "-fx-background-color: green") { // если включена
   del_vert_button.style = "-fx-background-color: #ffa000"
   off_parametrs() // сброс всего
   for(cell in vxcells){
    cell.enableDrag()
   }
  }
  else{
   del_vert_button.style = "-fx-background-color: green"
   for(cell in vxcells){
    cell.onMousePressed = onMousePressed_del_vert
   }
  }
 }


 var onMousePressed_del_vert = EventHandler<MouseEvent> {event ->
  val node = event.source as VXCell
  model.remove_cell(node.cell)
  cleaning(node)
  node.remove_connection()
  for(e in node.edgesmap.values){

   holst.children.remove(e)
   holst.children.remove(e.get_label())
  }
  vxcells.remove(node)
  holst.children.remove(node.get_lable())
  holst.children.remove(node)
 }
 private fun cleaning(cell: VXCell){
  for(neighbor in cell.neighbors){
   val cur = cell.weightmap[neighbor]
   cur?.let { Edge(cell.cell,neighbor.cell, it) }?.let { model.del_edge(it) }
   cur?.let { Edge(neighbor.cell,cell.cell, it) }?.let { model.del_edge(it) }
  }
 }
 /////////////////////////////////////////////////////////////////////////////
 @FXML
 private fun add_edge(event: ActionEvent) {
  event.consume()
  if (add_edge_button.style == "-fx-background-color: green") {
   add_edge_button.style = "-fx-background-color: #ffa000"
   off_parametrs()
   for(cell in vxcells){
    cell.enableDrag()
   }
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
    e.set_label(Text(weight.toString()))
    vxedges.add(e)
    edge[0].edgesmap[edge[1]] = e
    // edge[0].edges.add(e)
    edge[0].neighbors.add(edge[1])
    edge[0].weightmap[edge[1]] = weight
    edge[1].edgesmap[edge[0]] = e
    // edge[1].edges.add(e)
    edge[1].neighbors.add(edge[0])
    edge[1].weightmap[edge[0]] = weight
    holst.children.add(e.get_label())
    holst.children.add(e)
    model.addEdge(e.getsource().getcellId(),e.gettarget().getcellId(),weight)
    model.merge()
   }
   edge.clear()
  }
  event.consume()
 }
 /////////////////////////////////////////////////////////////////////////////
 @FXML
 private fun del_edge(event: ActionEvent) {
  event.consume()
  if (del_edge_button.style == "-fx-background-color: green") {
   del_edge_button.style = "-fx-background-color: #ffa000"
   off_parametrs()
   for (cell in vxcells) {
    cell.enableDrag()
   }
  } else {
   del_edge_button.style = "-fx-background-color: green"
   for(cell in vxcells){
    cell.disableDrag()
    cell.onMousePressed = onMousePressed_del_edge
   }
  }
 }

 var onMousePressed_del_edge = EventHandler<MouseEvent> {event ->
  val node = event.source as VXCell
  node.stroke = Color.FIREBRICK
  edge.add(node)
  if(edge.size == 2){
   println("del")
   if(edge[0].getcellId().toInt() != edge[1].getcellId().toInt()){
    val cur: Int? = edge[0].weightmap[edge[1]]
    val e: VXEdge? = edge[0].weightmap[edge[1]]?.let { VXEdge(edge[0],edge[1], it) }
    val second_e: VXEdge? = edge[0].weightmap[edge[1]]?.let { VXEdge(edge[1],edge[0], it) }
    edge[0].neighbors.remove(edge[1])
    holst.children.remove(edge[0].edgesmap[edge[1]])
    holst.children.remove(edge[1].edgesmap[edge[0]])
    holst.children.remove(edge[0].edgesmap[edge[1]]?.get_label())
    holst.children.remove(edge[1].edgesmap[edge[0]]?.get_label())
    edge[0].edgesmap.remove(edge[1])
    // edge[0].edges.remove(e)
    // edge[0].edges.remove(second_e)
    edge[0].weightmap.remove(edge[1])
    edge[1].neighbors.remove(edge[0])
    edge[1].edgesmap.remove(edge[0])
    //edge[1].edges.remove(e)
    //edge[1].edges.remove(second_e)
    edge[1].weightmap.remove(edge[0])
    cur?.let { Edge(edge[0].cell,edge[1].cell, it) }?.let { model.del_edge(it) }
    cur?.let { Edge(edge[1].cell,edge[0].cell, it) }?.let { model.del_edge(it) }

   }
   edge.clear()
  }
  event.consume()
 }

 @FXML
 private fun result() {
  model.BoruvkaMST()
 }
 @FXML
 private fun step_forward() {

 }
}