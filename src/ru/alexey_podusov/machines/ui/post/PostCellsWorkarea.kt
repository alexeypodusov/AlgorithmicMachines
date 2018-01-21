package ru.alexey_podusov.machines.ui.post


import ru.alexey_podusov.machines.engines.post.PostEngine
import ru.alexey_podusov.machines.engines.post.PostWorkareaTab
import ru.alexey_podusov.machines.ui.CellBase
import ru.alexey_podusov.machines.ui.BaseCellsWorkarea

class PostCellsWorkarea(tab: PostWorkareaTab) : BaseCellsWorkarea(tab) {
    init {
        connect()
    }
    override fun onCellChanched(numberCell: Int, cellParameter: Any) {
        (tab as PostWorkareaTab).changeValueCell(numberCell, cellParameter as Boolean)
    }

    override fun createCell(): CellBase {
        return PostCell()
    }

    override fun updateWorkArea() {
        tab as PostWorkareaTab
        for ((i, widget) in cellWidgets.withIndex()) {
            val cellWidget = widget as PostCell

            val numberCell = tab.currentCarriage - (numberWidgetCarriage - i)

            if (PostWorkareaTab.isInTape(numberCell)) {
                cellWidget.number = numberCell
                cellWidget.isMark = tab.getCell(numberCell)
                if (!cellWidget.isActive) {
                    cellWidget.isActive = true
                }
            } else cellWidget.isActive = false
        }
    }

    override fun onLeftButtonClicked() {
        (tab as PostWorkareaTab).currentCarriage--
    }

    override fun onRightButtonClicked() {
        (tab as PostWorkareaTab).currentCarriage++
    }
}