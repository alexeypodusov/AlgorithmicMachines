package ru.alexey_podusov.machines.ui.post


import ru.alexey_podusov.machines.models.ModelPost
import ru.alexey_podusov.machines.ui.CellBaseWidget
import ru.alexey_podusov.machines.ui.CellsWorkareaBaseWidget

class WorkareaPostWidget(model: ModelPost) : CellsWorkareaBaseWidget(model) {
    init {
        connect()
    }
    override fun onCellChanched(numberCell: Int, cellParameter: Any) {
        (model as ModelPost).changeValueCell(numberCell, cellParameter as Boolean)
    }

    override fun createCell(): CellBaseWidget {
        return CellPostWidget()
    }

    override fun updateWorkArea() {
        model as ModelPost
        for ((i, widget) in cellWidgetList.withIndex()) {
            val cellWidget = widget as CellPostWidget

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
        (model as ModelPost).currentCarriage--
    }

    override fun onRightButtonClicked() {
        (model as ModelPost).currentCarriage++
    }
}