package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.gui.*
import ru.alexey_podusov.machines.engines.post.PostCommandTab
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommand
import ru.alexey_podusov.machines.engines.post.PostEngine.PostCommandType

class PostSyntaxHightlighter(document: QTextDocument) : QSyntaxHighlighter(document) {
    private val numberCommandFormat = QTextCharFormat()
    private val typeCommandFormat = QTextCharFormat()
    private val transitionCommandFormat = QTextCharFormat()
    private val secondTransitionCommandFormat = QTextCharFormat()
    private val errorCommandFormat = QTextCharFormat()

    val commands = ArrayList<PostCommand>()

    data class PostCommandSyntaxField(var index: Int = -1, var length: Int = -1)
    data class PostCommandSyntax(var command: PostCommand,
                                 var numberField: PostCommandSyntaxField = PostCommandSyntaxField(),
                                 var commandTypeField: PostCommandSyntaxField = PostCommandSyntaxField(),
                                 var transitionField: PostCommandSyntaxField = PostCommandSyntaxField(),
                                 var secondTransitionField: PostCommandSyntaxField = PostCommandSyntaxField())

    init {
        numberCommandFormat.setForeground(QBrush(QColor(QColor.fromRgb(0,0,128))))
        numberCommandFormat.setFontWeight(QFont.Weight.Bold.value())

        typeCommandFormat.setForeground(QBrush(QColor(QColor.fromRgb(101,64,145))))
        typeCommandFormat.setFontWeight(QFont.Weight.Bold.value())

        transitionCommandFormat.setForeground(QBrush(QColor(QColor.fromRgb(0,128,64))))
        transitionCommandFormat.setFontWeight(QFont.Weight.Bold.value())

        secondTransitionCommandFormat.setForeground(QBrush(QColor(QColor.fromRgb(0,128,128))))
        secondTransitionCommandFormat.setFontWeight(QFont.Weight.Bold.value())

        errorCommandFormat.setForeground(QBrush(QColor(QColor.red)))
        errorCommandFormat.setFontUnderline(true)
    }

    companion object {
        val REGEX_NUMBER_COMMAND = Regex("\\d{1,3}|\\d{1,3}\\)|\\d{1,3}\\.")
        val REGEX_NOT_NUMBER = Regex("\\D")
        val REGEX_IS_SECOND_TRANSITION = Regex("[:;]\\d{1,3}")

        private fun getCommandTypeByString(text: String): PostCommandType? {
            val fieldTypes = PostCommandType.values()
                    .filter { it.shortText.equals(text, true) }

            return if (fieldTypes.count() != 0) {
                fieldTypes.first()
            } else null
        }

        private fun getTransitionByString(text: String): Int {
            val transition = text.toIntOrNull()
            if (transition != null && transition in 0..PostCommandTab.MAX_COMMANDS) {
                return transition
            }
            return -1
        }

        private fun getSecondTransitionByString(text: String): Int {
            if (text.matches(REGEX_IS_SECOND_TRANSITION)) {
                val transition = text.replace(REGEX_NOT_NUMBER, "")
                return transition.toInt()
            }
            return -1
        }

        fun getCommandByString(text: String?): PostCommandSyntax? {
            if (text == null) return null

            var textWithoutAddedFields = text
            var startIndexForSearchField = 0

            val fields = text.split(" ").filter { !it.isEmpty() }

            if (fields.isEmpty() || !fields.get(0).matches(REGEX_NUMBER_COMMAND)) return null

            val command = PostCommand(number = fields.get(0).replace(REGEX_NOT_NUMBER, "").toInt())
            val commandSyntax = PostCommandSyntax(command)
            commandSyntax.numberField.index = textWithoutAddedFields.indexOf(fields.get(0))
            commandSyntax.numberField.length = fields.get(0).length
            textWithoutAddedFields.replaceFirst(fields.get(0), "")
            startIndexForSearchField = commandSyntax.numberField.index + commandSyntax.numberField.length


            if (fields.size >= 2) {
                val commentFields = ArrayList<String>()

                fields as ArrayList<String>
                fields.removeAt(0)
                for (s in fields) {
                    //если коментария еще нет
                    if (commentFields.isEmpty()) {
                        if (command.commandType == PostCommandType.NULL_COMMAND && command.transition == -1 && command.secondTransition == -1) {
                            val commandType = getCommandTypeByString(s)
                            if (commandType != null) {
                                command.commandType = commandType
                                commandSyntax.commandTypeField.index = textWithoutAddedFields.indexOf(s, startIndexForSearchField)
                                commandSyntax.commandTypeField.length = s.length
                                startIndexForSearchField = commandSyntax.commandTypeField.index + commandSyntax.commandTypeField.length
                                textWithoutAddedFields.replaceFirst(s,"")
                                continue
                            }
                        }

                        if (command.transition == -1 && command.secondTransition == -1) {
                            val transition = getTransitionByString(s)
                            if (transition != -1) {
                                command.transition = transition
                                commandSyntax.transitionField.index = textWithoutAddedFields.indexOf(s, startIndexForSearchField)
                                commandSyntax.transitionField.length = s.length
                                startIndexForSearchField = commandSyntax.transitionField.index + commandSyntax.transitionField.length
                                textWithoutAddedFields.replaceFirst(s,"")
                                continue
                            }
                        }

                        if (command.commandType == PostCommandType.CHECK_MARK && command.secondTransition == -1) {
                            val transition = getSecondTransitionByString(s)
                            if (transition != -1) {
                                command.secondTransition = transition
                                commandSyntax.secondTransitionField.index = textWithoutAddedFields.indexOf(s, startIndexForSearchField)
                                commandSyntax.secondTransitionField.length = s.length
                                startIndexForSearchField = commandSyntax.secondTransitionField.index + commandSyntax.secondTransitionField.length
                                textWithoutAddedFields.replaceFirst(s,"")
                                continue
                            }
                        }
                    }
                    commentFields.add(s)
                }

                command.comment = commentFields.joinToString(" ")
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

            if (commandSyntax.commandTypeField.index != -1) {
                setFormat(commandSyntax.commandTypeField.index, commandSyntax.commandTypeField.length, typeCommandFormat)
            }

            if (commandSyntax.transitionField.index != -1) {
                setFormat(commandSyntax.transitionField.index, commandSyntax.transitionField.length, transitionCommandFormat)
            }

            if (commandSyntax.secondTransitionField.index != -1) {
                setFormat(commandSyntax.secondTransitionField.index, commandSyntax.secondTransitionField.length, secondTransitionCommandFormat)
            }
        } else {
            setFormat(0, text!!.length, errorCommandFormat)
        }
    }
}