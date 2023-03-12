package me.santio.utils.nms.types.player.properties

import me.santio.utils.reflection.reflection
import java.security.PublicKey
import java.util.*

class PropertyNMS(private val property: Any) {

    fun get() = property
    fun name(): String = property.reflection().field("name")!!.value() as String
    fun value(): String = property.reflection().field("value")!!.value() as String
    fun signature(): String? = property.reflection().field("signature")!!.value() as String?
    fun hasSignature() = signature()?.isNotEmpty() ?: false
    fun isSignatureValid(key: PublicKey): Boolean = property.reflection().method("isSignatureValid", false)!!.invoke(key) as Boolean
    fun decoded() = Base64.getDecoder().decode(value()).toString(Charsets.UTF_8)

}