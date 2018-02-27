package ru.alexey_podusov.machines.ui.markov

import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.engines.markov.MarkovCommandTab
import ru.alexey_podusov.machines.engines.markov.MarkovEngine
import ru.alexey_podusov.machines.engines.post.PostEngine
import ru.alexey_podusov.machines.ui.BaseTextEditorDialog
import ru.alexey_podusov.machines.ui.post.PostSyntaxHightlighter

class MarkovTextEditorDialog(mainWindow: MainWindow, tab: EngineTab?) : BaseTextEditorDialog(mainWindow, tab) {
    init {
        initUI()
        MarkovSyntaxHightlighter(textEdit.document())
        getStringsFromTab()
    }

    private fun getStringsFromTab() {
        for (command in (tab as MarkovCommandTab).commands) {
            var newLine = command.number.toString()
            if (!command.sample.isEmpty()) newLine += (" " + command.sample)
            if (!command.replacement.isEmpty()) newLine += (" -> " + command.replacement)
            textEdit.append(newLine)
        }
    }

    override fun getTabList(): List<EngineTab> {
        return tab!!.engine!!.commandTabs
    }

    override fun getCurrentMachine(): MachineType {
        return MachineType.MARKOV
    }

    override fun onSaveButtonClicked() {
        val lines = textEdit.toPlainText().split('\n')
        val commands = ArrayList<MarkovEngine.MarkovCommand>()

        lines.forEach {
            val commandSyntax = MarkovSyntaxHightlighter.getCommandByString(it)
            if (commandSyntax != null) {
                while (commandSyntax.command.number > commands.size - 1) {
                    commands.add(MarkovEngine.MarkovCommand(commands.size))
                }

                if (commandSyntax.command.number != -1) {
                    commands.set(commandSyntax.command.number, commandSyntax.command)
                } else {
                    commandSyntax.command.number = commands.size
                    commands.add(commandSyntax.command)
                }
            }
        }

        while (!commands.isEmpty() && MarkovCommandTab.isEmptyCommand(commands.last())) {
            commands.removeAt(commands.lastIndex)
        }

        if (commands.isEmpty()) {
            commands.add(MarkovEngine.MarkovCommand(0))
        }

        (tab as MarkovCommandTab).commands = commands

        close()


    }
}