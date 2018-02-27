package ru.alexey_podusov.machines.ui.post

import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.engines.post.PostEngine
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommandType.CHECK_MARK
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommandType.NULL_COMMAND
import ru.alexey_podusov.machines.ui.BaseTextEditorDialog

class PostTextEditorDialog(mainWindow: MainWindow, tab: EngineTab?) : BaseTextEditorDialog(mainWindow, tab) {
    init {
        initUI()
        PostSyntaxHightlighter(textEdit.document())
        getStringsFromTab()
    }

    private fun getStringsFromTab() {
        for (command in (tab as PostCommandTab).commands) {
            var newLine = command.number.toString()
            if (command.commandType != NULL_COMMAND) newLine += (" " + command.commandType.shortText)
            if (command.transition != -1) newLine += (" " + command.transition)
            if (command.commandType == CHECK_MARK && command.secondTransition != -1) newLine += (" :" + command.secondTransition)
            if (!command.comment.isEmpty()) newLine += (" " + command.comment)
            textEdit.append(newLine)
        }
    }

    override fun onSaveButtonClicked() {
        val lines = textEdit.toPlainText().split('\n')
        val commands = ArrayList<PostEngine.PostCommand>()
        lines.forEach {
            val commandSyntax = PostSyntaxHightlighter.getCommandByString(it)
            if (commandSyntax != null) {
                while (commandSyntax.command.number > commands.size - 1) {
                    commands.add(PostEngine.PostCommand(commands.size))
                }
                commands.set(commandSyntax.command.number, commandSyntax.command)
            }
        }

        while (!commands.isEmpty() && PostCommandTab.isEmptyCommand(commands.last())) {
            commands.removeAt(commands.lastIndex)
        }

        if (commands.isEmpty()) {
            commands.add(PostEngine.PostCommand(0))
        }

        (tab as PostCommandTab).commands = commands

        close()
    }

    override fun getTabList(): List<EngineTab> {
        return tab!!.engine!!.commandTabs
    }

    override fun getCurrentMachine(): MachineType {
        return MachineType.POST
    }
}