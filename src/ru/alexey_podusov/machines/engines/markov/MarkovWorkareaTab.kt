package ru.alexey_podusov.machines.engines.markov

import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.WorkareaTab

class MarkovWorkareaTab(name: String) : WorkareaTab(name) {
    var string: String = ""
        set(value) {
            field = value
            engine?.onWorkareaChanged()
        }
}