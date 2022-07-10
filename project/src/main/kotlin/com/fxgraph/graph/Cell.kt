package com.fxgraph.graph

import javafx.scene.Node
import javafx.scene.layout.Pane

open class Cell(private val cellId: String = ""): Pane() {
    private var children = mutableMapOf<Cell, Int>()
    lateinit var view: Node
    fun addCellChild(cell: Cell, weight : Int) {
        children[cell] = weight // добавить ребро исходящее
        cell.children[this] = weight
    }
    fun getCellChildren(): MutableMap<Cell, Int> {
        return children // вернуть список исходящих вершин
    }

    fun removeCellChild(cell: Cell) {
        cell.children.remove(this)
        children.remove(cell); // удалить исходящее ребро
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
}