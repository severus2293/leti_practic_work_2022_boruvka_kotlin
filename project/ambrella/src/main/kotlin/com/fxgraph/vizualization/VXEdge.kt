package com.fxgraph.vizualization

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.text.Text


class VXEdge(var source: VXCell,var target: VXCell,val weight: Int): Line() {
    private var weigh: Int = weight
    private var name: Text = Text(weight.toString())
    init{

        this.style = "-fx-stroke-width: 1px"
        name.setFill(Color.BLUE); // setting color of the text to blue
        name.setStroke(Color.BLUE)
        startXProperty().bind(source.centerXProperty().add( source.radiusProperty()))
        startYProperty().bind(source.centerYProperty().add( source.radiusProperty()))
        endXProperty().bind( target.centerXProperty().add( target.radiusProperty()))
        endYProperty().bind(target.centerYProperty().add( target.radiusProperty())) // задать при перемещении
    }
    fun getsource(): VXCell { //получить стартовую
        return source;
    }


    fun set_label(label: Text){
        name = label
        name.style = "-fx-text-inner-color: red; -fx-font-size: 16px;"
        name.fill = Color.RED
        name.layoutXProperty().bind(startXProperty().add(endXProperty()).divide(2).subtract(label.layoutBounds.width / 2))
        name.layoutYProperty().bind(startYProperty().add(endYProperty()).divide(2).add(label.layoutBounds.height / 1.5));
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


