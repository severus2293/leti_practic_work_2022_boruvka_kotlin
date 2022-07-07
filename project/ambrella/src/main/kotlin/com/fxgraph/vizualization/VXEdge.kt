package com.fxgraph.vizualization

import com.fxgraph.graph.Cell
import javafx.scene.Group
import javafx.scene.shape.Line

class VXEdge(var source: VXCell,var target: VXCell,val weight: Int): Line() {
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

    fun gettarget(): VXCell { // получить конечную
        return target;
    }

    fun getweight(): Int {
        return weight
    }
}