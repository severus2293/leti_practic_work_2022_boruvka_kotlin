package com.fxgraph.vizualization

import com.fxgraph.graph.Cell
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Text

class VXCell(var cellId: String,x: Double,y:Double): Circle(x,y,30.0) {

   var cell: Cell = Cell(cellId)
   var isDragging: Boolean = false
 //  var view: Circle = Circle(x,y,30.0)
   var name: Text = Text(cellId)
   init{

      stroke = Color.GREEN
      fill = Color.GREEN
      enableDrag()
   }
    fun getcellId(): String{
        return cellId
    }

   /**
    * Gets position of the center of this vertex view
    *
    * @return position of the center of this vertex view
    */
   fun getPosition(): Point2D? {
      return Point2D(centerX, centerY)
   }

   fun setPosition(x: Double, y: Double) {
      if (isDragging) {
         return
      }
      centerX = x
      centerY = y
   }
   fun edge_add_config(){
       disableDrag()
       onMousePressed = EventHandler {event: MouseEvent ->
           this.stroke = Color.FIREBRICK
       }

   }
   /**
    * Enables user drag
    */
   fun enableDrag() {
      class Point(var x: Double, var y: Double)

      val dragDelta = Point(0.0, 0.0)
      onMousePressed = EventHandler { event: MouseEvent ->
         if (event.isPrimaryButtonDown) {
            dragDelta.x = centerX - event.x
            dragDelta.y = centerY - event.y
            scene.cursor = Cursor.MOVE
            isDragging = true
            event.consume()
         }
      }
      onMouseReleased = EventHandler { event: MouseEvent ->
         scene.cursor = Cursor.HAND
         isDragging = false
         event.consume()
      }
      onMouseDragged = EventHandler { event: MouseEvent ->
         if (event.isPrimaryButtonDown) {
            val newX = event.x + dragDelta.x
            val x = boundCenterCoordinate(newX, 0.0, parent.layoutBounds.width)
            centerX = x
            val newY = event.y + dragDelta.y
            val y = boundCenterCoordinate(newY, 0.0, parent.layoutBounds.height)
            centerY = y
            this.relocate(x,y) /////
         }
      }
      onMouseEntered = EventHandler { event: MouseEvent ->
         if (!event.isPrimaryButtonDown) {
            scene.cursor = Cursor.HAND
         }
      }
      onMouseExited = EventHandler { event: MouseEvent ->
         if (!event.isPrimaryButtonDown) {
            scene.cursor = Cursor.DEFAULT
         }
      }
   }

   /**
    * Disables user drag
    */
   fun disableDrag() {
      onMousePressed = EventHandler { event: MouseEvent? -> }
      onMouseReleased = EventHandler { event: MouseEvent? -> }
      onMouseDragged = EventHandler { event: MouseEvent? -> }
      onMouseEntered = EventHandler { event: MouseEvent? -> }
      onMouseExited = EventHandler { event: MouseEvent? -> }
   }

   /**
    * Sets value to fit in boundaries: (min + radius; max - radius)
    *
    * @param value - current value
    * @param min   - min value
    * @param max   - max value
    * @return bound value
    */
   private fun boundCenterCoordinate(value: Double, min: Double, max: Double): Double {
      val radius = radius
      return if (value < min + radius) {
         min + radius
      } else if (value > max - radius) {
         max - radius
      } else {
         value
      }
   }

}