package ru.alexey_podusov.machines.ui

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.models.BaseEngine

abstract class BaseCommands(protected val engine: BaseEngine) : QWidget() {
    protected val mainLayout = QVBoxLayout()
    protected val scrollArea = QScrollArea()
    protected val scrollAreaWidget = QWidget()

    val enableCommandButtonsChange = Signal2<Boolean, Boolean>()

    init {
        scrollArea.setWidget(scrollAreaWidget)
        scrollArea.setWidgetResizable(true)
        mainLayout.addWidget(scrollArea)
        setLayout(mainLayout)

        engine.setExecCommandSignal.connect(this, ::onSetExecCommand)
    }

    abstract fun onChangedStatusPlay(statusPlay: BaseEngine.StatusPlay)
    abstract fun onAddCommandClicked()
    abstract fun onDeleteCommandClicked()
    abstract fun onBackCommandClicked()
    abstract fun onForwardCommandClicked()
    abstract fun checkCurrentIndex()
    abstract fun onSetExecCommand(numberCommand: Int, prevCommand: Int)



}