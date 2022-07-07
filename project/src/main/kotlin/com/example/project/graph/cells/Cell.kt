package com.example.project.graph.cells


import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.input.MouseEvent
import javafx.scene.shape.Circle


open class Cell(private var cellId: String, x: Double, y: Double): Circle(x,y,30.0){
    private var children = mutableMapOf<String, Int>()
    private var isDragging = false
    open fun getPosition(): Point2D? {
        return Point2D(centerX, centerY)
    }

    open fun setPosition(x: Double, y: Double) {
        if (isDragging) {
            return
        }
        centerX = x
        centerY = y
    }
    fun addCellChild(cell: Cell, weight : Int) {
        children[cell.getcellId()] = weight // добавить ребро исходящее
        cell.children[this.getcellId()] = weight
    }
    fun getcellId(): String {
        return cellId; // вернуть имя
    }
    fun getCellChildren(): MutableMap<String, Int> {
        return children // вернуть список исходящих вершин
    }
    /**
     * Enables user drag
     */
    open fun enableDrag() {
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
    open fun disableDrag() {
        onMousePressed = EventHandler { event: MouseEvent? -> }
        onMouseReleased = EventHandler { event: MouseEvent? -> }
        onMouseDragged = EventHandler { event: MouseEvent? -> }
        onMouseEntered = EventHandler { event: MouseEvent? -> }
        onMouseExited = EventHandler { event: MouseEvent? -> }
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
/*open class Cell(private val cellId: String): Pane() {
    private var children = mutableMapOf<String, Int>()
    lateinit var view: Node

    fun addCellChild(cell: Cell, weight : Int) {
        children[cell.getcellId()] = weight // добавить ребро исходящее
        cell.children[this.getcellId()] = weight
    }
    fun getCellChildren(): MutableMap<String, Int> {
        return children // вернуть список исходящих вершин
    }

    fun removeCellChild(cell: Cell) {
        cell.children.remove(this.getcellId())
        children.remove(cell.getcellId()); // удалить исходящее ребро
    }


    fun setview(view: Node) {

        this.view = view //присвоить отображение
        getChildren().add(view) // разместить на холсте

    }

    fun getview(): Node {
        return this.view; // вернуть отображение
    }

    fun getcellId(): String {
        return cellId; // вернуть имя
    }
}*/