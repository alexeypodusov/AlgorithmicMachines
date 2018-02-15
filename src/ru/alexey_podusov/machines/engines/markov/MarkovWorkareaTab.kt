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

    var historyString = ArrayList<String>()

    override fun saveWorkarea() {
        savedString = string
    }

    override fun restoreWorkarea() {
        if (savedString != null) {
            string = savedString!!
        }
    }
}