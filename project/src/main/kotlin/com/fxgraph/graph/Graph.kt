package com.fxgraph.graph

import javafx.scene.Group
import javafx.scene.control.ScrollPane
import javafx.scene.layout.Pane


class Graph() { // модель методов взаимодействия с компонентами графа

    private var model: Model = Model()
    private var canvas: Group = Group() // холст

    private var scrollPane: ZoomableScrollPane = ZoomableScrollPane(canvas) // скроллер
    var mousegestures: MouseGestures = MouseGestures(this) // жесты мыши
    private var cellLayer: CellLayer = CellLayer()
    init{
        canvas.children.add(cellLayer)
        scrollPane.isFitToWidth = true;
        scrollPane.isFitToHeight = true; // изменяемость скроллера по высоте и ширине
    }
    fun  getScrollPane(): ScrollPane { // получить скроллер
        return this.scrollPane;
    }

    fun  getCellLayer(): Pane {
        return this.cellLayer;
    }

    fun getModel(): Model { // получить модель
        return model;
    }

    fun beginUpdate() {
    }
    fun endUpdate() {

        // add components to graph pane
        getCellLayer().children.addAll(model.getAddedEdges());
        getCellLayer().children.addAll(model.getaddedCells());

        // remove components from graph pane
        getCellLayer().children.removeAll(model.getremovedCells());
        getCellLayer().children.removeAll(model.getRemovedEdges());

        // enable dragging of cells
        for (cell in model.getaddedCells()) { //присвоить каждому узлу функции перетаскивания мышью
            mousegestures.makeDraggable(cell);
        }


        // remove reference to graphParent
        getModel().disconnectFromGraphParent(model.getremovedCells()); // удалить связь между удалёнными вершинами и родителем

        // merge added & removed cells with all cells
        getModel().merge();

    }
    fun  getScale(): Double { // получить скалирование
        return this.scrollPane.getscalevalue();
    }
}