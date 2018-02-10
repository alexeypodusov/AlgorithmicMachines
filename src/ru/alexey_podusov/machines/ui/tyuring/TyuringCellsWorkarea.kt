package ru.alexey_podusov.machines.ui.tyuring

import ru.alexey_podusov.machines.engines.tyuring.TyuringWorkareaTab
import ru.alexey_podusov.machines.ui.BaseCell
import ru.alexey_podusov.machines.ui.BaseCellsWorkarea

class TyuringCellsWorkarea(tab: TyuringWorkareaTab) : BaseCellsWorkarea(tab) {
    init {
        connect()
    }

    override fun createCell(): BaseCell {
        return TyuringCell()
    }

    override fun onCellChanched(numberCell: Int, cellParameter: Any) {

    }

    override fun onLeftButtonClicked() {

    }

    override fun onRightButtonClicked() {

    }

    override fun updateWorkArea() {
    }
}