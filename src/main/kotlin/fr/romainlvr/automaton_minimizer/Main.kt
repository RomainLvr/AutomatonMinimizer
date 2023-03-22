package fr.romainlvr.automaton_minimizer

import fr.romainlvr.automaton_minimizer.minimization.MinimalAutomaton
import fr.romainlvr.automaton_minimizer.parsers.ParseDot

fun main(args: Array<String>) {

    val parser = ParseDot()
    val automaton = parser.parseFile("automaton2.dot")
    automaton.isComplete()
    automaton.printAutomaton()
    println(automaton.generateDotFile("test"))
    MinimalAutomaton(automaton).minimize()
}