package com.fxgraph.graph

import com.fxgraph.cells.CircleCell
import kotlin.collections.HashMap


class Model {
    private val graphParent: Cell = Cell("_ROOT_") // корневой узел невидимый
    private var allCells: MutableList<Cell> = mutableListOf() // список всех вершин
    private var addedCells: MutableList<Cell> = mutableListOf() // добавленные вершины
    private var removedCells: MutableList<Cell> = mutableListOf() // удалённые вершины
    private var allEdges: MutableList<Edge> = mutableListOf() // список рёбер
    private var addedEdges: MutableList<Edge> = mutableListOf() // список добавленных рёбер
    private var removedEdges: MutableList<Edge> = mutableListOf() // список удалённых рёбер
    private var cellMap: HashMap<String,Cell> = hashMapOf()  // <id,cell> доступ к вершине по имени

    fun clearAddedLists() { //очистить список рёбер и вершин (полное удаление)
        addedCells.clear();
        addedEdges.clear();
    }

    fun  getaddedCells(): MutableList<Cell> { // получить список добавленных вершин
        return addedCells;
    }

    fun getremovedCells(): MutableList<Cell> { // получить список удалённых вершин
        return removedCells;
    }

    fun getallCells(): MutableList<Cell> { // получить список всех вершин
        return allCells;
    }

    fun getAddedEdges(): MutableList<Edge> { // получить список добавленных рёбер
        return addedEdges;
    }

    fun getRemovedEdges(): MutableList<Edge> { // получить список удалённых рёбер
        return removedEdges;
    }
    fun getAllEdges(): MutableList<Edge> { // получить список всех рёбер
        return allEdges;
    }
    // вызывать только при нажатии мышки и нажатой клавише add
    fun addCell(id: String) { // добавить вершину
        val circlecell: CircleCell = CircleCell(id)
        addCell(circlecell)

    }
    fun addCell(cell: Cell) {  // приватный метод

        addedCells.add(cell) // добавить в список добавленных

        cellMap[cell.getcellId()] = cell // присвоить пару ключ-значение

    }

    fun addEdge( sourceId: String, targetId: String, weight : Int) { // добавить ребро

        val sourceCell: Cell = cellMap[sourceId]!!; // получение вершин по имени
        val targetCell: Cell = cellMap[targetId]!!;

        val edge: Edge = Edge( sourceCell, targetCell, weight); // создание ребра

        addedEdges.add(edge); // добавление ребра в список

    }


    fun disconnectFromGraphParent(cellList: MutableList<Cell>) { // удалить вершину от корня

        for(cell in cellList) {
            graphParent.removeCellChild( cell);
        }
    }


    fun findEdgeinList(temp1: Cell, temp2: Cell, weight: Int): Edge? {
        for(i in getAllEdges()){
            if ((i.getsource() == temp1 && i.gettarget() == temp2 || i.getsource() == temp2 && i.gettarget() == temp1) && weight == i.getweight())
                return i
        }
        return null
    }

    fun checkEdge(temp1: Cell, temp2: Cell, weight: Int): Boolean{
        for(i in getAllEdges()){
            if ((i.getsource() == temp1 && i.gettarget() == temp2 || i.getsource() == temp2 && i.gettarget() == temp1) && weight == i.getweight())
                return true
        }
        return false
    }

    fun findEdge(comp: Component, temp: Cell, weight: Int): Edge? {
        for (i in comp.getCells())
            if (checkEdge(i,temp, weight))
                return findEdgeinList(i, temp, weight)
        return null
    }


    fun merge() {

        // cells
        allCells.addAll( addedCells); // добавить список добавленных вершин во все
        allCells.removeAll( removedCells); // удалить все удалённые

        addedCells.clear();
        removedCells.clear(); // очистить буфферы

        // edges
        allEdges.addAll( addedEdges);
        allEdges.removeAll( removedEdges); // сделать также с рёбрами

        addedEdges.clear();
        removedEdges.clear(); // очистить буфферы

    }

    fun BoruvkaMST(): MutableList<Edge>? {
        if (this.getAllEdges().isNotEmpty()){
            var T = mutableListOf<Component>()
            for (i in 0 until allCells.size){
                var temp = Component()
                temp.addCells(getallCells()[i])
                temp.setAllEdges(getallCells()[i].getCellChildren())
                T.add(temp)
            }

            var n = 0

            while (T.size > 1){
                var p = T[n]
                print("Cells in current connectivity component ")
                p.printallCells()
                var destination = Cell()
                var num: Int = 0
                for (i in p.getEdges()){
                    println("Current cell: ${i.key.getcellId()} \n edge in question: ${i.value}")
                    println("Current minimal edge: $num \n The current cell to which the edge is directed: ${destination.getcellId()} \n")
                    if (destination.getcellId().isEmpty()){
                        destination = i.key
                        num = i.value
                    } else if (i.value < num){
                        num = i.value
                        destination = i.key
                    }
                }
                println("Cell found: ${destination.getcellId()}\n Edge found: $num \n")
                for (t in T){
                    println("Current connectivity component: ")
                    t.printallCells()
                    if (t.checkComponentCell(destination.getcellId()) != t.getCells().size){
                        println("\n \nCell \"${destination.getcellId()}\" contains in ")
                        t.printallCells()
                        t.removeEdge(p)
                        p.removeEdge(t)
                        p.mergeComp(t)
                        findEdge(p, destination, num)?.let { p.addAllEdges(it)}
                        T.remove(t)
                        break
                    }
                }


                n++
                if (n > T.size - 1) n = 0
                println("==========")
            }

            println("Final edges in MST")
            for (i in T){
                for (j in i.getAllEdges())
                    println("${j.getsource().getcellId()} ${j.getweight()} ${j.gettarget().getcellId()}")
                println("=====")
            }
            return T[0].getAllEdges()}
        return null
    }
}