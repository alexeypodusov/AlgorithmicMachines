package ru.alexey_podusov.machines.engines.post

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CellsWorkareaTab
import ru.alexey_podusov.machines.engines.WorkareaTab

class PostWorkareaTab(name: String) : CellsWorkareaTab(name) {
    @Expose
    var cells = ArrayList<Boolean>()

    var savedCells: ArrayList<Boolean>? = null

    init {
        for (i in 0 until COUNT_CELLS) {
            cells.add(false)
        }
    }

    fun getCell(numCell: Int): Boolean {
        if (isInTape(numCell)) {
            val cellIndex = getIndexByNum(numCell)
            return cells.get(cellIndex)
        }

        return false
    }

    fun changeValueCell(numCell: Int, cellValue: Boolean) {
        if (isInTape(numCell)) {
            val cellIndex = getIndexByNum(numCell)
            cells.set(cellIndex, cellValue)
            engine?.onWorkareaChanged()
        }
    }

    override fun saveWorkarea() {
        super.saveWorkarea()
        savedCells?.clear()
        savedCells = ArrayList()
        for (cell in cells) {
            savedCells?.add(cell)
        }
        engine?.onWorkareaChanged()
    }

    override fun restoreWorkarea() {
        super.restoreWorkarea()
        if (savedCells != null) {
            cells.clear()
            for (savedCell in savedCells!!) {
                cells.add(savedCell)
            }
            engine?.onWorkareaChanged()
        }
    }

    override fun savedIsNull(): Boolean {
        return savedCells == null
    }
}