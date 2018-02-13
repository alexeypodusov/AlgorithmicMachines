package ru.alexey_podusov.machines.engines.tyuring

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.tyuring.TyuringEngine.TyuringCommand

class TyuringCommandTab(name: String) : CommandTab(name) {
    companion object {
        val MAX_STATES = 999
    }

    @Expose
    var commands = ArrayList<ArrayList<TyuringCommand>>()

    override fun insertCommand(columnNum: Int) {
        if (columnNum < MAX_STATES) {
            for (row in commands.withIndex()) {
                row.value.filter { it.numberColumn >= columnNum }.forEach { it.numberColumn++ }
                row.value.filter { it.newState >= columnNum }.forEach {  it.newState++ }
                row.value.add(columnNum, TyuringCommand(numberColumn = columnNum, numberRow = row.index))
            }
            engine?.commandsChanged()
        }
    }

    override fun removeCommand(columnNum: Int) {
        if (commands.get(0).size != 1) {
            for (row in commands.withIndex()) {
                row.value.filter { it.numberColumn > columnNum }.forEach { it.numberColumn-- }
                row.value.filter { it.newState == columnNum }.forEach {  it.newState = -1}
                row.value.removeAt(columnNum)
            }
        }
    }

    fun changeCommand(command: TyuringCommand) {
        commands.get(command.numberRow).set(command.numberColumn, command)
        engine?.commandsChanged()
    }

    fun insertRow(rowNum: Int) {
        val countColumns = commands.get(0).size
        val newRow = ArrayList<TyuringCommand>()

        for(i in 0 until countColumns) {
            commands.filter { it.get(i).numberRow >= rowNum }.forEach { it.get(i).numberRow++ }
            newRow.add(TyuringCommand(numberColumn = i, numberRow = rowNum))
        }
        commands.add(rowNum, newRow)

        engine?.commandsChanged()
    }

    init {
        val row = ArrayList<TyuringCommand>()
        commands.add(row)
        insertCommand(0)
    }

    fun getColumnsCount(): Int {
        return commands.get(0).size
    }

    fun getRowsCount(): Int {
        return  commands.size
    }

    override fun getCommandsSize(): Int {
        return 0
    }
}