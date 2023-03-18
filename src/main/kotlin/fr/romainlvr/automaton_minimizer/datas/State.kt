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

    fun getName(): String{
        return this.name
    }

    fun getTransitions(): HashMap<String, State>{
        return this.transitions
    }

    fun getTransition(transition: String): State? {
        return this.transitions[transition]
    }

    fun isInitial(): Boolean{
        return this.isInitial
    }

    fun isFinal(): Boolean{
        return this.isFinal
    }


}