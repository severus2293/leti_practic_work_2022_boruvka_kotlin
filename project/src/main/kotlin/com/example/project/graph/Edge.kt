package com.example.project.graph
import javafx.scene.Group
import javafx.scene.shape.Line
import com.example.project.graph.cells.Cell
//import com.example.project.graph.cells.CircleCell
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.shape.Circle
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType

class Edge(protected var source: Cell,protected var target: Cell, protected var weight: Int): Group() {
    private var line: Line = Line()
    init{
        source.addCellChild(target, weight)
        target.addCellChild(source,weight)
        var weight_str: Text = Text(weight.toString())
        var stackpane: StackPane = StackPane()
        weight_str.boundsType = TextBoundsType.VISUAL;
        //stackpane.children.addAll(line,weight_str)
      //  line.startXProperty().bind((source.getview() as StackPane).layoutXProperty())
        line.startXProperty().bind(source.layoutXProperty().add(source.boundsInParent.centerX))
        line.startYProperty().bind(source.layoutYProperty().add(source.boundsInParent.centerY ));

       // line.startYProperty().bind((source.getview() as StackPane).layoutYProperty())
        line.endXProperty().bind(target.layoutXProperty().add(target.boundsInParent.centerX))
        line.endYProperty().bind(target.layoutYProperty().add(target.boundsInParent.centerY));
        children.addAll(line,weight_str)
        //line.endXProperty().bind((target.getview() as StackPane).layoutXProperty() )
        //line.endYProperty().bind( (target.getview() as StackPane).layoutYProperty()) // задать
        //children.add(line);
       // children.add(stackpane)
    }
    fun getsource(): Cell { //получить стартовую
        return source;
    }

    fun gettarget(): Cell { // получить конечную
        return target;
    }

    fun getweight(): Int {
        return weight
    }
    fun getline(): Line{
        return this.line
    }
}