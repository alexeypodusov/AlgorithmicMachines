package ru.alexey_podusov.machines.ui.markov

import com.trolltech.qt.gui.QTextCursor
import com.trolltech.qt.gui.QTextEdit
import com.trolltech.qt.gui.QVBoxLayout
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.engines.markov.MarkovWorkareaTab
import ru.alexey_podusov.machines.ui.TabDialog


class MarkovHistoryChangesDialog(mainWindow: MainWindow, tab: EngineTab?) : TabDialog(mainWindow, tab) {
    val mainLayout = QVBoxLayout()
    val textEdit = QTextEdit()

    companion object {
        val TITLE = "Протокол замен. Вкладка: "
        val FIRST_STRING_TAMPLATE = "%d: <font color=\"red\">%s</font> -> <font color=\"red\">%s</font> (правило: %d)"
        val RED_STRING_TAMPLATE = "<font color=\"red\">%s</font>"

        val START_WIDTH = 400
        val START_HEIGHT = 200
    }

    init {
        initUI()
        onWorkareaChanged()
    }

    override fun initUI() {
        super.initUI()

        tab!!.engine!!.workAreaChangedSignal!!.connect(this, ::onWorkareaChanged)

        mainLayout.addWidget(textEdit)
        textEdit.isReadOnly = true
        setLayout(mainLayout)

        setWindowTitle(TITLE + tab!!.name)

        setGeometry(mainWindow.x() + mainWindow.width() - START_WIDTH,
                mainWindow.y(), START_WIDTH, START_HEIGHT)
    }

    private fun onWorkareaChanged() {
        var currentReplacementCount = textEdit.document().lineCount() / 2
        val countFromTab = (tab!! as MarkovWorkareaTab).detailedHistoryReplacement!!.size

        while (currentReplacementCount < countFromTab) {
            addReplacement(currentReplacementCount + 1)
            currentReplacementCount++
        }

        while (currentReplacementCount > countFromTab) {
            for (i in 0..1) {
                removeLastLine()
            }
            currentReplacementCount--
        }
    }

    private fun removeLastLine() {
        val cursor = textEdit.textCursor()
        cursor.movePosition(QTextCursor.MoveOperation.End)
        cursor.select(QTextCursor.SelectionType.LineUnderCursor)
        cursor.removeSelectedText()
        cursor.deletePreviousChar()
    }

    private fun addReplacement(index: Int) {
        val historyItem = (tab!! as MarkovWorkareaTab).detailedHistoryReplacement!!.get(index - 1)
        val firstString = String.format(FIRST_STRING_TAMPLATE,
                index,
                historyItem.sample,
                historyItem.replacement,
                historyItem.numberRules)

        textEdit.append(firstString)

        var beforeString = historyItem.beforeString

        var redPartBeforeString = beforeString.substring(historyItem.startPositionChanged,
                historyItem.startPositionChanged + historyItem.sample.length)

        redPartBeforeString = String.format(RED_STRING_TAMPLATE, redPartBeforeString)

        beforeString = beforeString.replaceRange(historyItem.startPositionChanged,
                historyItem.startPositionChanged + historyItem.sample.length,
                redPartBeforeString)


        var afterString = historyItem.afterString
        var redPartAfterString = afterString.substring(historyItem.startPositionChanged,
                historyItem.startPositionChanged + historyItem.replacement.length)

        redPartAfterString = String.format(RED_STRING_TAMPLATE, redPartAfterString)

        afterString = afterString.replaceRange(historyItem.startPositionChanged,
                historyItem.startPositionChanged + historyItem.replacement.length,
                redPartAfterString)

        textEdit.append(beforeString + " -> " + afterString)
    }

    override fun getTabList(): List<EngineTab> {
        return tab!!.engine!!.workareaTabs
    }

    override fun getCurrentMachine(): MachineType {
        return MachineType.MARKOV
    }

}