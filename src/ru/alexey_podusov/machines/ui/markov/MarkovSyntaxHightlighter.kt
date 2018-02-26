package ru.alexey_podusov.machines.ui.markov

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.engines.markov.MarkovEngine.MarkovCommand
import ru.alexey_podusov.machines.utils.UserPreferences

class MarkovSyntaxHightlighter(document: QTextDocument) : QSyntaxHighlighter(document) {
    private val numberCommandFormat = QTextCharFormat()
    private val sampleFormat = QTextCharFormat()
    private val arrowFormat = QTextCharFormat()
    private val replacementFormat = QTextCharFormat()
    private val endSymbolFormat = QTextCharFormat()

    data class MarkovCommandSyntaxField(var index: Int = -1, var length: Int = -1)
    data class MarkovCommandSyntax(var command: MarkovCommand = MarkovCommand(number = -1),
                                   var numberField: MarkovCommandSyntaxField = MarkovCommandSyntaxField(),
                                   var sampleField: MarkovCommandSyntaxField = MarkovCommandSyntaxField(),
                                   var arrowField: MarkovCommandSyntaxField = MarkovCommandSyntaxField(),
                                   var replacementField: MarkovCommandSyntaxField = MarkovCommandSyntaxField(),
                                   var endSymbolField: MarkovCommandSyntaxField = MarkovCommandSyntaxField())

    init {
        numberCommandFormat.setForeground(QBrush(QColor(QColor.fromRgb(0, 0, 128))))
        numberCommandFormat.setFontWeight(QFont.Weight.Bold.value())

        sampleFormat.setForeground(QBrush(QColor(QColor.fromRgb(0, 128, 64))))
        sampleFormat.setFontWeight(QFont.Weight.Bold.value())

        arrowFormat.setForeground(QBrush(QColor(QColor.fromRgb(101, 64, 145))))
        arrowFormat.setFontWeight(QFont.Weight.Bold.value())

        replacementFormat.setForeground(QBrush(QColor(QColor.fromRgb(0, 128, 128))))
        replacementFormat.setFontWeight(QFont.Weight.Bold.value())

        endSymbolFormat.setForeground(QBrush(QColor(QColor.red)))
        endSymbolFormat.setFontWeight(QFont.Weight.Bold.value())
    }

    companion object {
        val REGEX_NUMBER_COMMAND = Regex("\\d{1,3}|\\d{1,3}\\)|\\d{1,3}\\.")
        val REGEX_NOT_NUMBER = Regex("\\D")

        private fun getNumberCommandByString(text: String): Int {
            if (text.matches(REGEX_NUMBER_COMMAND)) {
                return text.replace(REGEX_NOT_NUMBER, "").toInt()
            }
            return -1
        }

        private fun getArrowByString(text: String): String {
            if (text == "->" || text == ">" || text == "→") return text
            return ""
        }

        fun getCommandByString(text: String?): MarkovCommandSyntax? {
            if (text == null) return null

            var textWithoutAddedFields = text
            var startIndexForSearchField = 0

            val fields = text.split(" ")

            if (fields.isEmpty()) return null


            val commandSyntax = MarkovCommandSyntax()
            val command = commandSyntax.command

            var arrow = ""

            val sampleFields = ArrayList<String>()
            val replaceFields = ArrayList<String>()

            for (field in fields) {
                if (command.number == -1 && sampleFields.isEmpty() && replaceFields.isEmpty() && commandSyntax.arrowField.index == -1) {
                    if (field.isEmpty()) continue
                    val number = getNumberCommandByString(field)
                    if (number != -1) {
                        command.number = number
                        commandSyntax.numberField.index = textWithoutAddedFields.indexOf(field, startIndexForSearchField)
                        commandSyntax.numberField.length = field.length
                        startIndexForSearchField = commandSyntax.numberField.index + commandSyntax.numberField.length
                        textWithoutAddedFields.replaceFirst(field, "")
                        continue
                    }
                }

                if (commandSyntax.arrowField.index == -1 && replaceFields.isEmpty()) {
                    arrow = getArrowByString(field)
                    if (!arrow.isEmpty()) {
                        commandSyntax.arrowField.index = textWithoutAddedFields.indexOf(field, startIndexForSearchField)
                        commandSyntax.arrowField.length = field.length
                        continue
                    }
                }

                if (commandSyntax.arrowField.index == -1) {
                    sampleFields.add(field)
                    continue
                }

                if (commandSyntax.arrowField.index != -1) {
                    replaceFields.add(field)
                    continue
                }
            }

            command.sample = sampleFields.joinToString(" ")
            commandSyntax.sampleField.index = textWithoutAddedFields.indexOf(command.sample, startIndexForSearchField)
            commandSyntax.sampleField.length = command.sample.length
            startIndexForSearchField = commandSyntax.sampleField.index + commandSyntax.sampleField.length
            textWithoutAddedFields.replaceFirst(command.sample, "")

            if (!arrow.isEmpty()) {
                startIndexForSearchField = commandSyntax.arrowField.index + commandSyntax.arrowField.length
                textWithoutAddedFields.replaceFirst(arrow, "")
            }

            command.replacement = replaceFields.joinToString(" ")
            commandSyntax.replacementField.index = textWithoutAddedFields.indexOf(command.replacement, startIndexForSearchField)
            commandSyntax.replacementField.length = command.replacement.length


            val symbolEnd = UserPreferences.instance.finalSymbolMarkov
            if (command.replacement.length >= symbolEnd.length &&
                    command.replacement.takeLast(symbolEnd.length) == symbolEnd) {
                commandSyntax.replacementField.length = command.replacement.length - symbolEnd.length
                commandSyntax.endSymbolField.length = symbolEnd.length
                commandSyntax.endSymbolField.index = commandSyntax.replacementField.index + commandSyntax.replacementField.length
            }

            return commandSyntax

        }
    }

    override fun highlightBlock(text: String?) {
        val commandSyntax = getCommandByString(text)

        if (commandSyntax != null) {
            if (commandSyntax.numberField.index != -1) {
                setFormat(commandSyntax.numberField.index, commandSyntax.numberField.length, numberCommandFormat)
            }

            if (commandSyntax.sampleField.index != -1) {
                setFormat(commandSyntax.sampleField.index, commandSyntax.sampleField.length, sampleFormat)
            }

            if (commandSyntax.arrowField.index != -1) {
                setFormat(commandSyntax.arrowField.index, commandSyntax.arrowField.length, arrowFormat)
            }

            if (commandSyntax.replacementField.index != -1) {
                setFormat(commandSyntax.replacementField.index, commandSyntax.replacementField.length, replacementFormat)
            }

            if (commandSyntax.endSymbolField.index != -1) {
                setFormat(commandSyntax.endSymbolField.index, commandSyntax.endSymbolField.length, endSymbolFormat)
            }
        }
    }
}