// this file is auto generated by endoqa kotlin ffi, modify it with caution
package tree_sitter.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class TSLogType(
    public val `value`: Int,
) {
    TSLogTypeParse(0x00000000),
    TSLogTypeLex(0x00000001),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            TSLogType::class.java,
            "fromInt",
            MethodType.methodType(TSLogType::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            TSLogType::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): TSLogType = when (value) {
            0x00000000 -> TSLogTypeParse
            0x00000001 -> TSLogTypeLex
            else -> error("enum not found")
        }
    }
}
