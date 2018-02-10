package ru.alexey_podusov.machines.engines.post

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommand

class PostCommandTab(name: String) : CommandTab(name) {
    companion object {
        val MAX_COMMANDS = 999
    }

    @Expose
    var commands = ArrayList<PostCommand>()

    init {
        insertCommand(0)
    }


    fun changeCommand(command: PostCommand) {
        commands.set(command.number, command)
        engine?.commandsChanged()
    }

    fun insertCommand(number: Int) {
        if (commands.size < MAX_COMMANDS) {
            commands.filter { it.number >= number }.forEach { it.number++ }
            commands.filter { it.transition >= number }.forEach { it.transition++ }
            commands.filter { it.secondTransition >= number }.forEach { it.secondTransition++ }

            commands.add(number, PostCommand(number = number))
            engine?.commandsChanged()
        }
    }

    fun removeCommand(number: Int) {
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

}