package com.fxgraph.graph

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent


class MouseGestures     // присвоение графа
    (var graph: Graph) {
    val dragContext = DragContext() //координаты?
    fun makeDraggable(node: Node) {
        node.onMousePressed = onMousePressedEventHandler // определить функцию нажатия
        node.onMouseDragged = onMouseDraggedEventHandler // определить функцию перетаскивания
        node.onMouseReleased = onMouseReleasedEventHandler // определить функцию отпускания мыши
    }

    var onMousePressedEventHandler =
        EventHandler<MouseEvent> { event ->
            val node = event.source as Node
            val scale = graph.getScale() // получить скалирование?
            dragContext.x = node.boundsInParent.minX * scale - event.screenX // присвоение координат
            dragContext.y = node.boundsInParent.minY * scale - event.screenY
        }
    var onMouseDraggedEventHandler =
        EventHandler<MouseEvent> { event ->
            val node = event.source as Node
            var offsetX = event.screenX + dragContext.x // изменить координату
            var offsetY = event.screenY + dragContext.y // изменить координату
            // adjust the offset in case we are zoomed
            val scale = graph.getScale()
            offsetX /= scale
            offsetY /= scale
            node.relocate(offsetX, offsetY) //переместить элемент на соответствующие координаты
        }
    var onMouseReleasedEventHandler: EventHandler<MouseEvent> = EventHandler { }
    //##############################################################################
    //прототип добавления вершины по нажатию
    var onMousePressedEventHandler_add_vert =
        EventHandler<MouseEvent> { event ->
            val node = event.source as Node
            val scale = graph.getScale() // получить скалирование?
            dragContext.x = node.boundsInParent.minX * scale - event.screenX // присвоение координат
            dragContext.y = node.boundsInParent.minY * scale - event.screenY
        }

    //##############################################################################
    //прототип удаления вершины по нажатию


    //##############################################################################
    //прототип добавления ребра по нажатию двух вершин ()

    //#############################################################################
     class DragContext {
        // текущие координаты
        var x = 0.0
        var y = 0.0
    }
}