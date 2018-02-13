package ru.alexey_podusov.machines.engines.tyuring

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.CellsWorkareaTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.post.PostWorkareaTab

class TyuringWorkareaTab(name: String): CellsWorkareaTab(name) {
    @Expose
    var cells = ArrayList<String>()

    init {
        for (i in 0 until COUNT_CELLS) {
            cells.add("")
        }
    }

    fun changeValueCell(numCell: Int, cellValue: String) {
        if (isInTape(numCell)) {
            val cellIndex = getIndexByNum(numCell)
            cells.set(cellIndex, cellValue)
            engine?.onWorkareaChanged()
        }
    }

    fun getCell(numCell: Int): String {
        if (isInTape(numCell)) {
            val cellIndex = getIndexByNum(numCell)
            return cells.get(cellIndex)
        }
        return ""
    }

    fun deleteSymbolFromCells(symbol: String) {
        for ((i,cell) in cells.withIndex()) {
            if (cell == symbol) cells.set(i, "")
        }
    }

}