package fr.romainlvr.automaton_minimizer.datas

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

    fun getAllStates(): HashMap<String, State>{
        val states: HashMap<String, State> = HashMap()
        states[this.initialState.getName()] = this.initialState
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
        states.forEach { states ->
            this.states[states.getName()] = states
        }
    }

    fun addFinalState(finalState: State){
        this.finalStates[finalState.getName()] = finalState
    }

    fun addfinalStates(finalStates: Collection<State>){
        finalStates.forEach { states ->
            this.finalStates[states.getName()] = states
        }
    }

    fun isDeterministic(): Boolean{
        val allStates = getAllStates()
        allStates.entries.forEach { state ->
            if(state.value.getTransitions().size != this.alphabet.size)
                return false
            if(!this.alphabet.containsAll(state.value.getTransitions().keys))
                return false
        }
        return true
    }
}