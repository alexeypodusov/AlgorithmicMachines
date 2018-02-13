package ru.alexey_podusov.machines.engines.markov

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.markov.MarkovEngine.MarkovCommand

class MarkovCommandTab(name: String): CommandTab(name) {
    companion object {
        val MAX_COMMANDS = 999
    }

    @Expose
    var commands = ArrayList<MarkovCommand>()

    init {
        insertCommand(0)
    }

    fun changeCommand(command: MarkovCommand) {
        commands.set(command.number, command)
        engine?.commandsChanged()
    }

    override fun insertCommand(number: Int) {
        if (commands.size < MAX_COMMANDS) {
            commands.filter { it.number >= number }.forEach { it.number++ }
            commands.add(number, MarkovCommand(number = number))
            engine?.commandsChanged()
        }
    }

    override fun removeCommand(number: Int) {
        if (commands.size != 1) {
            commands.filter { it.number > number }.forEach { it.number-- }
            commands.removeAt(number)
            engine?.commandsChanged()
        }
    }

    override fun getCommandsSize(): Int {
        return commands.size
    }
}