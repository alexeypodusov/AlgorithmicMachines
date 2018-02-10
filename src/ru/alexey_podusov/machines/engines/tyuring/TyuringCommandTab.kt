package ru.alexey_podusov.machines.engines.tyuring

import ru.alexey_podusov.machines.engines.CommandTab

class TyuringCommandTab(name: String): CommandTab(name) {
    override fun getCommandsSize(): Int {
        return 0
    }
}