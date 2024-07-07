// this file is auto generated by endoqa kotlin ffi, modify it with caution
package tree_sitter.c

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class TSInputEncoding(
    public val `value`: Int,
) {
    TSInputEncodingUTF8(0x00000000),
    TSInputEncodingUTF16(0x00000001),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            TSInputEncoding::class.java,
            "fromInt",
            MethodType.methodType(TSInputEncoding::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            TSInputEncoding::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): TSInputEncoding = when (value) {
            0x00000000 -> TSInputEncodingUTF8
            0x00000001 -> TSInputEncodingUTF16
            else -> error("enum not found")
        }
    }
}
