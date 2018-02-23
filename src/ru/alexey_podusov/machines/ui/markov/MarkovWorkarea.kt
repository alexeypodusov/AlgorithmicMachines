package ru.alexey_podusov.machines.ui.markov

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.markov.MarkovWorkareaTab
import ru.alexey_podusov.machines.ui.BaseWorkarea

class MarkovWorkarea(tab: MarkovWorkareaTab): BaseWorkarea(tab) {
    private val mainLayout = QVBoxLayout()
    private val stringLayout = QHBoxLayout()
    private val buttonLayout = QHBoxLayout()
    private val editText = QLineEdit()
    private val restoreButton = QPushButton(RESTORE_BUTTON_TEXT)
    private val showHistoryButton = QPushButton(SHOW_HISTORY_BUTTON_TEXT)

    private var historyChangesDialog: HistoryChangesDialog? = null

    companion object {
        val WORKSTRING_TITLE = "Рабочая строка:"
        val RESTORE_BUTTON_TEXT = "Восстановить строку"
        val SHOW_HISTORY_BUTTON_TEXT = "Открыть протокол замен"
        val WIDTH_RESTORE_BUTTON = 180
        val WIDTH_SHOW_HISTORY_BUTTON = 180
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

        buttonLayout.addWidget(restoreButton)
        restoreButton.setFixedWidth(WIDTH_RESTORE_BUTTON)
        restoreButton.clicked.connect(this, ::onRestoreButtonClicked)
        buttonLayout.setAlignment(restoreButton, Qt.AlignmentFlag.AlignLeft)

        buttonLayout.addWidget(showHistoryButton)
        showHistoryButton.setFixedWidth(WIDTH_SHOW_HISTORY_BUTTON)
        showHistoryButton.clicked.connect(this, ::onShowHistoryButtonClicked)
        buttonLayout.setAlignment(showHistoryButton, Qt.AlignmentFlag.AlignRight)

        mainLayout.addLayout(buttonLayout)

        setLayout(mainLayout)
        updateWorkArea()
    }

    private fun onRestoreButtonClicked() {
        tab.restoreWorkarea()
    }

    private fun onShowHistoryButtonClicked() {
        if (historyChangesDialog != null && historyChangesDialog!!.isVisible) {
            historyChangesDialog!!.close()
        }
        historyChangesDialog = HistoryChangesDialog(MainWindow.getMainWindow(), tab as MarkovWorkareaTab)
        historyChangesDialog!!.show()
    }

    override fun updateSizeWidget() {

    }

    override fun updateWorkArea() {
        restoreButton.isEnabled = !tab.savedIsNull()
        editText.setText((tab as MarkovWorkareaTab).string)
    }

    private fun onEditingFinished() {
        (tab as MarkovWorkareaTab).string = editText.text()
    }

    override fun connect() {
        editText.editingFinished.connect(this, ::onEditingFinished)
    }
}