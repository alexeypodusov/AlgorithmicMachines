package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QApplication
import com.trolltech.qt.gui.QHBoxLayout
import ru.alexey_podusov.machines.forms.post.Ui_PostWorkAreaWidget
import ru.alexey_podusov.machines.models.ModelBase
import ru.alexey_podusov.machines.ui.post.StringPostWidget

abstract class CellsWorkareaBaseWidget(model: ModelBase) : WorkareaBaseWidget(model) {
    protected val ui = Ui_PostWorkAreaWidget()
    protected val cellWidgetList = ArrayList<CellBaseWidget>()

    protected var countCells: Int = 0
    protected var numberWidgetCarriage: Int = 0

    companion object {
        val SPACING_CELL_LAYOUT = 6
    }

    init {
        ui.setupUi(this)
        ui.scrollArea.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)

        ui.LeftPushButton.clicked.connect(this, "onLeftButtonClicked()")
        ui.RightPushButton.clicked.connect(this, "onRightButtonClicked()")
        initCells()
    }

    protected abstract fun createCell(): CellBaseWidget
    protected abstract fun onCellChanched(numberCell: Int, cellParameter:Any)
    protected abstract fun onLeftButtonClicked()
    protected abstract fun onRightButtonClicked()


    fun initCells() {
        cellWidgetList.clear()
        val scrollAreaLayout = QHBoxLayout(this)

        countCells = (QApplication.desktop().width() /
                (CellBaseWidget.WIDTH_CELL + SPACING_CELL_LAYOUT)) + 100

        numberWidgetCarriage = countCells / 2

        for (i: Int in 0..countCells) {
            val cell: CellBaseWidget = createCell()
            cellWidgetList.add(cell)

            cell.onCellChanchedSignal.connect(this, "onCellChanched(int, java.lang.Object)")
            scrollAreaLayout.addWidget(cell)
        }

        ui.scrollArea.widget().setLayout(scrollAreaLayout)
        scrollAreaLayout.setSpacing(SPACING_CELL_LAYOUT)
        updateWorkArea()
        cellWidgetList.get(numberWidgetCarriage).setCurrent()
    }

    override fun updateSizeWidget() {
        val widthCellWithSpacing: Int = CellBaseWidget.WIDTH_CELL + SPACING_CELL_LAYOUT
        val widthScrollArea: Int = ui.scrollArea.size().width()

        val leftCellNumber = numberWidgetCarriage - (widthScrollArea / widthCellWithSpacing) / 2

        ui.scrollArea.horizontalScrollBar().setMaximum(widthCellWithSpacing * countCells)
        ui.scrollArea.horizontalScrollBar().setValue(leftCellNumber * widthCellWithSpacing + 3)
    }

}