package fr.romainlvr.automaton_minimizer

import fr.romainlvr.automaton_minimizer.parsers.ParseDot

fun main(args: Array<String>) {

    val parser = ParseDot()
    parser.parseFile("automaton.dot")
}