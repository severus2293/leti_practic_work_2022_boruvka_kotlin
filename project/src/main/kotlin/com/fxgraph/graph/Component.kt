package com.fxgraph.graph

import kotlin.math.min

class Component {
    private var Cells: MutableList<Cell> = mutableListOf() // список всех вершин
    private var allEdges: MutableList<Edge> = mutableListOf() // список рёбер внутри компоненты
    private var edges = mutableMapOf<Cell, Int>() // список рёбер соединяющий разные компоненты

    fun getCells() = Cells
    fun getAllEdges() = allEdges
    fun getEdges() = edges

    fun checkComponentCell(name: String): Int {
        var num = 0
        for (i in Cells){
            if (name == i.getcellId())
                return num
            num++
        }
        return Cells.size
    }

    fun removeEdge(temp: Component){
        for (i in temp.Cells)
            this.edges.remove(i)}

    fun addCells(temp: Cell){Cells.add(temp)}
    fun addAllEdges(temp: Edge){allEdges.add(temp)}
    fun addEdges(temp: Cell, num: Int){edges[temp] = num}

    fun setCells(temp: MutableList<Cell>) {Cells = temp}
    fun setEdges(temp: MutableList<Edge>) {allEdges = temp}
    fun setAllEdges(temp: MutableMap<Cell, Int>) {edges = temp}

    fun printallCells(){
        for (i in Cells){
            print("${i.getcellId()} ")
        }
        println("")
    }

    fun mergeComp(temp: Component){
        print("to the connectivity component ")//-------
        this.printallCells()//  Вывод компонент свзяности
        print("is added")//
        temp.printallCells()//-------
        for (i in temp.Cells){
            this.Cells.add(i)
        }
        for (i in temp.allEdges){
            this.allEdges.add(i)
        }

        var keys_1 = this.edges.keys
        var keys_2 = temp.edges.keys

        println("The first component of connectivity contains the following edges ${this.getEdges().values}")
        println("The second component of connectivity contains the following edges ${temp.getEdges().values}")

        for (i in 0 until keys_2.size){
            if (keys_2.elementAt(i) in keys_1){
                if (this.edges.containsKey(keys_2.elementAt(i)) && temp.edges.containsKey(keys_2.elementAt(i))){
                    var min1 = min(this.edges[keys_2.elementAt(i)]!!, temp.edges[keys_2.elementAt(i)]!!)
                    this.edges.remove(keys_2.elementAt(i))
                    this.edges[keys_2.elementAt(i)] = min1}
            } else {
                temp.edges[keys_2.elementAt(i)]?.let { this.edges.put(keys_2.elementAt(i), it) }
            }
        }

        println("List of edges after joining the connectivity components ${this.getEdges().values}")

    }



}