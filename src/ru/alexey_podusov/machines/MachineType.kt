package ru.alexey_podusov.machines

enum class MachineType(val nameMachine:String, val fileFormat: String) {
    POST("Машина Поста", "amp"),
    MARKOV("Нормальные алгоритмы Маркова", "nam"),
    TYURING("Машина Тьюринга", "amt");

    companion object {
        fun getTypeByFileFormat(fileFormat: String) : MachineType {
            return values().filter { fileFormat.equals(it.fileFormat) }.first()
        }
    }
}