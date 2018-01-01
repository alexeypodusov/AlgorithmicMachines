package ru.alexey_podusov.machines.ui

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.models.ModelBase

abstract class CommandsBaseWidget(protected val model: ModelBase): QWidget() {
    protected val mainLayout = QVBoxLayout()
    protected val scrollArea = QScrollArea()
    protected val scrollAreaWidget = QWidget()
    init {
        scrollArea.setWidget(scrollAreaWidget)
        scrollArea.setWidgetResizable(true)
        mainLayout.addWidget(scrollArea)
        setLayout(mainLayout)
    }
}