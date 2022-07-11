package com.fxgraph.layout

class SquareLayout {  // 6x6
    var step_width: Double = 0.0
    var step_height: Double = 0.0
    val separ: Int = 5
    private var width_val = 0.0
    private var height_val = 0.0
    var x = 0.0
    var y = 0.0
    fun set_param(width: Double,height: Double){
        width_val = width
        height_val = height
        step_width = width / separ - 15
        step_height = height / separ + 15
        //x = step_height
        y = step_height
    }
    fun execute(): MutableList<Double> {
        var coordinate: MutableList<Double> = mutableListOf()
        if(x > width_val - 20 ){
            x = step_width
            y += step_height
        }
        else{
            x += step_width
        }
        coordinate.add(x)
        coordinate.add(y)
        return coordinate
    }
    fun reset(){
        x = 0.0
        y = 0.0
    }
}