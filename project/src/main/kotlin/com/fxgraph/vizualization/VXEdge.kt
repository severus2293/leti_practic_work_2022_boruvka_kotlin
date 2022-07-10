package com.fxgraph.vizualization

import com.fxgraph.graph.Cell
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.text.Text

class VXEdge(var source: VXCell,var target: VXCell,val weight: Int): Line() {
    private var name: Text = Text(weight.toString())
    init{
        //startXProperty().bind(source.centerXProperty().add( source.layoutX))
       // startYProperty().bind(source.centerYProperty().add( source.layoutY)) // при появлении
        startXProperty().bind(source.centerXProperty().add( source.radiusProperty()))
        startYProperty().bind(source.centerYProperty().add( source.radiusProperty()))
       // startX = source.centerX
       // startY = source.centerY
       // endX = target.centerX
      //  endY = target.centerY
      //  source.line_start_moved(this)
       // target.line_end_moved(this)
      //  endXProperty().bind(target.centerXProperty().add( target.radiusProperty()))
        //startYProperty().bind(source.centerYProperty().add( source.layoutY))
            //endXProperty().bind(target.centerXProperty().add( target.layoutX))
       // endYProperty().bind(target.centerYProperty().add( target.layoutY))

        endXProperty().bind( target.centerXProperty().add( target.radiusProperty()))
        endYProperty().bind(target.centerYProperty().add( target.radiusProperty())) // задать при перемещении
        //endX = target.centerX
       // endY = target.centerY
    }
    fun getsource(): VXCell { //получить стартовую
        return source;
    }

    fun set_label(label: Text){
        name = label
        name.style = "-fx-text-inner-color: red; -fx-font-size: 16px;"
        name.fill = Color.RED
        name.xProperty().bind(startXProperty().add(endXProperty()).divide(2).subtract(label.layoutBounds.width / 2));
        name.yProperty().bind(startYProperty().add(endYProperty()).divide(2).add(label.layoutBounds.height / 1.5));
        name.requestFocus()
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