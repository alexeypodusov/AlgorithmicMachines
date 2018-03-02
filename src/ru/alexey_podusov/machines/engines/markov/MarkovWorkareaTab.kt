package ru.alexey_podusov.machines.engines.markov

import com.google.gson.annotations.Expose
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.WorkareaTab

class MarkovWorkareaTab(name: String) : WorkareaTab(name) {
    @Expose
    var string: String = ""
        set(value) {
            field = value
            engine?.onWorkareaChanged()
        }

    var savedString: String? = null

    var historyString: ArrayList<String>? = ArrayList()

    var detailedHistoryReplacement: ArrayList<HistoryChangesItem>? = ArrayList()

    data class HistoryChangesItem(var numberRules: Int,
                                  var sample: String,
                                  var replacement: String,
                                  var beforeString: String,
                                  var afterString: String,
                                  var startPositionChanged: Int)

    override fun saveWorkarea() {
        savedString = string
        engine?.onWorkareaChanged()
    }

    override fun restoreWorkarea() {
        if (savedString != null) {
            string = savedString!!
            engine?.onWorkareaChanged()
        }
    }

    override fun savedIsNull(): Boolean {
        return (savedString == null)
    }
}