package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QApplication
import com.trolltech.qt.gui.QHBoxLayout
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.forms.post.Ui_PostWorkAreaWidget
import ru.alexey_podusov.machines.engines.WorkareaTab

abstract class BaseCellsWorkarea(tab: WorkareaTab) : BaseWorkarea(tab) {
    protected val ui = Ui_PostWorkAreaWidget()
    protected val cellWidgets = ArrayList<CellBase>()

    protected var countCells: Int = 0
    protected var numberWidgetCarriage: Int = 0

    companion object {
        val SPACING_CELL_LAYOUT = 6
    }

    init {
        ui.setupUi(this)
        ui.scrollArea.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
        initCells()
    }

    protected abstract fun createCell(): CellBase
    protected abstract fun onCellChanched(numberCell: Int, cellParameter:Any)
    protected abstract fun onLeftButtonClicked()
    protected abstract fun onRightButtonClicked()

    override fun connect() {
        ui.LeftPushButton.clicked.connect(this, ::onLeftButtonClicked)
        ui.RightPushButton.clicked.connect(this, ::onRightButtonClicked)
    }

    fun initCells() {
        cellWidgets.clear()
        val scrollAreaLayout = QHBoxLayout()

        countCells = (QApplication.desktop().width() /
                (CellBase.WIDTH_CELL + SPACING_CELL_LAYOUT)) + 100

        numberWidgetCarriage = countCells / 2

        for (i: Int in 0..countCells) {
            val cell: CellBase = createCell()
            cellWidgets.add(cell)

            cell.onCellChanchedSignal.connect { num: Int, param: Any -> onCellChanched(num, param) }
            scrollAreaLayout.addWidget(cell)
        }

        ui.scrollArea.widget().setLayout(scrollAreaLayout)
        scrollAreaLayout.setSpacing(SPACING_CELL_LAYOUT)
        updateWorkArea()
        cellWidgets.get(numberWidgetCarriage).setCurrent()
    }

    override fun updateSizeWidget() {
        val widthCellWithSpacing: Int = CellBase.WIDTH_CELL + SPACING_CELL_LAYOUT
        val widthScrollArea: Int = ui.scrollArea.size().width()

        val leftCellNumber = numberWidgetCarriage - (widthScrollArea / widthCellWithSpacing) / 2

        ui.scrollArea.horizontalScrollBar().setMaximum(widthCellWithSpacing * countCells)
        ui.scrollArea.horizontalScrollBar().setValue(leftCellNumber * widthCellWithSpacing + 3)
    }

}