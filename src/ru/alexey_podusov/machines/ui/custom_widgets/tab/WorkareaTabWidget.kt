package ru.alexey_podusov.machines.ui.custom_widgets.tab

import com.trolltech.qt.gui.QWidget
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.ui.BaseWorkarea

class WorkareaTabWidget: EngineTabWidget() {
    companion object {
        val DEFAULT_WORKAREA_TAB_NAME = "Лента"
        val MAX_HEIGHT = 140
    }

    init {
        setMaximumHeight(MAX_HEIGHT)
    }

    override fun addWidgetsFromEngine() {
        clear()
        engine!!.workareaTabs.forEach { addEngineTab(getNewWidget(it), it.name) }
    }

    override fun getNewWidget(tab: EngineTab): QWidget {
        return factory!!.createWorkWidget(tab as WorkareaTab)
    }

    override fun addTab() {
        val tab = engine!!.addWorkareaTab(DEFAULT_WORKAREA_TAB_NAME + " " + count().toString())
        addEngineTab(getNewWidget(tab), tab.name)
    }

    fun getCurrent(): BaseWorkarea {
        return currentWidget() as BaseWorkarea
    }
}