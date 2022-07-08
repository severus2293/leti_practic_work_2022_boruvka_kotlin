package com.fxgraph.vizualization

import com.fxgraph.graph.Cell
import javafx.scene.Group
import javafx.scene.shape.Line
import javafx.scene.text.Text

class VXEdge(var source: VXCell,var target: VXCell,val weight: Int): Line() {
    private var name: Text = Text(weight.toString())
    init{

        startXProperty().bind(source.centerXProperty().add( source.radiusProperty()))

        //  line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));
        startYProperty().bind(source.centerYProperty().add( source.radiusProperty()))
        // line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
        endXProperty().bind( target.centerXProperty().add( target.radiusProperty()))
        //line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));
        endYProperty().bind(target.centerYProperty().add( target.radiusProperty())) // задать
    }
    fun getsource(): VXCell { //получить стартовую
        return source;
    }

    fun set_label(label: Text){
        name = label
        name.xProperty().bind(startXProperty().add(endXProperty()).divide(2).subtract(label.layoutBounds.width / 2));
        name.yProperty().bind(startYProperty().add(endYProperty()).divide(2).add(label.layoutBounds.height / 1.5));
    }

    fun get_label(): Text{
        return name
    }

    fun gettarget(): VXCell { // получить конечную
        return target;
    }

    fun getweight(): Int {
        return weight
    }
}