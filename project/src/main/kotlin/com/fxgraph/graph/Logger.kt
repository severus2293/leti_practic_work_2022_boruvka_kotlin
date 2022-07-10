package com.fxgraph.graph
import javafx.scene.control.TextArea
import java.io.*
import java.nio.charset.Charset
class Logger(var console: TextArea) {
    fun log(temp:String){
        println(temp)
        console.appendText(temp + "\n")
    }
}