package com.example.refactor_vizualize_graph_ktln

import com.fxgraph.graph.Graph
import com.fxgraph.graph.Model
import com.fxgraph.layout.base.Layout
import com.fxgraph.layout.random.RandomLayout
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

 class Main : Application() {

     //    var graph: Graph = Graph() //  граф
     override fun start(stage: Stage) {
         /* var  root: BorderPane = BorderPane(); // создать сцену
        root.center = graph.getScrollPane(); // разместить по центру холст вероятно

        var  scene: Scene = Scene(root, 1024.0, 768.0); // начальная размерность сцены
        // scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("com.example.test_graph.application.css")).toExternalForm());

        stage.scene = scene;
        stage.show(); // отобразить сцену

        addGraphComponents(); // создание графа

        var layout: Layout = RandomLayout(graph);
        layout.execute();*/
         val fxmlLoader = FXMLLoader(Main::class.java.getResource("hello-view.fxml"))
         val scene = Scene(fxmlLoader.load(), 900.0, 600.0)
         stage.title = "Hello!"
         stage.scene = scene
         stage.show()
     }

   /* private fun addGraphComponents() {
        var model: Model = graph.getModel() // получить инструмент пользования
        graph.beginUpdate()

        model.addCell("Cell A");
        model.addCell("Cell B");
        model.addCell("Cell C");
        model.addCell("Cell D");
        model.addCell("Cell E");
        model.addCell("Cell F");
        model.addCell("Cell G");

        model.addEdge("Cell A", "Cell B", 1);
        model.addEdge("Cell A", "Cell G", 2);
        model.addEdge("Cell A", "Cell F", 6);
        model.addEdge("Cell B", "Cell C",10);
        model.addEdge("Cell B", "Cell F",4);
        model.addEdge("Cell B", "Cell G",5);
        model.addEdge("Cell C", "Cell D",1);
        model.addEdge("Cell D", "Cell E",4);
        model.addEdge("Cell C", "Cell E",3);
        model.addEdge("Cell E", "Cell F",10);
        model.addEdge("Cell F", "Cell G", 3);

        graph.endUpdate(); // добавление рёбер, вершин, очистка буфферов

        model.BoruvkaMST()



    }*/
     fun main(){
         Application.launch(Main::class.java)
     }

}

//fun main() {
 //   Application.launch(Main::class.java)
//}