package com.fxgraph.graph
import javafx.scene.control.TextArea
class Logger(var console: TextArea) {
    fun log(temp:String){
        println(temp)
        console.appendText(temp + "\n")
    }
}