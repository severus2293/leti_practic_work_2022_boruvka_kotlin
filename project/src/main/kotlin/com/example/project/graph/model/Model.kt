package com.example.project.graph.model

import com.example.project.graph.cells.Cell
//import com.example.project.graph.cells.CircleCell
import com.example.project.graph.Edge
import com.example.project.graph.model.Components
import javafx.scene.shape.Line
import kotlin.collections.HashMap


class Model {
  //  private val graphParent: Cell = Cell("_ROOT_",) // корневой узел невидимый
    private var allCells: MutableList<Cell> = mutableListOf() // список всех вершин
    private var addedCells: MutableList<Cell> = mutableListOf() // добавленные вершины
    private var removedCells: MutableList<Cell> = mutableListOf() // удалённые вершины
    private var allEdges: MutableList<Edge> = mutableListOf() // список рёбер
    private var addedEdges: MutableList<Edge> = mutableListOf() // список добавленных рёбер
    private var removedEdges: MutableList<Edge> = mutableListOf() // список удалённых рёбер
    private var cellMap: HashMap<String, Cell> = hashMapOf()  // <id,cell> доступ к вершине по имени
    private var allComponents: MutableList<Components> = mutableListOf() // список компонент-связонностей

    fun clearAddedLists() { //очистить список рёбер и вершин (полное удаление)
        addedCells.clear();
        addedEdges.clear();
    }

    fun getaddedCells(): MutableList<Cell> { // получить список добавленных вершин
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
        val circlecell: Cell = Cell(id)
        addCell(circlecell)

    }

    fun addCell(cell: Cell) {  // приватный метод

        addedCells.add(cell) // добавить в список добавленных

        cellMap[cell.getcellId()] = cell // присвоить пару ключ-значение

    }

    fun addEdge(sourceId: String, targetId: String, weight: Int): Line { // добавить ребро

        val sourceCell: Cell = cellMap[sourceId]!!; // получение вершин по имени
        val targetCell: Cell = cellMap[targetId]!!;

        val edge: Edge = Edge(sourceCell, targetCell, weight); // создание ребра

        addedEdges.add(edge); // добавление ребра в список
       return edge.getline()
    }

    fun getAllComponents(): MutableList<Components> { // получить список добавленных компонентов
        return allComponents;
    }

    fun addComponent(index: Int) {
        var comp: Components = Components(index)
        allComponents.add(comp)
    }

    fun disconnectFromGraphParent(cellList: MutableList<Cell>) { // удалить вершину от корня

        for (cell in cellList) {
            graphParent.removeCellChild(cell);
        }
    }

    fun merge() {

        // cells
        allCells.addAll(addedCells); // добавить список добавленных вершин во все
        allCells.removeAll(removedCells); // удалить все удалённые

        addedCells.clear();
        removedCells.clear(); // очистить буфферы

        // edges
        allEdges.addAll(addedEdges);
        allEdges.removeAll(removedEdges); // сделать также с рёбрами

        addedEdges.clear();
        removedEdges.clear(); // очистить буфферы

    }
    fun createNewComponent(k:Int, c1:Cell, c2:Cell, name:String):Int{
        addComponent(k)
        for(i in c1.getCellChildren()){
            if(i.key !=name){
                getAllComponents()[k].addComponentChildren(i.key,i.value)
            }
        }
        for(i in c2.getCellChildren()){
            if(i.key !=c1.getcellId()){
                getAllComponents()[k].addComponentChildren(i.key,i.value)
            }
        }
        getAllComponents()[k].addComponentCells(name)
        getAllComponents()[k].addComponentCells(c1.getcellId())
        return k+1
    }

    fun addCellsToComponent(cell:Cell, comp:Components){
        for(i in cell.getCellChildren()){
            if(i.key !in comp.getComponentChildren()){
                getAllComponents()[comp.index].addComponentChildren(i.key,i.value)
            }
        }
    }

    fun BoruvkaMST() {
        var res = mutableMapOf<String, Int>()
        var k = 0
        for (i in getallCells()) {
            var name: String = ""
            var valu: Int = 0
            for (j in i.getCellChildren()) {
                if (name.isEmpty()) {
                    name = j.key
                    valu = j.value
                } else if (j.value < valu) {
                    valu = j.value
                    name = j.key
                }
            }
            for (j in getallCells()) {
                if (j.getcellId() == name) {
                    if (!res.containsKey(name + " " + i.getcellId())) {
                        res[i.getcellId() + " " + name] = valu
                        if (k == 0) {
                            k=createNewComponent(k,i,j,name)
                        }else{
                            for(h in allComponents){
                                for(g in h.getComponentChildren()){
                                    if(g.key == name){
                                        h.addComponentCells(name)
                                        for(r in j.getCellChildren()){
                                            if(r.key !in h.getComponentChildren()){
                                                getAllComponents()[h.index].addComponentChildren(r.key,r.value)
                                            }
                                        }
                                        //addCellsToComponent(j,h)
                                    }else if(g.key == i.getcellId()){
                                        addCellsToComponent(j,h)
                                    }
                                }
                            }
                        }
                    }
                    break
                }
            }
        }
        println(getAllComponents()[0].getComponentChildren())
        println(res)
    }
}