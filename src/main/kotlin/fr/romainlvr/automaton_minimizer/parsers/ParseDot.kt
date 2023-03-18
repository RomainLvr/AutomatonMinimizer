package fr.romainlvr.automaton_minimizer.parsers

import java.io.File
import java.io.FileNotFoundException
import com.google.common.io.Resources


class ParseDot{

    private var file: File = File("")

    fun parseFile(path: String){
        openFile(path)
        readFile()
    }
    private fun openFile(path: String){

        try {
            this.file = File(Resources.getResource(path).file)
        }catch (e: FileNotFoundException){
            println("File not found :" + e.message)
        }

    }

    private fun readFile(){
        this.file.forEachLine {
            println(it)
        }
    }
}