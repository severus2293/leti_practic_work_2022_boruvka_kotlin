package com.fxgraph.layout.random

import java.util.Random
import com.fxgraph.graph.Cell
import com.fxgraph.graph.Graph
import com.fxgraph.layout.base.Layout


 class RandomLayout(var graph: Graph):  Layout() { //  граф
     var rnd: Random = Random() // класс рандома

    override fun execute() {
        val cells: MutableList<Cell> = graph.getModel().getallCells() //получить список всех вершин от модели

        for (cell in cells) {
            var x: Double = rnd.nextDouble() * 500
            var y: Double = rnd.nextDouble() * 500
            cell.relocate(x, y); //разместить случайным образом вершины

    }

    }

}