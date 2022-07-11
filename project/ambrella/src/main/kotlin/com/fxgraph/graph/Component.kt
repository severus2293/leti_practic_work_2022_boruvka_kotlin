package com.fxgraph.graph


import com.fxgraph.vizualization.VXCell
import com.fxgraph.vizualization.VXEdge
import kotlin.math.min

class Component {
    private var Cells: MutableList<Cell> = mutableListOf() // список всех вершин
    private var VXCells: MutableList<VXCell> = mutableListOf()
    private var allEdges: MutableList<Edge> = mutableListOf() // список рёбер внутри компоненты
    private var allVXEdges: MutableList<VXEdge> = mutableListOf()
    private var edges = mutableMapOf<Cell, Int>() // список рёбер соединяющий разные компоненты

    fun getCells() = Cells
    fun getAllEdges() = allEdges
    fun getEdges() = edges

    fun checkComponentCell(name: String): Int {
        var num = 0
        for (i in Cells) {
            if (name == i.getcellId())
                return num
            num++
        }
        return Cells.size
    }

    fun printneigbors():String{
        var str1 = ""
        var str2 = ""
        for (i in edges.keys) {
            str1 += i.getcellId() + " "
        }
        for (i in edges.values) {
            str2 += "$i "
        }
        return "\n[${str1.trim()}]\n[${str2.trim()}]"
    }


    fun removeEdge(temp: Component){
        for (i in temp.Cells)
            this.edges.remove(i)}

    fun addCells(temp: Cell){Cells.add(temp)}
    fun addVXCells(temp: VXCell){VXCells.add(temp)}
    fun addAllEdges(temp: Edge,cur: VXEdge){
        allEdges.add(temp)
        cur.stroke = VXCells[0].fill
        cur.style = "-fx-stroke-width: 4px"
        allVXEdges.add(cur)
    }
    fun addEdges(temp: Cell, num: Int){edges[temp] = num}

    fun setCells(temp: MutableList<Cell>) {Cells = temp}
    fun setEdges(temp: MutableList<Edge>) {allEdges = temp}
    fun setAllEdges(temp: MutableMap<Cell, Int>) {edges.putAll(temp)}

    fun printallCells(): String {
        var res: String = ""
        for (i in Cells){
            res += i.getcellId()+" "
        }
        return res.trim()
    }

    fun mergeComp(temp: Component,log:Logger){
        log.log("\n---Начало слияния двух компонент-связности---")//-------
        log.log("Вершины первой компоненты, в которую войдут вершины и ребра второй - [${this.printallCells()}]")//  Вывод компонент свзяности
        log.log("Вершины второй компоненты, которая будет поглощена первой - [${temp.printallCells()}]")//------
        for (i in temp.Cells){
            this.Cells.add(i)
        }
        for(i in temp.VXCells){
            i.fill = this.VXCells[0].fill
            this.VXCells.add(i)
        }
        for (i in temp.allEdges){
            this.allEdges.add(i)
        }
        for(i in temp.allVXEdges){
            i.stroke = this.VXCells[0].fill
            this.allVXEdges.add(i)
        }

        var keys_1 = this.edges.keys
        var keys_2 = temp.edges.keys

        log.log("Первая компонента соединена с другими через следующие ребра, конечные вершины и их вес:${this.printneigbors()}")
        log.log("Вторая компонента соединена с другими через следующие ребра, конечные вершины и их вес:${temp.printneigbors()}")

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

        log.log("Список ребер получившейся компоненты, с помощью которых она соединена с другими, их конечные вершины и вес:${this.printneigbors()}")

    }



}
/*import com.fxgraph.vizualization.VXCell
import com.fxgraph.vizualization.VXEdge
import kotlin.math.min

class Component {
    private var Cells: MutableList<Cell> = mutableListOf() // список всех вершин
    private var VXCells: MutableList<VXCell> = mutableListOf()
    private var allEdges: MutableList<Edge> = mutableListOf() // список рёбер внутри компоненты
    private var allVXEdges: MutableList<VXEdge> = mutableListOf()
    private var edges = mutableMapOf<Cell, Int>() // список рёбер соединяющий разные компоненты

    fun getCells() = Cells
    fun getAllEdges() = allEdges
    fun getEdges() = edges

    fun checkComponentCell(name: String): Int {
        var num = 0
        for (i in Cells) {
            if (name == i.getcellId())
                return num
            num++
        }
        return Cells.size
    }

    fun printneigbors() {
        var str1 = ""
        var str2 = ""
        for (i in edges.keys) {
            str1 += i.getcellId() + " "
        }
        for (i in edges.values) {
            str2 += "$i "
        }
        println("[$str1]\n[$str2]")

    }


    fun removeEdge(temp: Component){
        for (i in temp.Cells)
            this.edges.remove(i)}

    fun addCells(temp: Cell){Cells.add(temp)}
    fun addVXCells(temp: VXCell){VXCells.add(temp)}
    fun addAllEdges(temp: Edge,cur: VXEdge){
        allEdges.add(temp)
        cur.stroke = VXCells[0].fill
        allVXEdges.add(cur)
    }
    fun addEdges(temp: Cell, num: Int){edges[temp] = num}

    fun setCells(temp: MutableList<Cell>) {Cells = temp}
    fun setEdges(temp: MutableList<Edge>) {allEdges = temp}
    fun setAllEdges(temp: MutableMap<Cell, Int>) {edges.putAll(temp)}

    fun printallCells(): String {
        var res: String = ""
        for (i in Cells){
            res += i.getcellId()+" "
        }
        return res.trim()
    }

    fun mergeComp(temp: Component){
        println("---Beginning of merging of components---")//-------
        println("First component [${this.printallCells()}]")//  Вывод компонент свзяности
        println("Second component [${temp.printallCells()}]")//------
        for (i in temp.Cells){
            this.Cells.add(i)
        }
        for(i in temp.VXCells){
            i.fill = this.VXCells[0].fill
            this.VXCells.add(i)
        }
        for (i in temp.allEdges){
            this.allEdges.add(i)
        }
        for(i in temp.allVXEdges){
            i.stroke = this.VXCells[0].fill
            this.allVXEdges.add(i)
        }

        var keys_1 = this.edges.keys
        var keys_2 = temp.edges.keys

        println("The first component of connectivity contains the following edges ${this.printneigbors()}")
        println("The second component of connectivity contains the following edges ${temp.printneigbors()}")

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

        println("List of edges after joining the connectivity components ${this.printneigbors()}")

    }



}
 */