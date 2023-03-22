package fr.romainlvr.automaton_minimizer.minimization

import fr.romainlvr.automaton_minimizer.datas.Automaton
import fr.romainlvr.automaton_minimizer.datas.State

class MinimalAutomaton(automate: Automaton){

    private val states: HashMap<String, State> = automate.getStates()
    private val finalsStates: HashMap<String, State> = automate.getFinalsStates()

    private var statesGroups: HashMap<String, ArrayList<State>> = hashMapOf(
            finalsStates.keys.toString() to finalsStates.values.toCollection(ArrayList()),
            states.keys.toString() to states.values.toCollection(ArrayList())
    )

    fun minimize(){
        printGroups()
        while (true){
            val newGroups = splitGroups()
            if(newGroups == statesGroups)
                break
            statesGroups = newGroups
            printGroups()
        }
    }

    private fun splitGroups(): HashMap<String, ArrayList<State>>{
        val newGroups: HashMap<String, ArrayList<State>> = hashMapOf()
        this.statesGroups.forEach { group ->
            val groupTransitions: HashMap<HashMap<String, ArrayList<State>>, ArrayList<State>> = hashMapOf()
            group.value.forEach { groupState ->
                val stateTransitions: HashMap<String, ArrayList<State>> = hashMapOf()
                groupState.getTransitions().forEach { transition ->
                    stateTransitions[transition.key] = getGroup(transition.value)
                }
                if(groupTransitions.containsKey(stateTransitions)){
                    groupTransitions[stateTransitions]?.add(groupState)
                }else{
                    groupTransitions[stateTransitions] = arrayListOf(groupState)
                }
            }
            groupTransitions.forEach { groupTransition ->
                newGroups[groupTransition.value.map { it.getName() }.toString()] = groupTransition.value
            }
        }
        return newGroups
    }

    private fun getGroup(state: State): ArrayList<State>{
        var groupe: ArrayList<State> = arrayListOf()
        this.statesGroups.forEach { group ->
            if(group.value.contains(state))
                groupe = group.value
        }
        return groupe
    }

    private fun printGroups(){
        println()
        statesGroups.forEach { group ->
            println(group.key + " -> " + group.value.map { it.getName() })
        }
        println()
    }


}