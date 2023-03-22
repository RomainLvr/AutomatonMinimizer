package fr.romainlvr.automaton_minimizer.parsers

import java.io.File
import java.io.FileNotFoundException
import com.google.common.io.Resources
import fr.romainlvr.automaton_minimizer.datas.Automaton
import fr.romainlvr.automaton_minimizer.datas.State
import java.util.regex.Pattern


class ParseDot{

    private var file: File = File("")
    private var automaton: Automaton = Automaton()

    fun parseFile(path: String): Automaton{
        openFile(path)
        readFile()
        return this.automaton
    }

    private fun openFile(path: String){
        try {
            this.file = File(path)
            this.automaton = Automaton()
            println("File opened" + this.file.path)
        }catch (e: FileNotFoundException){
            println("File not found :" + e.message)
        }
    }

    private fun readFile(){
        println("Parsing file...")

        val patternInitialState = Pattern.compile("^\\s*start\\s*->\\s*\\w+;\\s*$")
        val patternState = Pattern.compile("^\\s*\\w+\\s+\\[label=\".*\"(,\\s+shape=([a-z]+))?\\];\\s*$")
        val patternTransition = Pattern.compile("^\\s*\\w+\\s+->\\s+\\w+\\s+\\[label=\".*\"?\\];\\s*$")

        File(Resources.getResource(this.file.path).file).forEachLine { line ->

            val matcherState = patternState.matcher(line)
            val matcherTransition = patternTransition.matcher(line)
            val matcherInitialState = patternInitialState.matcher(line)
            if (matcherState.matches()) {
                val stateName = matcherState.group(0).split("\"")[1]
                val isFinal = matcherState.group(0).contains("doublecircle")
                val state = State(stateName, false, isFinal)

                if (isFinal) {
                    this.automaton.addFinalState(state)
                }else{
                    this.automaton.addState(state)
                }
            } else if (matcherTransition.matches()) {
                val fromStateName = matcherTransition.group(0).split("->")[0].trim().split("[")[0].trim()
                val toStateName = matcherTransition.group(0).split("->")[1].trim().split("[")[0].trim()
                val label = matcherTransition.group(0).split("\"")[1]

                val fromState = this.automaton.getStateFromAll(fromStateName) ?: State(fromStateName, false, false)
                val toState = this.automaton.getStateFromAll(toStateName) ?: State(toStateName, false, false)

                if(label.contains(",")){
                    label.split(",").forEach { label ->
                        this.automaton.addToAlphabet(label)
                        fromState.addTransition(label, toState)
                    }
                }else{
                    this.automaton.addToAlphabet(label)
                    fromState.addTransition(label, toState)
                }


            }else if (matcherInitialState.matches()) {
                this.automaton.setInitialState(State(matcherInitialState.group(0).split("->")[1].trim().split(";")[0].trim(), true, false))
            }
        }
    }
}