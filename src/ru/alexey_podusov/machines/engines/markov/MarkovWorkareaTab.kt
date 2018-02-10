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

    var historyString = ArrayList<String>()
}