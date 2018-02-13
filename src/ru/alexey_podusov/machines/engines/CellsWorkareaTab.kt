package ru.alexey_podusov.machines.engines

import com.google.gson.annotations.Expose

abstract class CellsWorkareaTab(name: String) : WorkareaTab(name) {
    @Expose
    var currentCarriage: Int = 0 //not index! fact number cell
        set(value) {
            if (isInTape(value)) {
                field = value
                engine?.onWorkareaChanged()
            }
        }

    companion object {
        val COUNT_CELLS = 2000
        fun isInTape(number: Int): Boolean = number in -((COUNT_CELLS / 2) - 1)..((COUNT_CELLS / 2) - 1)
    }

    private fun isIndexInTape(index: Int): Boolean {
        return index in 0..COUNT_CELLS
    }

    internal fun getIndexByNum(num: Int): Int = num + ((COUNT_CELLS / 2) - 1)


}