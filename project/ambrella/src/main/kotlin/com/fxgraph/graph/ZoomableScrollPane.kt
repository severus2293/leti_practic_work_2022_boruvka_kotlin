package com.fxgraph.graph

import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.ScrollPane
import javafx.scene.input.ScrollEvent
import javafx.scene.transform.Scale


class ZoomableScrollPane(var contents: Node): ScrollPane() {
    private var zoomGroup: Group = Group()
    var scaleValue: Double = 1.0;
    var scaleTransform: Scale = Scale(scaleValue,scaleValue, 0.0, 0.0) // масштабирование
    private var delta: Double = 0.1
    private var contentGroup: Group = Group()
    init{
        contentGroup.children.add(zoomGroup)
        zoomGroup.children.add(contents) // добавить холст на панель
        content = contentGroup
        zoomGroup.transforms.add(scaleTransform); // добавить масштабирование
        zoomGroup.onScroll = ZoomHandler() // возможность изменить масштаб с помощью колёсика
    }
    fun getscalevalue(): Double { // получить скалирование
        return scaleValue;
    }

    fun  zoomToActual() {
        zoomTo(1.0);
    }

    private fun zoomTo(scaleValue: Double) { // изменить масштаб

        this.scaleValue = scaleValue;

        scaleTransform.x = scaleValue;
        scaleTransform.y = scaleValue;

    }

    fun zoomActual() { // обычный масштаб

        scaleValue = 1.0
        zoomTo(scaleValue);

    }

    fun zoomOut() { // увеличить масштаб (отдалить)
        scaleValue -= delta;

        if (scaleValue < 0.1) {
            scaleValue = 0.1;
        }

        zoomTo(scaleValue);
    }

    fun zoomIn() { // уменьшить масштаб (приблизить)

        scaleValue += delta;

        if (scaleValue > 10.0) {
            scaleValue = 10.0;
        }

        zoomTo(scaleValue);

    }

    /**
     *
     * @param minimizeOnly
     *            If the content fits already into the viewport, then we don't
     *            zoom if this parameter is true.
     */
    fun zoomToFit(minimizeOnly: Boolean) {

        var scaleX: Double = viewportBounds.width / content.boundsInLocal.width;
        var scaleY: Double = viewportBounds.height / content.boundsInLocal.height;

        // consider current scale (in content calculation)
        scaleX *= scaleValue;
        scaleY *= scaleValue;

        // distorted zoom: we don't want it => we search the minimum scale
        // factor and apply it
       var scale: Double = scaleX.coerceAtMost(scaleY);

        // check precondition
        if (minimizeOnly) {

            // check if zoom factor would be an enlargement and if so, just set
            // it to 1
            if (scale > 1.0) {
                scale = 1.0;
            }
        }

        // apply zoom
        zoomTo(scale);

    }
    private inner class ZoomHandler: EventHandler<ScrollEvent> {

        override fun handle(scrollEvent: ScrollEvent) { // прокрутка


                if (scrollEvent.deltaY < 0) { //колёсико вниз
                    scaleValue -= delta;
                } else {
                    scaleValue += delta; // колёсико вверх
                }

                zoomTo(scaleValue); // масштабировать

                scrollEvent.consume();
            }
        }

}






