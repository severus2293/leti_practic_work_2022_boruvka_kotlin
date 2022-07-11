package com.fxgraph.layout

import java.util.Random


 class RandomLayout() { //  граф
     var rnd: Random = Random() // класс рандома

    fun execute(): MutableList<Double> {
            var coordinate: MutableList<Double> = mutableListOf()
            var x: Double = rnd.nextDouble() * 500
            var y: Double = rnd.nextDouble() * 500
            coordinate.add(x)
            coordinate.add(y)
            return coordinate
    }

    }

