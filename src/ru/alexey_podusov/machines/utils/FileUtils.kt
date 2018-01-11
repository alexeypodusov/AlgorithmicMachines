package ru.alexey_podusov.machines.utils

import com.google.gson.GsonBuilder
import com.trolltech.qt.core.QByteArray
import com.trolltech.qt.core.QFile
import com.trolltech.qt.core.QIODevice
import ru.alexey_podusov.machines.MachineType
import ru.alexey_podusov.machines.MachineType.*
import ru.alexey_podusov.machines.engines.BaseEngine
import ru.alexey_podusov.machines.engines.CommandTab
import ru.alexey_podusov.machines.engines.WorkareaTab
import ru.alexey_podusov.machines.engines.post.PostEngine
import ru.alexey_podusov.machines.utils.instance_creators.CommandTabInstanceCreator
import ru.alexey_podusov.machines.utils.instance_creators.WorkareaTabInstanceCreator

class FileUtils {
    companion object {
        fun write(engine: BaseEngine, filepath: String) {
            val file = QFile(filepath)
            if (file.open(QIODevice.OpenModeFlag.WriteOnly)) {
                val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                val bytes = QByteArray().append(gson.toJson(engine))
                file.write(bytes)
                file.close()
            }

        }

        fun read(filepath: String): PostEngine? {
            val file = QFile(filepath)
            if (file.open(QIODevice.OpenModeFlag.ReadOnly)) {
                val gson = GsonBuilder()
                        .registerTypeAdapter(CommandTab::class.java, CommandTabInstanceCreator(POST))
                        .registerTypeAdapter(WorkareaTab::class.java, WorkareaTabInstanceCreator(POST))
                        .create()
                val engine = gson.fromJson<PostEngine>(file.readAll().toString(), PostEngine::class.java)

                file.close()
                return engine
            }
            return null
        }
    }
}