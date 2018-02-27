package ru.alexey_podusov.machines.engines.post

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommand
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommandType.NULL_COMMAND
import java.util.Arrays.asList

class PostCommandTab(name: String) : CommandTab(name) {
    companion object {
        val MAX_COMMANDS = 999

        fun isEmptyCommand(command: PostCommand): Boolean {
            return command.comment.isEmpty() &&
                    command.commandType == NULL_COMMAND &&
                    command.secondTransition == -1 &&
                    command.transition == -1
        }
    }

    @Expose
    var commands = ArrayList<PostCommand>()
    set(value) {
        field = value
        engine?.commandsChanged()
    }

    init {
        insertCommand(0)
    }


    fun changeCommand(command: PostCommand) {
        commands.set(command.number, command)
        engine?.commandsChanged()
    }

    override fun insertCommand(number: Int) {
        if (commands.size < MAX_COMMANDS) {
            commands.filter { it.number >= number }.forEach { it.number++ }
            commands.filter { it.transition >= number }.forEach { it.transition++ }
            commands.filter { it.secondTransition >= number }.forEach { it.secondTransition++ }

            commands.add(number, PostCommand(number = number))
            engine?.commandsChanged()
        }
    }

    override fun removeCommand(number: Int) {
        if (commands.size != 1) {
            commands.filter { it.number > number }.forEach { it.number-- }
            commands.filter { it.transition == number }.forEach { it.transition = -1 }
            commands.filter { it.secondTransition == number }.forEach { it.secondTransition = -1 }
            commands.removeAt(number)
            engine?.commandsChanged()
        }
    }

    override fun getCommandsSize(): Int {
        return commands.size
    }

    override fun isEmptyCommand(number: Int): Boolean {
        val command = commands.get(number)
        return command.comment.isEmpty() &&
                command.commandType == NULL_COMMAND &&
                command.secondTransition == -1 &&
                command.transition == -1
    }
}