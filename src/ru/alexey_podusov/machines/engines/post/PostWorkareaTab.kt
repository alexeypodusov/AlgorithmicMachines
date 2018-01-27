package ru.alexey_podusov.machines.engines.post

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.WorkareaTab

class PostWorkareaTab(name: String) : WorkareaTab(name) {

    @Expose
    var cells = ArrayList<Boolean>()

    override fun setMainEngine(engine: BaseEngine) {
        this.engine = engine
    }

    var currentCarriage: Int = 0 //not index! fact number cell
        set(value) {
            if (isInTape(value)) {
                field = value
                engine!!.onWorkareaChanged()
            }
        }


    companion object {
        val COUNT_CELLS = 2000
        fun isInTape(number: Int): Boolean = number in -((COUNT_CELLS / 2) - 1)..((COUNT_CELLS / 2) - 1)
    }

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
            engine!!.onWorkareaChanged()
        }
    }


    private fun isIndexInTape(index: Int): Boolean {
        return index in 0..COUNT_CELLS
    }

    internal fun getIndexByNum(num: Int): Int = num + ((COUNT_CELLS / 2) - 1)
}