package com.fxgraph.cells

import com.fxgraph.graph.Cell
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType


class CircleCell(id: String): Cell(id) {
    init{
        var name: Text = Text(id)

    //    view.relocate(offsetX,offsetY)
        name.boundsType = TextBoundsType.VISUAL;
        view = Circle(30.0)
        val cur:StackPane = StackPane()
        cur.children.addAll(view,name)
        (view as Circle).stroke = Color.GREEN; //задать границы
        (view as Circle).fill = Color.GREEN; // задать заливку

        setview(cur); //разместить изображение на холсте
    }
}