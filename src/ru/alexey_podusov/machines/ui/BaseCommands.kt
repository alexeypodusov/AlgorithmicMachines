package ru.alexey_podusov.machines.ui

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab

abstract class BaseCommands(protected val tab: CommandTab) : QWidget() {
    protected val mainLayout = QVBoxLayout()
    protected val scrollArea = QScrollArea()
    protected val scrollAreaWidget = QWidget()

    val enableCommandButtonsChange = Signal2<Boolean, Boolean>()

    init {
        scrollArea.setWidget(scrollAreaWidget)
        scrollArea.setWidgetResizable(true)
        mainLayout.addWidget(scrollArea)
        setLayout(mainLayout)
    }

    abstract fun onChangedStatusPlay(statusPlay: BaseEngine.StatusPlay)
    abstract fun onInsertAfterClicked()
    abstract fun onInsertBeforeClicked()
    abstract fun onDeleteCommandClicked()
    abstract fun onBackCommandClicked()
    abstract fun onForwardCommandClicked()
    abstract fun checkCurrentIndex()
    abstract fun onSetExecCommand(numberCommand: Int, prevCommand: Int)
    abstract fun updateCommands()

}