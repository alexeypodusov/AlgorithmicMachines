package ru.alexey_podusov.machines.ui

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.CellsWorkareaTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.forms.post.Ui_PostWorkAreaWidget

abstract class BaseCellsWorkarea(tab: WorkareaTab) : BaseWorkarea(tab) {
    protected val ui = Ui_PostWorkAreaWidget()
    protected val cellWidgets = ArrayList<BaseCell>()

    protected var countCells: Int = 0
    protected var numberWidgetCarriage: Int = 0

    companion object {
        val SPACING_CELL_LAYOUT = 6
        val ICON_GO_LEFT = QIcon("res/icons/ic_go_left.png")
        val ICON_GO_RIGHT = QIcon("res/icons/ic_go_right.png")
    }

    init {
        ui.setupUi(this)

        initUI()
        initCells()
    }

    private fun initUI() {
        ui.LeftPushButton.setIcon(ICON_GO_LEFT)
        ui.RightPushButton.setIcon(ICON_GO_RIGHT)

        ui.scrollArea.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
        ui.restoreButton.clicked.connect(this, ::onRestoreButtonClicked)
        ui.goToLineEdit.setValidator(QIntValidator(-999, 999))
        ui.goToLineEdit.textEdited.connect(this, ::onGoToTextEdited)
    }

    private fun onGoToTextEdited(text: String) {
        if (!text.isEmpty()) {
            (tab as CellsWorkareaTab).currentCarriage = text.toInt()
        } else {
            (tab as CellsWorkareaTab).currentCarriage = 0
        }
    }

    override fun updateWorkArea() {
        ui.restoreButton.isEnabled = !tab.savedIsNull()
        if ((tab as CellsWorkareaTab).currentCarriage != 0) {
            ui.goToLineEdit.setText(tab.currentCarriage.toString())
        } else {
            ui.goToLineEdit.setText("")
        }
    }

    protected abstract fun createCell(): BaseCell
    protected abstract fun onCellChanched(numberCell: Int, cellParameter: Any)

    private fun onRestoreButtonClicked() {
        tab.restoreWorkarea()
    }

    protected fun onLeftButtonClicked() {
        (tab as CellsWorkareaTab).currentCarriage--
    }

    protected fun onRightButtonClicked() {
        (tab as CellsWorkareaTab).currentCarriage++
    }

    override fun connect() {
        ui.LeftPushButton.clicked.connect(this, ::onLeftButtonClicked)
        ui.RightPushButton.clicked.connect(this, ::onRightButtonClicked)
    }

    fun initCells() {
        cellWidgets.clear()
        val scrollAreaLayout = QHBoxLayout()

        countCells = (QApplication.desktop().width() /
                (BaseCell.WIDTH_CELL + SPACING_CELL_LAYOUT)) + 100

        numberWidgetCarriage = countCells / 2

        for (i: Int in 0..countCells) {
            val cell: BaseCell = createCell()
            cellWidgets.add(cell)

            cell.onCellChanchedSignal.connect(this, ::onCellChanched)
            scrollAreaLayout.addWidget(cell)
        }

        ui.scrollArea.widget().setLayout(scrollAreaLayout)
        scrollAreaLayout.setSpacing(SPACING_CELL_LAYOUT)
        updateWorkArea()
        cellWidgets.get(numberWidgetCarriage).setCurrent()
    }

    override fun updateSizeWidget() {
        val widthCellWithSpacing: Int = BaseCell.WIDTH_CELL + SPACING_CELL_LAYOUT
        val widthScrollArea: Int = ui.scrollArea.size().width()

        val leftCellNumber = numberWidgetCarriage - (widthScrollArea / widthCellWithSpacing) / 2

        ui.scrollArea.horizontalScrollBar().setMaximum(widthCellWithSpacing * countCells)
        ui.scrollArea.horizontalScrollBar().setValue(leftCellNumber * widthCellWithSpacing + 3)
    }

}