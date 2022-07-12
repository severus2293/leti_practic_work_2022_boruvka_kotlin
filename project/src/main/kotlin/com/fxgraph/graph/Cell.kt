package com.fxgraph.graph

import javafx.scene.layout.Pane

open class Cell(private val cellId: String = ""): Pane() {
    private var children = mutableMapOf<Cell, Int>()
    fun addCellChild(cell: Cell, weight : Int) {
        children[cell] = weight // добавить ребро исходящее
        cell.children[this] = weight
    }
    fun getCellChildren(): MutableMap<Cell, Int> {
        return children // вернуть список исходящих вершин
    }

    fun removeCellChild(cell: Cell) {
        cell.children.remove(this)
        children.remove(cell) // удалить исходящее ребро
    }




    fun getcellId(): String {
        return cellId // вернуть имя
    }
}