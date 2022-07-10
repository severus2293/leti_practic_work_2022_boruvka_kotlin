package com.fxgraph.graph

import com.fxgraph.vizualization.VXCell
import javafx.scene.Group
import javafx.scene.shape.Line


class Edge(protected var source: Cell,protected var target: Cell, protected var weight: Int): Group() {
    private val line: Line = Line()
    init{
        source.addCellChild(target, weight)
            // line.startXProperty().bind( source.layoutXProperty().add(source.boundsInParent.centerX))
        //  line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));
      //  line.startYProperty().bind( source.layoutYProperty().add(source.boundsInParent.centerY))
        // line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
      //  line.endXProperty().bind( target.layoutXProperty().add( target.boundsInParent.centerX))
        //line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));
       // line.endYProperty().bind( target.layoutYProperty().add( target.boundsInParent.centerY)) // задать
       // children.add(line);
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

}