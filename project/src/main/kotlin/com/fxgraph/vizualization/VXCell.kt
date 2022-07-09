package com.fxgraph.vizualization

import com.fxgraph.graph.Cell
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Text

class VXCell(var cellId: String,x: Double,y:Double): Circle(x,y,30.0) {
    private var name: Text = Text(cellId)
    var edgesmap: MutableMap<VXCell, VXEdge> = hashMapOf()
    var weightmap: MutableMap<VXCell, Int> = hashMapOf()
    var neighbors: MutableList<VXCell> = mutableListOf()
    var cell: Cell = Cell(cellId)
    var isDragging: Boolean = false

    //  var view: Circle = Circle(x,y,30.0)
    //var name: Text = Text(cellId)
    init {

        stroke = Color.GREEN
        fill = Color.GREEN
        enableDrag()
    }

    fun getcellId(): String {
        return cellId
    }

    fun get_lable(): Text {
        return name
    }

    /**
     * Gets position of the center of this vertex view
     *
     * @return position of the center of this vertex view
     */
    fun getPosition(): Point2D? {
        return Point2D(centerX, centerY)
    }

    fun remove_connection() {
        for (neighbr in neighbors) {
            val cur = this.weightmap[neighbr]
            neighbr.edgesmap.remove(this)
            //  neighbr.edges.remove(cur?.let { VXEdge(this,neighbr, it) })
            // neighbr.edges.remove(cur?.let { VXEdge(neighbr,this, it) })
            neighbr.neighbors.remove(this)
            this.weightmap.remove(neighbr)
        }

    }

    fun setPosition(x: Double, y: Double) {
        if (isDragging) {
            return
        }
        centerX = x
        centerY = y
    }

    fun edge_add_config() {
        disableDrag()
        onMousePressed = EventHandler { event: MouseEvent ->
            this.stroke = Color.FIREBRICK
        }

    }

    /**
     * Enables user drag
     */
    fun enableDrag() {
        class Point(var x: Double, var y: Double)

        val dragDelta = Point(0.0, 0.0)
        onMousePressed = EventHandler { event: MouseEvent ->
            if (event.isPrimaryButtonDown) {
                dragDelta.x = centerX - event.x
                dragDelta.y = centerY - event.y
                scene.cursor = Cursor.MOVE
                isDragging = true
                event.consume()
            }
        }
        onMouseReleased = EventHandler { event: MouseEvent ->
            scene.cursor = Cursor.HAND
            isDragging = false
            event.consume()
        }
        onMouseDragged = EventHandler { event: MouseEvent ->
            if (event.isPrimaryButtonDown) {
                val newX = event.x + dragDelta.x
                val x = boundCenterCoordinate(newX, 0.0, parent.layoutBounds.width)
                centerX = x
                val newY = event.y + dragDelta.y
                val y = boundCenterCoordinate(newY, 0.0, parent.layoutBounds.height)
                centerY = y
                this.relocate(x, y) /////
            }
        }
        onMouseEntered = EventHandler { event: MouseEvent ->
            if (!event.isPrimaryButtonDown) {
                scene.cursor = Cursor.HAND
            }
        }
        onMouseExited = EventHandler { event: MouseEvent ->
            if (!event.isPrimaryButtonDown) {
                scene.cursor = Cursor.DEFAULT
            }
        }
    }

    /**
     * Disables user drag
     */
    fun disableDrag() {
        onMousePressed = EventHandler { event: MouseEvent? -> }
        onMouseReleased = EventHandler { event: MouseEvent? -> }
        onMouseDragged = EventHandler { event: MouseEvent? -> }
        onMouseEntered = EventHandler { event: MouseEvent? -> }
        onMouseExited = EventHandler { event: MouseEvent? -> }
    }

    fun setLabel(label: Text) {
        name = label
        name.xProperty().bind(centerXProperty().add(radiusProperty()))
        //name.xProperty().bind(centerXProperty().subtract(label.layoutBounds.width / 2.0))
        //name.yProperty().bind(centerYProperty().add(radius + label.layoutBounds.height))
        name.yProperty().bind(centerYProperty().add(radiusProperty()))
    }

    /**
     * Sets value to fit in boundaries: (min + radius; max - radius)
     *
     * @param value - current value
     * @param min   - min value
     * @param max   - max value
     * @return bound value
     */
    private fun boundCenterCoordinate(value: Double, min: Double, max: Double): Double {
        val radius = radius
        return if (value < min + radius) {
            min + radius
        } else if (value > max - radius) {
            max - radius
        } else {
            value
        }
    }
}

