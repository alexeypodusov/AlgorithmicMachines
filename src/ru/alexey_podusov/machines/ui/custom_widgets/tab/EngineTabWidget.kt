package ru.alexey_podusov.machines.ui.custom_widgets.tab

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.EngineTab
import ru.alexey_podusov.machines.factories.IFactory

abstract class EngineTabWidget : QTabWidget() {
    val tabTitleEditedSignal = Signal2<Int, String>()
    val addTabClicked = Signal0()

    val tabBar = EngineTabBar(this)


    protected var engine: BaseEngine? = null
    protected var factory: IFactory? = null

    init {
        setTabBar(tabBar)
        setTabsClosable(true)
        addAddingTab()

        tabCloseRequested.connect(this, ::onDeleteTab)
        currentChanged.connect(this, ::onCurrentChanged)
    }

    fun setEngine(engine: BaseEngine, factory: IFactory) {
        this.engine = engine
        this.factory = factory

        blockSignals(true)

        addWidgetsFromEngine()

        blockSignals(false)
        addAddingTab()

        setCurrentIndex(0)
    }

    abstract fun addWidgetsFromEngine()
    abstract fun addTab()
    abstract fun getNewWidget(tab: EngineTab): QWidget
    abstract fun removeTabFromEngine(index: Int)

    private fun onDeleteTab(index: Int) {
        if (count() > 2) {
            tabBar.lineEdit.hide()
            widget(index).disconnect()
            widget(index).hide()
            removeTabFromEngine(index)

            if (index == count() - 2) setCurrentIndex(index - 1)
            removeTab(index)
        }
    }

    private fun onCurrentChanged(index: Int) {
        tabBar.lineEdit.hide()
        if (index == tabBar.count() - 1) {
            //добавление новой вкладки
            addTabClicked.emit()
            addTab()
            setCurrentIndex(count() - 2)
            tabBar.showLineEdit(count() - 2)
        }
    }

    protected abstract fun renameTab(index: Int, text: String)

    internal fun onTabTitleEdited(index: Int, text: String) {
        tabTitleEditedSignal.emit(index, text)
        renameTab(index, text)
    }

    private fun addAddingTab() {
        addTab(QLabel(), "")
        tabBar.setTabButton(count() - 1, QTabBar.ButtonPosition.RightSide, null)
    }

    protected fun addEngineTab(widget: QWidget, title: String) {
        insertTab( count() - 1, widget, title)
    }
}

