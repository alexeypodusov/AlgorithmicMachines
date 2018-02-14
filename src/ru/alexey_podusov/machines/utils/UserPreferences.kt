package ru.alexey_podusov.machines.utils

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

class UserPreferences private constructor() {
    enum class Speed(var milliseconds: Int){
        VERY_HIGHT(100),
        HIGHT(250),
        MIDLE(500),
        LOW(750),
        VERY_LOW(1000)
    }

    @Expose
    var speed: Speed = Speed.MIDLE
    @Expose
    var autoDeleteEmptyCommands = true
    @Expose
    var finalSymbolMarkov = ".!"

    companion object {
        private val FILENAME = "preferences.json"

        val instance: UserPreferences by lazy { init() }

        private fun init(): UserPreferences {
            try {
                val prefJsonString = FileUtils.read(FILENAME)
                return GsonBuilder()
                        .create()
                        .fromJson<UserPreferences>(prefJsonString, UserPreferences::class.java)
            } catch ( e: Exception) {
                return UserPreferences()
            }
        }
    }

    fun save() {
        FileUtils.writeObject(this, FILENAME)
    }

}