package ru.alexey_podusov.machines.ui.custom_widgets.tab

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect

class EngineTabBar(val parent: EngineTabWidget) : QTabBar(parent) {
    val lineEdit = QLineEdit(this)
    var indexTabEdited = 0

    init {
        
        lineEdit.hide()
        lineEdit.editingFinished.connect(this, ::onEditingFinished)
    }

    internal fun showLineEdit(tabIndex: Int) {
        try {
            lineEdit.setText(tabText(tabIndex))
            lineEdit.setGeometry(tabRect(tabIndex))
            lineEdit.show()
            lineEdit.setFocus()
            indexTabEdited = tabIndex
        } catch (e: NullPointerException) {
        }

    }

    fun onEditingFinished() {
        lineEdit.hide()
        setTabText(indexTabEdited, lineEdit.text())
        parent.onTabTitleEdited(indexTabEdited, lineEdit.text())
    }

    override fun mouseDoubleClickEvent(arg__1: QMouseEvent?) {
        super.mouseDoubleClickEvent(arg__1)
        for (i in 0 until count()) {
            try {
                if (tabRect(i).contains(arg__1!!.x(), arg__1.y())) {
                    showLineEdit(i)
                    return
                }
            } catch (e: NullPointerException) {
            }
        }
    }

}