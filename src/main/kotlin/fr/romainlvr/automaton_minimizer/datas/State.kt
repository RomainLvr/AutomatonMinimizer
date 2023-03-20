package fr.romainlvr.automaton_minimizer.datas

class State (){
    private lateinit var name: String
    private lateinit var transitions: HashMap<String, State>
    private var isInitial: Boolean = false
    private var isFinal: Boolean = false

    constructor(name: String, transitions: HashMap<String, State>, isInitial: Boolean, isFinal: Boolean) : this() {
        this.name = name
        this.transitions = transitions
        this.isInitial = isInitial
        this.isFinal = isFinal
    }

    constructor(name: String, isInitial: Boolean, isFinal: Boolean) : this() {
        this.name = name
        this.transitions = HashMap()
        this.isInitial = isInitial
        this.isFinal = isFinal
    }

    fun getName(): String{
        return this.name
    }

    fun getTransitions(): HashMap<String, State>{
        return this.transitions
    }

    fun getTransition(transition: String): State? {
        return this.transitions[transition]
    }

    fun addTransition(transition: String, state: State): Boolean{
        this.transitions[transition] = state
        return this.transitions.contains(transition)
    }

    fun addTransitions(transitions: HashMap<String, State>){
        this.transitions.putAll(transitions)
    }

    fun isInitial(): Boolean{
        return this.isInitial
    }

    fun isFinal(): Boolean{
        return this.isFinal
    }


}