package ru.alexey_podusov.machines.engines.post

import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommand

class PostCommandTab(name: String, engine: PostEngine) : CommandTab(name, engine) {
    companion object {
        val MAX_COMMANDS = 999
    }

    var commands = ArrayList<PostCommand>()

    init {
        insertCommand(0)
    }


    fun changeCommand(command: PostCommand) {
        commands.set(command.number, command)
    }

    fun insertCommand(number: Int) {
        if (commands.size < MAX_COMMANDS) {
            commands.filter { it.number >= number }.forEach { it.number++ }
            commands.filter { it.transition >= number }.forEach { it.transition++ }
            commands.filter { it.secondTransition >= number }.forEach { it.secondTransition++ }

            commands.add(number, PostCommand(number = number))
        }
    }

    fun removeCommand(number: Int) {
        if (number != 0) {
            commands.filter { it.number > number }.forEach { it.number-- }
            commands.filter { it.transition == number }.forEach { it.transition = -1 }
            commands.filter { it.secondTransition == number }.forEach { it.secondTransition = -1 }
            commands.removeAt(number)
        }
    }

    override fun getCommandsSize(): Int {
        return commands.size
    }

}