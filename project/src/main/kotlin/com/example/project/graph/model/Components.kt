package com.example.project.graph.model
import  com.example.project.graph.Edge
class Components(var index:Int) {
    private var children = mutableMapOf<String, Int>()//словарь смежных вершин
    private var allEdges: MutableList<Edge> = mutableListOf() // список рёбер
    private var allcells: MutableList<String> = mutableListOf()//список вершин в компоненте
    fun getAllEdges(): MutableList<Edge> { // получить список рёбер
        return allEdges
    }
    fun getAllComponentCells(): MutableList<String> { // получить список рёбер
        return allcells
    }
    fun getComponentChildren(): MutableMap<String, Int> { // получить межных вершин
        return children
    }
    fun addComponentChildren(cell:String,weight:Int){ // добавить смежные вершины
        children[cell]=weight
    }
    fun addComponentEdges(t:Edge){ // добавить смежные вершины
        allEdges.add(t)
    }
    fun rename(newIndex:Int){
        index = newIndex
    }
    fun addComponentCells(t:String){ // добавить смежные вершины
        allcells.add(t)
    }


}