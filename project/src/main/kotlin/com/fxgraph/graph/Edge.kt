package com.fxgraph.graph

import javafx.scene.Group
import javafx.scene.shape.Line


class Edge(protected var source: Cell,protected var target: Cell, protected var weight: Int): Group() {
    private val line: Line = Line()
    init{
        source.addCellChild(target, weight)
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