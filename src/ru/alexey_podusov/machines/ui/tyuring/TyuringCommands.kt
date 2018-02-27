package ru.alexey_podusov.machines.ui.tyuring

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QGridLayout
import com.trolltech.qt.gui.QVBoxLayout
import ru.alexey_podusov.machines.MainWindow.Companion.getMainWindow
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.tyuring.TyuringCommandTab
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine.TyuringCommand
import ru.alexey_podusov.machines.engines.tyuring.TyuringWorkareaTab
import ru.alexey_podusov.machines.ui.BaseCommands
import ru.alexey_podusov.machines.utils.UserPreferences

class TyuringCommands(tab: TyuringCommandTab) : BaseCommands(tab) {
    private val mainWindow = getMainWindow()
    private var currentWorkareaTab = (mainWindow.workareaTabWidget.getCurrent() as TyuringCellsWorkarea).tab as TyuringWorkareaTab

    private val scrollAreaLayout = QVBoxLayout()
    private val commandsLayout = QGridLayout()

    private var rowsWithoutHeaderCount = 0
    private var columnWithoutHeaderCount = 0

    private var selectedRowWithoutHeader = 0
    private var selectedColumnWithoutHeader = 0

    private var currentExecColumn = -1
    private var currentExecRow = -1

    private val transCommands = ArrayList<CommandRowAndColumn>()

    data class CommandRowAndColumn(var row: Int, var column: Int)

    companion object {
        val HEIGHT_COLUMN_HEADER = 20
        val WIDTH_ROW_HEADER = 40
        val EMPTY_REPLACE_TITLE = "Пробел"
    }

    init {
        initUI()
        updateCommands()
        transCommands.add(CommandRowAndColumn(0,0))
    }

    private fun initUI() {
        scrollArea.widget().setLayout(scrollAreaLayout)
        scrollAreaLayout.addLayout(commandsLayout)
        commandsLayout.setSpacing(0)

        scrollAreaLayout.setAlignment(commandsLayout, Qt.AlignmentFlag.AlignTop)
    }

    override fun updateCommands() {
        tab as TyuringCommandTab
        val rowCount = tab.getRowsCount()
        val columnCount = tab.getColumnsCount()

        while (rowsWithoutHeaderCount > rowCount) {
            removeWidgetItem(rowsWithoutHeaderCount, 0)
            for (column in 0 until columnWithoutHeaderCount) {
                removeWidgetItem(rowsWithoutHeaderCount, column + 1)
            }
            rowsWithoutHeaderCount--
        }

        while (columnWithoutHeaderCount > columnCount) {
            removeWidgetItem(0, columnWithoutHeaderCount)
            for (row in 0 until rowsWithoutHeaderCount) {
                removeWidgetItem(row + 1, columnWithoutHeaderCount)
            }
            columnWithoutHeaderCount--
        }

        while (rowsWithoutHeaderCount < rowCount) {
            for (column in 0 until columnWithoutHeaderCount) {
                addWidgetItem(rowsWithoutHeaderCount + 1, column + 1)
            }
            addRowHeader(rowsWithoutHeaderCount + 1)

            rowsWithoutHeaderCount++
        }

        while (columnWithoutHeaderCount < columnCount) {
            for (row in 0 until rowsWithoutHeaderCount) {
                addWidgetItem(row + 1, columnWithoutHeaderCount + 1)
            }
            addColumnHeader(columnWithoutHeaderCount + 1)

            columnWithoutHeaderCount++
        }

        bindCommands()
        updateSelectingCommand(selectedRowWithoutHeader, selectedColumnWithoutHeader)
    }

    private fun addColumnHeader(column: Int) {
        val headerItem = TyuringHeaderLabel()
        headerItem.setText("Q$column")
        //headerItem.setFixedHeight(HEIGHT_COLUMN_HEADER)
        commandsLayout.addWidget(headerItem, 0, column)
    }

    private fun addRowHeader(row: Int) {
        val headerItem = TyuringHeaderLabel()
        headerItem.setFixedWidth(WIDTH_ROW_HEADER)
        commandsLayout.addWidget(headerItem, row, 0)
    }

    private fun addWidgetItem(row: Int, column: Int) {
        val tableItem = TyuringTableItem()
        tableItem.onEditedSignal.connect(this, ::onCommandEdited)
        tableItem.inFocusSignal.connect(this, ::onInFocusCommand)
        tableItem.onLinkClickedSignal.connect(this, ::onClickNewState)
        commandsLayout.addWidget(tableItem, row, column)
    }

    private fun onClickNewState(transitionColumnNum: Int, senderRow: Int, senderColumn: Int) {
        if (transitionColumnNum < columnWithoutHeaderCount) {
            if (transCommands.size > 1) {
                for (i in transCommands.size - 1 downTo currentTransCommandIndex + 1) {
                    transCommands.removeAt(i)
                }
            }

            if (transCommands.get(currentTransCommandIndex).column != senderColumn) {
                transCommands.add(CommandRowAndColumn(senderRow, senderColumn))
            }
            currentTransCommandIndex++
            transCommands.add(CommandRowAndColumn(selectedRowWithoutHeader, transitionColumnNum))

            goToCommandByTransCommandIndex(currentTransCommandIndex)
        }
    }

    private fun onInFocusCommand(rowWithoutHeader: Int, columnWithoutHeader: Int) {

        val isAutoDeleting = UserPreferences.instance.autoDeleteEmptyCommands
        if (isAutoDeleting && selectedColumnWithoutHeader < columnWithoutHeaderCount && selectedColumnWithoutHeader != columnWithoutHeader) {
            if (columnWithoutHeaderCount != 1 && tab.isEmptyCommand(selectedColumnWithoutHeader)) {
                tab.removeCommand(selectedColumnWithoutHeader)
            }
        }
        updateSelectingCommand(rowWithoutHeader, columnWithoutHeader)
    }

    private fun updateSelectingCommand(rowWithoutHeader: Int, columnWithoutHeader: Int) {
        //убираем старое выделение
        if (selectedRowWithoutHeader < rowsWithoutHeaderCount) {
            val headerWidget = commandsLayout.itemAtPosition(selectedRowWithoutHeader + 1, 0)
                    .widget() as TyuringHeaderLabel
            headerWidget.isSelected = false
        }

        if (selectedColumnWithoutHeader < columnWithoutHeaderCount) {
            val headerWidget = commandsLayout.itemAtPosition(0, selectedColumnWithoutHeader + 1)
                    .widget() as TyuringHeaderLabel
            headerWidget.isSelected = false
        }

        if (rowWithoutHeader < rowsWithoutHeaderCount) selectedRowWithoutHeader = rowWithoutHeader
        else selectedRowWithoutHeader = rowsWithoutHeaderCount - 1

        if (columnWithoutHeader < columnWithoutHeaderCount) selectedColumnWithoutHeader = columnWithoutHeader
        else selectedColumnWithoutHeader = columnWithoutHeaderCount - 1

        val selectedRowWidget = commandsLayout.itemAtPosition(selectedRowWithoutHeader + 1, 0)
                .widget() as TyuringHeaderLabel
        selectedRowWidget.isSelected = true

        val selectedColumnWidget = commandsLayout.itemAtPosition(0, selectedColumnWithoutHeader + 1)
                .widget() as TyuringHeaderLabel
        selectedColumnWidget.isSelected = true
    }


    private fun removeWidgetItem(row: Int, column: Int) {
        val item = commandsLayout.itemAtPosition(row, column)
        item.widget().hide()
        commandsLayout.removeItem(item)
    }

    private fun onCommandEdited(command: TyuringCommand) {
        (tab as TyuringCommandTab).changeCommand(command)
        if (command.numberColumn == tab.getColumnsCount() - 1) {
            tab.insertCommand(command.numberColumn + 1)
        }
    }

    private fun bindCommands() {
        tab as TyuringCommandTab
        val engine = tab.engine as TyuringEngine

        for ((rowIndex, row) in tab.commands.withIndex()) {
            val headerItemWidget = commandsLayout.itemAtPosition(rowIndex + 1, 0).widget() as TyuringHeaderLabel
            if (rowIndex != tab.commands.size - 1) {
                headerItemWidget.setText(Character.toString(engine.alphabet.get(rowIndex)))
            } else {
                headerItemWidget.setText(EMPTY_REPLACE_TITLE)
            }

            for ((columnIndex, command) in row.withIndex()) {
                val itemWidget = commandsLayout.itemAtPosition(rowIndex + 1, columnIndex + 1).widget() as TyuringTableItem
                itemWidget.setCommand(command)
            }
        }
    }

    override fun onChangedStatusPlay(statusPlay: BaseEngine.StatusPlay) {
        if (statusPlay == BaseEngine.StatusPlay.STOPPED) {
            deselectExecCommand()
            currentExecColumn = -1
            currentExecRow = -1
        } else {
            currentWorkareaTab = (mainWindow.workareaTabWidget.getCurrent() as TyuringCellsWorkarea).tab as TyuringWorkareaTab

        }
    }

    private fun deselectExecCommand() {
        if (currentExecColumn != -1 || currentExecRow != -1) {
            (commandsLayout.itemAtPosition(currentExecRow + 1, currentExecColumn + 1).widget() as TyuringTableItem).hideExecBorder()
        }
    }

    override fun onInsertAfterClicked() {
        tab.insertCommand(selectedColumnWithoutHeader + 1)
    }

    override fun onInsertBeforeClicked() {
        tab.insertCommand(selectedColumnWithoutHeader)
        updateSelectingCommand(selectedRowWithoutHeader, selectedColumnWithoutHeader + 1)
    }

    override fun onDeleteCommandClicked() {
        tab.removeCommand(selectedColumnWithoutHeader)
    }

    override fun goToCommandByTransCommandIndex(commandIndexTransition: Int): Boolean {
        val transitionCommand = transCommands.get(commandIndexTransition)

        if (transitionCommand.column >= columnWithoutHeaderCount) {
            transCommands.removeAt(commandIndexTransition)
            return false
        }

        var transRow = transitionCommand.row

        if (transRow >= rowsWithoutHeaderCount) {
            transRow = 0
        }

        checkCurrentIndex()
        updateSelectingCommand(transRow, transitionCommand.column)
        val widget = commandsLayout.itemAtPosition(transRow + 1, transitionCommand.column + 1).widget()
        scrollArea.ensureWidgetVisible(widget)

        return true
    }

    override fun getTransCommandsSize(): Int {
        return transCommands.size
    }

    override fun onSetExecCommand(numberCommand: Int, prevCommand: Int) {
        deselectExecCommand()
        var rowNum = (currentWorkareaTab.engine as TyuringEngine).getRowNumber(currentWorkareaTab, tab as TyuringCommandTab)

        if (rowNum == -1) {
            rowNum = rowsWithoutHeaderCount - 1
        }

        currentExecColumn = numberCommand
        currentExecRow = rowNum
        val widget = (commandsLayout.itemAtPosition(currentExecRow + 1, currentExecColumn + 1).widget() as TyuringTableItem)
        widget.setExecBorder()
        scrollArea.ensureWidgetVisible(widget)
    }
}