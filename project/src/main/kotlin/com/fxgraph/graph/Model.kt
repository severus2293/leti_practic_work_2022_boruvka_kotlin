package com.fxgraph.graph

import java.util.Random
import com.fxgraph.vizualization.VXCell
import com.fxgraph.vizualization.VXEdge
import javafx.scene.paint.Color
import kotlin.collections.HashMap

class Model {
    var end_flag: Boolean = false
    private var index = 0
    private var dif_colors: MutableList<Color> = mutableListOf()
    private var rand: Random = Random()
    private val graphParent: Cell = Cell("_ROOT_") // корневой узел невидимый
    private var allCells: MutableList<Cell> = mutableListOf() // список всех вершин
    private var allVXCells: MutableList<VXCell> = mutableListOf()
    private var addedCells: MutableList<Cell> = mutableListOf() // добавленные вершины
    private var addedVXCells: MutableList<VXCell> = mutableListOf()
    private var removedCells: MutableList<Cell> = mutableListOf() // удалённые вершины
    private var removedVXCells: MutableList<VXCell> = mutableListOf()
    private var allEdges: MutableList<Edge> = mutableListOf() // список рёбер
    private var allVXEdges: MutableList<VXEdge> = mutableListOf()
    private var addedEdges: MutableList<Edge> = mutableListOf() // список добавленных рёбер
    private var addedVXEdges: MutableList<VXEdge> = mutableListOf()
    private var removedEdges: MutableList<Edge> = mutableListOf() // список удалённых рёбер
    private var removedVXEdges: MutableList<VXEdge> = mutableListOf()
    private var cellMap: HashMap<String,Cell> = hashMapOf()  // <id,cell> доступ к вершине по имени
    private var allComponents: MutableList<Component> = mutableListOf() // список компонент связности
    var n = 0
    lateinit var log: Logger
    fun setAllComponents(temp:MutableList<Component>){allComponents = temp.toMutableList()}
    fun getAllComponents():MutableList<Component>{return allComponents}

    fun clearAddedLists() { //очистить список рёбер и вершин (полное удаление)
        addedCells.clear();
        addedEdges.clear();
    }

    fun  getaddedCells(): MutableList<Cell> { // получить список добавленных вершин
        return addedCells;
    }
    private fun get_color(): Color{

        var r: Double = rand.nextFloat() / 2f + 0.5
        var g: Double = rand.nextFloat() / 2f + 0.5
        var b: Double = rand.nextFloat() / 2f + 0.5
        var col: Color = Color.color(r,g,b)
        var first_expr: Boolean = col == Color.GREEN
        var second_expr: Boolean = col == Color.WHITE
        while (dif_colors.contains(col) == true  or first_expr or second_expr ){
            r = rand.nextFloat() / 2f + 0.5
            g = rand.nextFloat() / 2f + 0.5
            b = rand.nextFloat() / 2f + 0.5
            col = Color.color(r,g,b)
        }
        dif_colors.add(col)

        return  col
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
    fun addCell(cell: VXCell) {  // приватный метод

        addedCells.add(cell.cell) // добавить в список добавленных
        addedVXCells.add(cell)
        cellMap[cell.getcellId()] = cell.cell // присвоить пару ключ-значение
        merge()
    }
    fun del_edge(edge: Edge,cur: VXEdge){
        removedEdges.add(edge)
        removedVXEdges.add(cur)
        for(i in 0..getallCells().size - 1){
            if(allCells[i].getcellId() == edge.getsource().getcellId()){
                allCells[i].removeCellChild(edge.gettarget())
            }
            if(allCells[i].getcellId() == edge.gettarget().getcellId()){
                allCells[i].removeCellChild(edge.getsource())
            }
        }
        edge.getsource()
        merge()
    }

    fun addEdge( sourceId: String, targetId: String, weight : Int, cur: VXEdge) { // добавить ребро

        val sourceCell: Cell = cellMap[sourceId]!!; // получение вершин по имени
        val targetCell: Cell = cellMap[targetId]!!;

        val edge: Edge = Edge( sourceCell, targetCell, weight); // создание ребра

        addedEdges.add(edge); // добавление ребра в список
        addedVXEdges.add(cur)
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
            index += 1
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

    fun findComp(): Component?{
        if(getAllComponents().size==1) {
            return getAllComponents()[0]
        }
        for (i in getAllComponents())
            if (i.getEdges().isEmpty())
                return i
        return null
    }
    fun merge() {

        // cells
        allCells.addAll( addedCells); // добавить список добавленных вершин во все
        allCells.removeAll( removedCells); // удалить все удалённые
        allVXCells.addAll(addedVXCells)
        allVXCells.removeAll(removedVXCells)
        addedCells.clear();
        addedVXCells.clear()
        removedCells.clear(); // очистить буфферы
        removedVXCells.clear()
        // edges
        allEdges.addAll( addedEdges);
        allEdges.removeAll( removedEdges); // сделать также с рёбрами
        allVXEdges.addAll(addedVXEdges)
        allVXEdges.removeAll(removedVXEdges)
        addedEdges.clear();
        addedVXEdges.clear()
        removedEdges.clear(); // очистить буфферы
        removedVXEdges.clear()

    }
    fun remove_cell(cell:VXCell){
        removedCells.add(cell.cell)
        removedVXCells.add(cell)
        merge()
    }

    fun clear_all(){
        allCells.clear()// список всех вершин
        allVXCells.clear()
        addedCells.clear()  // добавленные вершины
        addedVXCells.clear()
        removedCells.clear() // удалённые вершины
        removedVXCells.clear()
        allEdges.clear() // список рёбер
        allVXEdges.clear()
        addedEdges.clear()// список добавленных рёбер
        addedVXEdges.clear()
        removedEdges.clear() // список удалённых рёбер
        removedVXEdges.clear()
        cellMap.clear()  // <id,cell> доступ к вершине по имени
    }
    fun getallVXCells(): MutableList<VXCell>{
        return allVXCells
    }
    fun createAllComponents():Boolean {
        if (this.getAllEdges().isNotEmpty() && getAllComponents().size!=1) {
            if (getAllComponents().isEmpty()) {
                for (i in 0 until allCells.size) {
                    var temp = Component()
                    temp.addCells(getallCells()[i]) // создал компоненты (перекрасить все вершины)
                    ///
                    getallVXCells()[i].fill = get_color() // покраска вершин
                    ///
                    temp.addVXCells(getallVXCells()[i])

                    temp.setAllEdges(getallCells()[i].getCellChildren())
                    getAllComponents().add(temp)
                }
                return true
            }
            return true
        }
        return false
    }
    fun removecomponents(){
        allComponents.clear()
        n = 0
    }
    fun stepAlgoritm(){
        var p = getAllComponents()[n]
        log.log("Вершины, из которых состоит рассматриваемая компонента-связности - [${p.printallCells()}]\n")
        var destination = Cell()
        var num: Int = 0
        log.log("---Процесc нахождения минимального ребра, через которое текущая компонента свазана с другой---")
        for (i in p.getEdges()) {
            log.log("Конечная вершина рассматримаевого ребра, находящаяся в другой компоненте: ${i.key.getcellId()} \nВес этого ребра: ${i.value}")
            if (destination.getcellId().isEmpty()) {
                destination = i.key
                num = i.value
            } else if (i.value < num) {
                num = i.value
                destination = i.key
            }
            log.log("Конечная вершина текущего минимального ребра, которая находится в другой компоненте: ${destination.getcellId()} \nТекущий минимальный вес ребра: $num \n")
        }
        log.log("Найденная конечная вершина минимального ребра: ${destination.getcellId()}\nВес этого ребра: $num\n")
        log.log("---Нахождение компоненты, с которой произойдет слияние с текущей, через найденное минимальное ребро---")
        for (t in getAllComponents()) {
            log.log("Рассматриваемая для слияния компонента состоит из следующих вершин: [${t.printallCells()}]")
            if (t.checkComponentCell(destination.getcellId()) != t.getCells().size) {
                log.log("Последняя рассматриваемая компонента содержит нужную вершину - \"${destination.getcellId()}\"")
                t.removeEdge(p)
                p.removeEdge(t)
                p.mergeComp(t,log) // перекрасить всё множество t в цвет множества p
                findEdge(p, destination, num)?.let { p.addAllEdges(it,allVXEdges[index]) } // перекраска ребра
                index = 0
                getAllComponents().remove(t)
                break
            }
        }
        n++
        if (n > getAllComponents().size - 1) n = 0
        log.log("==========\n")
    }
    fun printresult(comp:Component){
        log.log("---Итог - Ребра из которх состоит минимальное остовное дерево---")
        for (i in comp.getAllEdges())
            log.log("${i.getsource().getcellId()} ${i.getweight()} ${i.gettarget().getcellId()}")
        log.log("=====")
        end_flag = true
    }
    fun BoruvkaMST(index:Int){
        if(index == 1) {
            while (getAllComponents().size > 1 && checkEdgesOut()) {
                stepAlgoritm()
            }
            findComp()?.let { printresult(it) }
        }else if(index == 0 ){
            if (checkEdgesOut()){
                stepAlgoritm()
            }
            if (getAllComponents().size == 1 || !checkEdgesOut())
                findComp()?.let { printresult(it) }
        }
    }
    fun checkEdgesOut(): Boolean {
        for (i in getAllComponents())
            if (i.getEdges().isEmpty())
                return false
        return true
    }
}