package ru.alexey_podusov.machines.engines.markov

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.markov.MarkovEngine.MarkovCommand
import java.util.Arrays.asList

class MarkovCommandTab(name: String): CommandTab(name) {
    companion object {
        val MAX_COMMANDS = 999

        fun isEmptyCommand(command: MarkovCommand): Boolean {
            return asList(command.comment,
                    command.replacement,
                    command.sample)
                    .filter { it.isEmpty() }
                    .count() == 3
        }
    }

    @Expose
    var commands = ArrayList<MarkovCommand>()
    set(value) {
        field = value
        engine?.commandsChanged()
    }

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

    override fun isEmptyCommand(number: Int): Boolean {
        val command = commands.get(number)
        return asList(command.comment,
                command.replacement,
                command.sample)
                .filter { it.isEmpty() }
                .count() == 3
    }

}