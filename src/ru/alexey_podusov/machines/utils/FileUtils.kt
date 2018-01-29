package ru.alexey_podusov.machines.utils

import com.google.gson.GsonBuilder
import com.trolltech.qt.core.*
import java.nio.charset.Charset

class FileUtils {
    companion object {
        fun writeObject(obj: Any, filepath: String) {
            val file = QFile(filepath)
            if (file.open(QIODevice.OpenModeFlag.WriteOnly)) {
                val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                val bytes: ByteArray = gson.toJson(obj).toByteArray()
                file.write(bytes)
                file.close()
            }
        }

        fun read(filepath: String): String {
            val file = QFile(filepath)
            var result: String
            if (file.open(QIODevice.OpenModeFlag.ReadOnly)) {
                result = String(file.readAll().toByteArray(), Charset.defaultCharset())
                file.close()
                return result
            } else return ""
        }
    }
}