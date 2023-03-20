package fr.romainlvr.automaton_minimizer

import fr.romainlvr.automaton_minimizer.datas.Automaton
import fr.romainlvr.automaton_minimizer.datas.State
import fr.romainlvr.automaton_minimizer.parsers.ParseDot

fun main(args: Array<String>) {

    val parser = ParseDot()
    parser.parseFile("automaton.dot")

    val initialState: State = State("0", true, false)
    val state1: State = State("1", false, false)
    val state2: State = State("2", false, true)
    val state3: State = State("3", false, false)
    val state4: State = State("4", false, true)
    val state5: State = State("5", false, true)
    val state6: State = State("6", false, false)
    val state7: State = State("7", false, false)
    val state8: State = State("8", false, true)
    val state9: State = State("9", false, false)


    initialState.addTransition("a", state9)
    initialState.addTransition("b", state8)
    state9.addTransition("a", state3)
    state9.addTransition("b", state4)
    state3.addTransition("a", initialState)
    state3.addTransition("b", state4)
    state8.addTransition("b", state3)
    state8.addTransition("a", state5)
    state4.addTransition("b", state9)
    state4.addTransition("a", state2)
    state2.addTransition("a", state1)
    state2.addTransition("b", state9)
    state5.addTransition("a", state7)
    state5.addTransition("b", state3)
    state1.addTransition("a", state6)
    state1.addTransition("b", state1)
    state7.addTransition("a", state6)
    state7.addTransition("b", state1)
    state6.addTransition("a", state6)
    state6.addTransition("b", state7)

    val automaton = Automaton(listOf(initialState, state1, state2, state3, state4, state5, state6, state7, state8, state9))

    println("Initial state : " + automaton.getInitialState().getName())
    automaton.getFinalsStates().forEach { state ->
        println("Final state : " + state.value.getName())
    }
    automaton.getStates().forEach { state ->
        println("States : " + state.value.getName())
    }
    println("Déterministic : " + automaton.isDeterministic())
}