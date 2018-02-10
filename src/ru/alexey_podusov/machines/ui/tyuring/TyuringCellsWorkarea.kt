package ru.alexey_podusov.machines.ui.tyuring

import ru.alexey_podusov.machines.engines.CellsWorkareaTab
import ru.alexey_podusov.machines.engines.post.PostWorkareaTab
import ru.alexey_podusov.machines.engines.tyuring.TyuringWorkareaTab
import ru.alexey_podusov.machines.ui.BaseCell
import ru.alexey_podusov.machines.ui.BaseCellsWorkarea
import ru.alexey_podusov.machines.ui.post.PostCell

class TyuringCellsWorkarea(tab: TyuringWorkareaTab) : BaseCellsWorkarea(tab) {
    init {
        connect()
    }

    override fun createCell(): BaseCell {
        return TyuringCell()
    }

    override fun onCellChanched(numberCell: Int, cellParameter: Any) {
        (tab as TyuringWorkareaTab).changeValueCell(numberCell, cellParameter as String)
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