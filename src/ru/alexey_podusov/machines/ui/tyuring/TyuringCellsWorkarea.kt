package ru.alexey_podusov.machines.ui.tyuring

import com.trolltech.qt.gui.QMessageBox
import ru.alexey_podusov.machines.engines.CellsWorkareaTab
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine
import ru.alexey_podusov.machines.engines.tyuring.TyuringWorkareaTab
import ru.alexey_podusov.machines.ui.BaseCell
import ru.alexey_podusov.machines.ui.BaseCellsWorkarea

class TyuringCellsWorkarea(tab: TyuringWorkareaTab) : BaseCellsWorkarea(tab) {
    companion object {
        val DIALOG_ADD_SYMBOL_TEXT = "Данного символа нет алфавите. Добавить?"
        val WINDOW_ADD_SYMBOL_TITLE = "Отсутствие символа в алфавите"
        val YES_BUTTON_TEXT = "Да"
        val NO_BUTTON_TEXT = "Нет"
    }

    init {
        connect()
    }

    override fun createCell(): BaseCell {
        return TyuringCell()
    }

    override fun onCellChanched(numberCell: Int, cellParameter: Any) {
        tab as TyuringWorkareaTab
        val engine = tab.engine as TyuringEngine

        val charSequence: CharSequence = cellParameter as String
        if (!engine.alphabet.contains(charSequence)) {
            dialogAddSymbolInAlphabet(numberCell, cellParameter)
        } else {
            tab.changeValueCell(numberCell, cellParameter)
        }
    }

    private fun dialogAddSymbolInAlphabet(numberCell: Int, cellParameter: String) {
        val msgBox = QMessageBox(this)
        msgBox.setText(DIALOG_ADD_SYMBOL_TEXT)
        msgBox.setWindowTitle(WINDOW_ADD_SYMBOL_TITLE)
        val buttonYes = msgBox.addButton(YES_BUTTON_TEXT, QMessageBox.ButtonRole.AcceptRole)
        msgBox.addButton(NO_BUTTON_TEXT, QMessageBox.ButtonRole.AcceptRole)

        msgBox.exec()

        if (msgBox.clickedButton() == buttonYes) {
            val engine = tab.engine as TyuringEngine
            val newAlphabet = engine.alphabet + cellParameter
            engine.changeAlphabet(newAlphabet.length, newAlphabet)
            (tab as TyuringWorkareaTab).changeValueCell(numberCell, cellParameter)
        }
    }

    override fun updateWorkArea() {
        tab as TyuringWorkareaTab
        for ((i, widget) in cellWidgets.withIndex()) {
            val cellWidget = widget as TyuringCell

            val numberCell = tab.currentCarriage - (numberWidgetCarriage - i)

            if (CellsWorkareaTab.isInTape(numberCell)) {
                cellWidget.number = numberCell
                cellWidget.cellValue = tab.getCell(numberCell)
                if (!cellWidget.isActive) {
                    cellWidget.isActive = true
                }
            } else cellWidget.isActive = false
        }
    }
}