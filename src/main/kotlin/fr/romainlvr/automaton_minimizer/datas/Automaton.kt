package fr.romainlvr.automaton_minimizer.datas

import java.io.File

class Automaton() {

    private var states: HashMap<String, State> = HashMap()
    private var alphabet = mutableSetOf<String>()
    private lateinit var initialState: State
    private var finalStates: HashMap<String, State> = HashMap()

    constructor(states: Collection<State>) : this() {
        states.forEach { state ->

            if(state.isInitial() && !this::initialState.isInitialized)
                this.initialState = state
            if(state.isFinal())
                this.finalStates[state.getName()] = state
            else if(!state.isInitial() && !state.isFinal())
                this.states[state.getName()] = state

            state.getTransitions().entries.forEach{transition ->
                this.alphabet.add(transition.key)
            }
        }
    }

    fun getInitialState(): State{
        return this.initialState
    }

    fun getFinalsStates(): HashMap<String, State>{
        return this.finalStates
    }

    fun getStates(): HashMap<String, State>{
        return this.states
    }

    fun getState(state: String): State? {
        return this.states[state]
    }

    fun getStateFromAll(state: String): State? {
        val allStates = getAllStates()
        return allStates[state]
    }

    fun getAllStates(): HashMap<String, State>{
        val states: HashMap<String, State> = HashMap()
        this.states.forEach { state ->
            states[state.key] = state.value
        }
        this.finalStates.forEach { finalsStates ->
            states[finalsStates.key] = finalsStates.value
        }
        return states
    }

    fun setInitialState(initialState: State){
        this.initialState = initialState
    }

    fun addState(state: State){
        this.states[state.getName()] = state
    }

    fun addStates(states: Collection<State>){
        states.forEach { state ->
            this.states[state.getName()] = state
        }
    }

    fun addFinalState(finalState: State){
        this.finalStates[finalState.getName()] = finalState
    }

    fun addfinalStates(finalStates: Collection<State>){
        finalStates.forEach { state ->
            this.finalStates[state.getName()] = state
        }
    }

    fun addToAlphabet(letter: String){
        this.alphabet.add(letter)
    }

    fun isDeterministic(): Boolean{
        val allStates = getAllStates()
        allStates.entries.forEach { state ->
            if(state.value.getTransitions().size != this.alphabet.size) {
                println("State ${state.key} is not deterministic : ${state.value.getTransitions().size} transitions ${state.value.getTransitions().keys} instead of ${this.alphabet.size} ${this.alphabet}")
                return false
            }
        }
        return true
    }

    fun isComplete(){

        if(!this::initialState.isInitialized)
            throw Exception("Automaton is not complete : no initial state")

        if (this.finalStates.isEmpty())
            throw Exception("Automaton is not complete : no final state")

        if(!isDeterministic())
            throw Exception("Automaton is not complete : not deterministic")

        println("Automaton is complete & deterministic")
    }

    fun printAutomaton(){
        println("Automaton :")
        println("Initial state : ${this.initialState.getName()}")
        println()
        println("States :")
        this.states.forEach { state ->
            println("State ${state.key} :")
            state.value.getTransitions().forEach { transition ->
                println("Transition ${transition.key} -> ${transition.value.getName()}")
            }
        }
        println()
        println("Final states :c")
        this.finalStates.forEach { finalState ->
            println("State ${finalState.key} :")
            finalState.value.getTransitions().forEach { transition ->
                println("Transition ${transition.key} -> ${transition.value.getName()}")
            }
        }
        println()
    }

    fun generateDotFile(fileName: String){
        var dotFile = "digraph Automate {\n"
        dotFile += "rankdir=LR;\n"
        dotFile += "node [shape=circle];\n"
        dotFile += "start [shape=none, label=\"\"];\n"
        dotFile += "start -> ${this.initialState.getName()};\n"
        this.getAllStates().forEach { states ->
            dotFile += states.key + "[label="+states.key + states.value.isFinal().let { if(it) ", shape=doublecircle" else "" } +"];\n"
        }
        this.getAllStates().forEach { state ->
            state.value.getTransitions().entries.groupBy { it.value.getName() }.forEach { transition ->
                dotFile += state.key + " -> " + transition.key + " [label=\""
                transition.value.forEach { transi ->
                    dotFile += if(transition.value.indexOf(transi) != transition.value.size-1)
                        transi.key + ","
                    else
                        transi.key
                }
                dotFile += "\"];\n"
            }
        }
        dotFile += "}"
        val file = File("src/main/resources/$fileName.dot")
        file.writeText(dotFile)

    }
}