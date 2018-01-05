package ru.alexey_podusov.machines.ui.custom_widgets.tab

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.connect
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.factories.IFactory

class EngineTabWidget(parent: QWidget, engine: BaseEngine?, factory: IFactory) : QTabWidget(parent) {
    val tabTitleEditedSignal = Signal2<Int, String>()
    val addTabClicked = Signal0()

    val tabBar = EngineTabBar(this)

    init {
        setTabBar(tabBar)
        setTabsClosable(false)
        addAddingTab()
        currentChanged.connect(this, ::onCurrentChanged)
    }

    private fun onCurrentChanged(index: Int) {
        if (index == tabBar.count() - 1) {
            addTabClicked.emit()
            setCurrentIndex(tabBar.count() - 2)
            tabBar.showLineEdit(tabBar.count() - 2)
        }
    }

    internal fun onTabTitleEdited(index: Int, text: String) {
        tabTitleEditedSignal.emit(index, text)
    }

    private fun addAddingTab() {
        addTab(QLabel(), "")
    }

    fun addEngineTab(widget: QWidget, title: String) {
        insertTab( tabBar.count() - 2, widget, "")
    }
}

