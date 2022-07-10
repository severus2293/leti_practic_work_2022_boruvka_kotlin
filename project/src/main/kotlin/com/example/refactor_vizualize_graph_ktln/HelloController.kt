package com.example.refactor_vizualize_graph_ktln

import com.fxgraph.graph.Edge
import com.fxgraph.graph.Logger
import com.fxgraph.graph.Model
import com.fxgraph.layout.random.RandomLayout
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
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.Stage
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.Collections.max

class HelloController {
 private var cell_number: Int = 1
 var weight: Int = 0
 private var cellmap: MutableMap<Int,VXCell> = hashMapOf()
 private var layout: RandomLayout = RandomLayout()
 private var edge: MutableList<VXCell> = mutableListOf()
 private var model: Model = Model()
 private var vxcells: MutableList<VXCell> = mutableListOf()
 private var vxedges: MutableList<VXEdge> = mutableListOf()
 @FXML
 private lateinit var node: AnchorPane
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
 private lateinit var my_console: TextArea
 @FXML
 private fun save_file(event: ActionEvent){
  val ex1: FileChooser.ExtensionFilter = FileChooser.ExtensionFilter("Text Files", "*.txt")
  event.consume()
  val filechooser: FileChooser = FileChooser()
  filechooser.extensionFilters.add(ex1)
  val selectedfile: File? = filechooser.showSaveDialog(node.scene.window)
  if(selectedfile != null){
   SaveGraph(selectedfile.path)
  }

 }

 private fun SaveGraph(path: String){ ///доработать
  var file: BufferedWriter = File(path).bufferedWriter()
  file.write("${model.getAllEdges().size+getSingleCell()}\n")
  for(i in  model.getAllEdges()){
   file.write("${i.getsource().getcellId()} ${i.gettarget().getcellId()} ${i.getweight()}\n")
  }
  file.close()
 }
 private fun getSingleCell():Int{
  var res = 0
  for(i in model.getallCells()){
   if(i.getCellChildren().isEmpty()){res++}
  }
  return res
 }

 @FXML
 fun initialize() {
  model.log = Logger(my_console)
 }
 @FXML
 private fun open_file(event: ActionEvent){
  val ex1: FileChooser.ExtensionFilter = FileChooser.ExtensionFilter("Text Files", "*.txt")

  event.consume()
  val filechooser: FileChooser = FileChooser()
  filechooser.extensionFilters.add(ex1)
  val selectedfile: File? = filechooser.showOpenDialog(node.scene.window)
  if(selectedfile != null){
   println(selectedfile.path)
   take_graph(selectedfile.path)
  }
 }
private fun take_graph(path: String){
 try {
  val inputStream: InputStream = File(path).inputStream()
  val lineList = mutableListOf<String>()
  inputStream.bufferedReader().forEachLine { lineList.add(it) }
  val num_edge: Int = lineList[0].toInt()
  if(lineList.size != num_edge + 1){
    throw(java.lang.NumberFormatException())
  }
  var source: Int = 0
  var target: Int = 0
  var weight: Int = 0
  var coordinate: MutableList<Double> = mutableListOf()
  var edge: VXEdge
  var sourcecell: VXCell
  var targetcell: VXCell
  for(i in 1..(num_edge)){
   var strs = lineList[i].split(" ").toTypedArray()
   source = strs[0].toInt()
   target = strs[1].toInt()
   weight = strs[2].toInt()
   if(source != target){    //разные вершины

       if(!cellmap.containsKey(source)) {
        coordinate = layout.execute()
        sourcecell = VXCell(source.toString(), coordinate[0], coordinate[1])
        cellmap[source] = sourcecell
        sourcecell.setLabel(Text(source.toString()))
        vxcells.add(sourcecell)                        //добавления источника
        holst.children.add(sourcecell)
        holst.children.add(sourcecell.get_lable())
        model.addCell(sourcecell)
        model.merge()
       }
       else{
          sourcecell = cellmap[source]!!
       }
       if(!cellmap.containsKey(target)){
        coordinate = layout.execute()
        targetcell = VXCell(target.toString(),coordinate[0],coordinate[1])
        cellmap[target] = targetcell
        targetcell.setLabel(Text(target.toString()))
        vxcells.add(targetcell)                          // добавления конца
        holst.children.add(targetcell)
        holst.children.add(targetcell.get_lable())
        model.addCell(targetcell)
        model.merge()
       }
       else{
        targetcell = cellmap[target]!!
       }
       edge = VXEdge(sourcecell,targetcell,weight) // добавление ребра
       edge.set_label(Text(weight.toString()))
       vxedges.add(edge)
       sourcecell.edgesmap[targetcell] = edge
       sourcecell.neighbors.add(targetcell)
       sourcecell.weightmap[targetcell] = weight
       targetcell.edgesmap[sourcecell] = edge
       targetcell.neighbors.add(sourcecell)
       targetcell.weightmap[sourcecell] = weight
       holst.children.add(edge.get_label())
       holst.children.add(edge)
       model.addEdge(edge.getsource().getcellId(),edge.gettarget().getcellId(),weight,edge)
       model.merge()
   }
   else{
    if(!cellmap.containsKey(source)) {
     coordinate = layout.execute()
     sourcecell = VXCell(source.toString(), coordinate[0], coordinate[1])
     cellmap[source] = sourcecell
     sourcecell.setLabel(Text(source.toString()))
     vxcells.add(sourcecell)                        //добавления источника
     holst.children.add(sourcecell)
     holst.children.add(sourcecell.get_lable())
     model.addCell(sourcecell)
     model.merge()
    }
   }
  }
  cell_number = max(cellmap.keys) + 1
  inputStream.close()
 }
 catch(e: IOException){
  val stage = Stage()
  val loader = FXMLLoader(javaClass.getResource("readfile_exception.fxml"))
  val root = loader.load<Parent>()
  stage.title = "Exception"
  stage.scene = Scene(root, 400.0, 200.0)
  stage.initModality(Modality.APPLICATION_MODAL)
  stage.showAndWait()
 }
 catch(e: java.lang.Exception){
  val stage = Stage()
  val loader = FXMLLoader(javaClass.getResource("wrong_data.fxml"))
  val root = loader.load<Parent>()
  stage.title = "Exception"
  stage.scene = Scene(root, 400.0, 200.0)
  stage.initModality(Modality.APPLICATION_MODAL)
  stage.showAndWait()
  clear_holst()
 }

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
  cellmap.clear()
  cell_number = 1
  off_parametrs()
 }

 private fun off_parametrs(){
  for (cell in vxcells){
   cell.disableDrag()
  }
   del_edge_button.style = "-fx-background-color: #ffa000"
 add_vert_button.style = "-fx-background-color: #ffa000"
 add_edge_button.style = "-fx-background-color: #ffa000"
 del_vert_button.style = "-fx-background-color: #ffa000"
  edge.clear()
  scroll_pane.onMouseClicked = null
 }
 @FXML
 private fun add_vert(event: ActionEvent) {
  event.consume()
  if (add_vert_button.style == "-fx-background-color: green") { // если включена
   off_parametrs() // сброс всего
   add_vert_button.style = "-fx-background-color: #ffa000"

   for(cell in vxcells){
    cell.enableDrag()
   }
  } else {                                                // включаем

   off_parametrs()                                                    //сброс всех настроек
   add_vert_button.style = "-fx-background-color: green"
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
   model.addCell(cell) // добавить в структуру
   model.merge()
  }
 ////////////////////////////////////////////////////////////////////////////////////
 @FXML
 private fun del_vert(event: ActionEvent) {
  event.consume()
  if(del_vert_button.style == "-fx-background-color: green") { // если включена
   off_parametrs() // сброс всего
   del_vert_button.style = "-fx-background-color: #ffa000"
   for(cell in vxcells){
    cell.enableDrag()
   }
  }
  else{
   off_parametrs()
   del_vert_button.style = "-fx-background-color: green"
   for(cell in vxcells){
    cell.onMousePressed = onMousePressed_del_vert
   }
  }
 }


 var onMousePressed_del_vert = EventHandler<MouseEvent> {event ->
  val node = event.source as VXCell
  model.remove_cell(node)
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
   val e: VXEdge? = cell.weightmap[neighbor]?.let { VXEdge(cell,neighbor, it) }
   val second_e: VXEdge? = cell.weightmap[neighbor]?.let { VXEdge(neighbor,cell, it) }
   cur?.let { Edge(cell.cell,neighbor.cell, it) }?.let {
    if (e != null) {
     model.del_edge(it,e)
    }
   }
   cur?.let { Edge(neighbor.cell,cell.cell, it) }?.let {
    if (second_e != null) {
     model.del_edge(it,second_e)
    }
   }
  }
 }
 /////////////////////////////////////////////////////////////////////////////
 @FXML
 private fun add_edge(event: ActionEvent) {
  //event.consume()
  if (add_edge_button.style == "-fx-background-color: green") {
   off_parametrs()
   add_edge_button.style = "-fx-background-color: #ffa000"
   for(cell in vxcells){
    cell.enableDrag()
   }
  } else {
   off_parametrs()
   add_edge_button.style = "-fx-background-color: green"
   for(cell in vxcells){
    cell.disableDrag()
    cell.onMousePressed = onMousePressed_add_edge
    cell.onMouseDragged = onMousePressed_add_edge
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
    edge[0].stroke = Color.GREEN
    edge[1].stroke = Color.GREEN
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
    model.addEdge(e.getsource().getcellId(),e.gettarget().getcellId(),weight,e)
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
   off_parametrs()
   del_edge_button.style = "-fx-background-color: #ffa000"
   for (cell in vxcells) {
    cell.enableDrag()
   }
  } else {
   off_parametrs()
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
    cur?.let { Edge(edge[0].cell,edge[1].cell, it) }?.let {
     if (e != null) {
      model.del_edge(it,e)
     }
    }
    cur?.let { Edge(edge[1].cell,edge[0].cell, it) }?.let {
     if (second_e != null) {
      model.del_edge(it,second_e)
     }
    }

   }
   edge.clear()
  }
  event.consume()
 }

 @FXML
 private fun result() {
  if(model.createAllComponents()){
   model.BoruvkaMST(1)
  }

 }
 @FXML
 private fun step_forward() {
  if(model.createAllComponents()){
   model.BoruvkaMST(0)
  }
 }
}