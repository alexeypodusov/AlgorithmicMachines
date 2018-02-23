package ru.alexey_podusov.machines.ui.markov

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QDialog
import com.trolltech.qt.gui.QTextEdit
import com.trolltech.qt.gui.QVBoxLayout
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.markov.MarkovWorkareaTab

class HistoryChangesDialog(var mainWindow: MainWindow, var tab: MarkovWorkareaTab?) : QDialog(mainWindow) {
    val mainLayout = QVBoxLayout()
    val textEdit = QTextEdit()

    companion object {
        val TITLE = "Протокол замен. Вкладка: "
        val START_WIDTH = 400
        val START_HEIGHT = 200
    }

    init {
        initUI()
    }

    private fun initUI() {
        mainLayout.addWidget(textEdit)
        textEdit.isReadOnly = true
        setLayout(mainLayout)

        setWindowTitle(TITLE + tab!!.name)

        setGeometry(mainWindow.x() + mainWindow.width() - START_WIDTH,
                mainWindow.y(), START_WIDTH, START_HEIGHT)

        val flags = windowFlags()
        flags.clear(Qt.WindowType.WindowContextHelpButtonHint)
        setWindowFlags(flags)

        mainWindow.currentMachineChanged.connect(this, ::onCurrentMachineChange)
        tab!!.engine!!.workAreaChangedSignal!!.connect(this, ::onWorkareaChanged)
        tab!!.engine!!.changedTabsSignal!!.connect(this, ::onTabsChanged)
    }

    private fun bindView() {

    }

    private fun onWorkareaChanged() {
    }

    private fun onTabsChanged() {
        if (tab != null && tab!!.engine != null) {
            if (tab!!.engine!!.workareaTabs.contains(tab!!)) {
                return
            }
        }

        close()
    }

    private fun onCurrentMachineChange() {
        if (mainWindow.currentMachine != MachineType.MARKOV) {
            close()
        }
    }
}