package ru.alexey_podusov.machines.ui.markov

import com.trolltech.qt.gui.QHBoxLayout
import com.trolltech.qt.gui.QLabel
import com.trolltech.qt.gui.QLineEdit
import com.trolltech.qt.gui.QVBoxLayout
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.markov.MarkovWorkareaTab
import ru.alexey_podusov.machines.ui.BaseWorkarea

class MarkovWorkarea(tab: MarkovWorkareaTab): BaseWorkarea(tab) {
    private val mainLayout = QVBoxLayout()
    private val stringLayout = QHBoxLayout()
    private val editText = QLineEdit()

    companion object {
        val WORKSTRING_TITLE = "Рабочая строка:"
        val MAX_LENGHT = 10000
    }

    init {
        initUI()
        connect()
    }

    private fun initUI() {
        editText.setMaxLength(MAX_LENGHT)
        stringLayout.addWidget(QLabel(WORKSTRING_TITLE))
        stringLayout.addWidget(editText)
        mainLayout.addLayout(stringLayout)
        setLayout(mainLayout)
        updateWorkArea()
    }

    override fun updateSizeWidget() {

    }

    override fun updateWorkArea() {
        editText.setText((tab as MarkovWorkareaTab).string)
    }

    private fun onEditingFinished() {
        (tab as MarkovWorkareaTab).string = editText.text()
    }

    override fun connect() {
        editText.editingFinished.connect(this, ::onEditingFinished)
    }
}