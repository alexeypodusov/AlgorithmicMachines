package ru.alexey_podusov.machines.ui.post


import ru.alexey_podusov.machines.models.ModelPost
import ru.alexey_podusov.machines.ui.CellBaseWidget
import ru.alexey_podusov.machines.ui.CellsWorkareaBaseWidget

class WorkareaPostWidget(override val model: ModelPost) : CellsWorkareaBaseWidget() {
    override fun createCell(): CellBaseWidget {
        return CellPostWidget()
    }
    init {
        updateWorkArea()
        connect()
    }

    override fun onCellChanched(numberCell: Int, cellParameter: Any) {
        model.changeValueCell(numberCell, cellParameter as Boolean)
    }

    override fun updateWorkArea() {
        for (i in cellWidgetList.indices) {
            val cellWidget = cellWidgetList.get(i) as CellPostWidget

            val numberCell = model.currentCarriage - (numberWidgetCarriage - i)

            if (ModelPost.isInTape(numberCell)) {
                cellWidget.number = numberCell
                cellWidget.isMark = model.getCell(numberCell)
                if (!cellWidget.isActive) {
                    cellWidget.isActive = true
                }
            } else cellWidget.isActive = false
        }
    }

    override fun onLeftButtonClicked() {
        model.currentCarriage--
    }

    override fun onRightButtonClicked() {
        model.currentCarriage++
    }
}