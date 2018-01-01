package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QVBoxLayout
import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.models.ModelBase
import ru.alexey_podusov.machines.ui.post.CellPostWidget

abstract class StringCommandsBaseWidget(model: ModelBase) : CommandsBaseWidget(model) {
    protected val stringWidgetList = ArrayList<StringBaseWidget>()

    private val scrollAreaLayout = QVBoxLayout()
    protected val commandStringsLayout = QVBoxLayout()
    init {
        layoutsManipulation()
        updateCommands()
    }

    abstract fun createStringCommand(): StringBaseWidget
    abstract fun bindCommands()

    private fun layoutsManipulation() {
        scrollArea.widget().setLayout(scrollAreaLayout)
        stringWidgetList.clear()
        scrollAreaLayout.addLayout(commandStringsLayout)
        scrollAreaWidget.setLayout(scrollAreaLayout)
        scrollArea.widget().setLayout(scrollAreaLayout)
        scrollAreaLayout.setAlignment(commandStringsLayout, Qt.AlignmentFlag.AlignTop)


    }

    private fun updateCommands() {
        val commandSize = model.getCommandsSize()

        while (stringWidgetList.size > commandSize) {
            stringWidgetList.removeAt(stringWidgetList.size - 1)
        }

        while (stringWidgetList.size < commandSize) {
            val stringCommand = createStringCommand()
            stringWidgetList.add(stringCommand)
            commandStringsLayout.addWidget(stringCommand)
        }
        bindCommands()
    }

}