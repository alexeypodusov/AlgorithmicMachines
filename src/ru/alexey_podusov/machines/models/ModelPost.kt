package ru.alexey_podusov.machines.models

class ModelPost : ModelBase() {
    var currentCarriage: Int = 0
        set(value) {
            if (isInTape(value)) {
                field = value
            }
            workAreaChangedSignal.emit()
        }

    var cellsList = ArrayList<Boolean>()

    companion object {
        val COUNT_CELLS = 2000
        fun isInTape(number: Int): Boolean = number in -((COUNT_CELLS / 2) - 1)..((COUNT_CELLS / 2) - 1)
    }

    init {
        for (i in 0..COUNT_CELLS) {
            cellsList.add(false)
        }
    }

    fun getCell(numCell: Int): Boolean {
        if (isInTape(numCell)) {
            val cellIndex = getIndexByNum(numCell)
            return cellsList.get(cellIndex)
        }

        return false
    }

    fun changeValueCell(numCell: Int, cellValue: Boolean) {
        if (isInTape(numCell)) {
            val cellIndex = getIndexByNum(numCell)
            cellsList.set(cellIndex, cellValue)
            workAreaChangedSignal.emit()
        }
    }

    private fun getIndexByNum(num: Int): Int = num + ((COUNT_CELLS / 2) - 1)

}