package ru.alexey_podusov.machines.ui.tyuring

import com.trolltech.qt.core.QEvent
import com.trolltech.qt.core.QObject
import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.tyuring.TyuringCommandTab.Companion.MAX_STATES
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine.TyuringCommand
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine.TyuringCommandType
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine.TyuringCommandType.NULL_COMMAND
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine.TyuringCommandType.values
import ru.alexey_podusov.machines.ui.custom_widgets.ComboBoxWithoutScroll

class TyuringTableItem : QFrame() {
    private val mainLayout = QHBoxLayout()
    private val replaceEdit = QLineEdit()
    private val commandComboBox = ComboBoxWithoutScroll()
    private val newStateNumberEdit = QLineEdit()

    val onEditedSignal = Signal1<TyuringCommand>()
    val inFocusSignal = Signal2<Int, Int>()

    companion object {
        val HEIGHT_STRING = 20
        val REPLACE_EDIT_WIDHT = 30
        val COMMAND_COMBO_BOX_WIDHT = 40
        val NEW_STATE_NUMBER_WIDHT = 30

        val SELECT_ITEM_CSS = """#commandItem{
                                        margin-right: 5px;
                                        border:1px solid green;
                                        }"""

        val NOSELECT_ITEM_CSS = """#commandItem{
                                        margin: 0px;
                                        border:0px solid green;
                                        }"""
    }

    var numberColumn = 0
    var numberRow = 0

    var replace: String = ""
        set(value) {
            field = value
            replaceEdit.setText(value)
        }

    var tyuringCommandType: TyuringCommandType = NULL_COMMAND
        set(value) {
            field = value
            commandComboBox.blockSignals(true)
            commandComboBox.setCurrentIndex(value.ordinal)
            commandComboBox.blockSignals(false)
        }

    var newState: Int = -1
        set(value) {
            field = value
            if (value != -1) newStateNumberEdit.setText(value.toString())
            else newStateNumberEdit.setText("")
        }


    fun setCommand(command: TyuringCommand) {
        numberColumn = command.numberColumn
        numberRow = command.numberRow
        replace = command.replace
        tyuringCommandType = command.commandType
        newState = command.newState
    }

    init {
        setObjectName("commandItem")

        replaceEdit.setFixedSize(REPLACE_EDIT_WIDHT, HEIGHT_STRING)
        replaceEdit.setAlignment(Qt.AlignmentFlag.AlignCenter)
        replaceEdit.textEdited.connect(this, ::onReplaceTextEdited)
        replaceEdit.installEventFilter(this)
        mainLayout.addWidget(replaceEdit)

        commandComboBox.setFixedSize(COMMAND_COMBO_BOX_WIDHT, HEIGHT_STRING)
        values().forEach { commandComboBox.addItem(it.text) }
        commandComboBox.currentIndexChanged.connect(this, ::onComboBoxIndexChanged)
        mainLayout.addWidget(commandComboBox)

        newStateNumberEdit.setFixedSize(NEW_STATE_NUMBER_WIDHT, HEIGHT_STRING)
        newStateNumberEdit.setMaxLength(3)
        newStateNumberEdit.setValidator(QIntValidator(0, MAX_STATES))
        newStateNumberEdit.setAlignment(Qt.AlignmentFlag.AlignCenter)
        newStateNumberEdit.textEdited.connect(this, ::onNewStateTextEdited)
        newStateNumberEdit.installEventFilter(this)
        mainLayout.addWidget(newStateNumberEdit)

        setSizePolicy(QSizePolicy.Policy.Fixed, QSizePolicy.Policy.Fixed)

        setLayout(mainLayout)
    }

    override fun eventFilter(qObject: QObject?, event: QEvent?): Boolean {
        if (QEvent.Type.FocusIn.equals(event!!.type())) {
            inFocusSignal.emit(numberRow, numberColumn)
        }
        return super.eventFilter(qObject, event)
    }

    private fun onReplaceTextEdited(text: String) {
        var newValue = text
        if (text.length > 1) {
            val cursorPosition = replaceEdit.cursorPosition()
            newValue = Character.toString(text.get(cursorPosition - 1))
        }

        if (newValue == " ") newValue = ""
        replace = newValue
        replaceEdit.setText(newValue)
        onEditedSignal.emit(TyuringCommand(numberColumn, numberRow, replace, tyuringCommandType, newState))

    }

    private fun onComboBoxIndexChanged(index: Int) {
        tyuringCommandType = TyuringCommandType.values()[index]
        onEditedSignal.emit(TyuringCommand(numberColumn, numberRow, replace, tyuringCommandType, newState))
        inFocusSignal.emit(numberRow, numberColumn)
    }

    private fun onNewStateTextEdited(text: String) {
        try {
            newState = text.toInt()
        } catch (e: NumberFormatException) {
            newState = -1
        }
        onEditedSignal.emit(TyuringCommand(numberColumn, numberRow, replace, tyuringCommandType, newState))
    }

    fun setExecBorder() {
        setStyleSheet(SELECT_ITEM_CSS)
    }

    fun hideExecBorder() {
        setStyleSheet(NOSELECT_ITEM_CSS)
    }
}