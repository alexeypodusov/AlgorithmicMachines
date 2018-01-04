package ru.alexey_podusov.machines.ui.post


import ru.alexey_podusov.machines.engines.PostEngine
import ru.alexey_podusov.machines.ui.CellBase
import ru.alexey_podusov.machines.ui.BaseCellsWorkarea

class PostCellsWorkarea(model: PostEngine) : BaseCellsWorkarea(model) {
    init {
        connect()
    }
    override fun onCellChanched(numberCell: Int, cellParameter: Any) {
        (engine as PostEngine).changeValueCell(numberCell, cellParameter as Boolean)
    }

    override fun createCell(): CellBase {
        return PostCell()
    }

    override fun updateWorkArea() {
        engine as PostEngine
        for ((i, widget) in cellWidgets.withIndex()) {
            val cellWidget = widget as PostCell

            val numberCell = engine.currentCarriage - (numberWidgetCarriage - i)

            if (PostEngine.isInTape(numberCell)) {
                cellWidget.number = numberCell
                cellWidget.isMark = engine.getCell(numberCell)
                if (!cellWidget.isActive) {
                    cellWidget.isActive = true
                }
            } else cellWidget.isActive = false
        }
    }

    override fun onLeftButtonClicked() {
        (engine as PostEngine).currentCarriage--
    }

    override fun onRightButtonClicked() {
        (engine as PostEngine).currentCarriage++
    }
}