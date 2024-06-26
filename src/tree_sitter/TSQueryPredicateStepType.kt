// this file is auto generated by endoqa kotlin ffi, modify it with caution
package tree_sitter

import kotlin.Int
import tree_sitter.TSQueryPredicateStepType.Capture
import tree_sitter.TSQueryPredicateStepType.Done
import tree_sitter.TSQueryPredicateStepType.String

public enum class TSQueryPredicateStepType(
    public val `value`: Int,
) {
    Done(0x00000000),
    Capture(0x00000001),
    String(0x00000002),
    ;

    public companion object {
        public fun fromInt(`value`: Int): TSQueryPredicateStepType = when (value) {
            0x00000000 -> Done
            0x00000001 -> Capture
            0x00000002 -> String
            else -> error("enum not found")
        }
    }
}
