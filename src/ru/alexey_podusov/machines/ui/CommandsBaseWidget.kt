package ru.alexey_podusov.machines.ui

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.models.ModelBase

abstract class CommandsBaseWidget(protected val model: ModelBase) : QWidget() {
    protected val mainLayout = QVBoxLayout()
    protected val scrollArea = QScrollArea()
    protected val scrollAreaWidget = QWidget()

    val enableCommandButtonsChange = Signal2<Boolean, Boolean>()

    init {
        scrollArea.setWidget(scrollAreaWidget)
        scrollArea.setWidgetResizable(true)
        mainLayout.addWidget(scrollArea)
        setLayout(mainLayout)

        model.setExecCommandSignal.connect(this, "onSetExecCommand(int, int)")
    }

    abstract fun onChangedStatusPlay(statusPlay: ModelBase.StatusPlay)
    abstract fun onAddCommandClicked()
    abstract fun onDeleteCommandClicked()
    abstract fun onBackCommandClicked()
    abstract fun onForwardCommandClicked()
    abstract fun checkCurrentIndex()
    abstract fun onSetExecCommand(numberCommand: Int, prevCommand: Int)



}