// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.Int

public enum class TSQuantifier(
    public val `value`: Int,
) {
    Zero(0),
    ZeroOrOne(1),
    ZeroOrMore(2),
    One(3),
    OneOrMore(4),
    ;

    public companion object {
        @JvmStatic
        public val fromInt: MethodHandle = MethodHandles.lookup().findStatic(
            TSQuantifier::class.java,
            "fromInt",
            MethodType.methodType(TSQuantifier::class.java, Int::class.java)
        )

        @JvmStatic
        public val toInt: MethodHandle = MethodHandles.lookup().findGetter(
            TSQuantifier::class.java,
            "value",
            Int::class.java
        )

        @JvmStatic
        public fun fromInt(`value`: Int): TSQuantifier = when (value) {
            Zero.value -> Zero
            ZeroOrOne.value -> ZeroOrOne
            ZeroOrMore.value -> ZeroOrMore
            One.value -> One
            OneOrMore.value -> OneOrMore
            else -> error("enum not found")
        }
    }
}