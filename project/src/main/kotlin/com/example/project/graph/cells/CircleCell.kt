package com.example.project.graph.cells

import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType


/*class CircleCell(id: String): Cell(id) {
    constructor(id:String,offsetX: Double,offsetY: Double): this(id){
        var name: Text = Text(id)
        var stackpane:StackPane = StackPane()
        view = Circle(30.0)

        (view as Circle).stroke = Color.GREEN; //задать границы
        (view as Circle).fill = Color.GREEN; // задать заливку
        view.relocate(offsetX,offsetY)
        name.boundsType = TextBoundsType.VISUAL;
        stackpane.children.addAll(view,name)
        stackpane.relocate(offsetX,offsetY)
        view.localToParent(view.boundsInParent.width,view.boundsInParent.width)
        println(view.boundsInParent.maxX)
        println(view.boundsInLocal.maxX)
        println(view.boundsInParent.maxY)
        println(view.boundsInLocal.maxY)
        //setview(view); //разместить изображение на холсте

        stackpane.isCenterShape = true
        setview(stackpane)
    }
}

/*class CircleCell(id: String,offsetX: Double,offsetY: Double): Cell(id) {
    init{
        view = Circle(30.0)
        (view as Circle).stroke = Color.GREEN; //задать границы
        (view as Circle).fill = Color.GREEN; // задать заливку
        view.relocate(offsetX,offsetY)
        setview(view); //разместить изображение на холсте
    }
}*/