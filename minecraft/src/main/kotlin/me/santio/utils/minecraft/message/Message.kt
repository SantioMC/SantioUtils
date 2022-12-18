package me.santio.utils.minecraft.message

@Suppress("unused")
class Message() {

    private var text: String = ""
    private var hover: String? = null
    private var click: Pair<ClickAction, String>? = null
    private val children: MutableList<Message> = mutableListOf()

    constructor(text: String): this() {
        this.text = text
    }

    fun text(text: String): Message {
        this.text = text
        return this
    }

    fun hover(text: String): Message {
        this.hover = text
        return this
    }

    fun command(command: String): Message {
        this.click = Pair(ClickAction.RUN_COMMAND, text)
        return this
    }

    fun suggest(command: String): Message {
        this.click = Pair(ClickAction.SUGGEST_COMMAND, text)
        return this
    }

    fun url(url: String): Message {
        this.click = Pair(ClickAction.OPEN_URL, text)
        return this
    }

    fun add(text: Message): Message {
        this.children.add(text)
        return this
    }

    fun text(): String {
        return text
    }

    fun hover(): String? {
        return hover
    }

    fun click(): Pair<ClickAction, String>? {
        return click
    }

    fun children(): List<Message> {
        return children
    }

    enum class ClickAction {
        OPEN_URL,
        RUN_COMMAND,
        SUGGEST_COMMAND
    }

}