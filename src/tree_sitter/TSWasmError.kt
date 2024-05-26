// this file is auto generated by endoqa kotlin ffi, modify it with caution
package tree_sitter

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout
import java.lang.invoke.VarHandle
import kotlin.Byte
import kotlin.Int
import kotlin.jvm.JvmField
import kotlin.jvm.JvmInline

@JvmInline
public value class TSWasmError(
    public val `$mem`: MemorySegment,
) {
    public var kind: TSWasmErrorKind
        get() = TSWasmErrorKind.fromInt(TSWasmError.kindHandle.get(this.`$mem`) as Int)
        set(`value`) {
            TSWasmError.kindHandle.set(this.`$mem`, value.value)
        }

    public var message: Pointer<Byte>
        get() = TSWasmError.messageHandle.get(this.`$mem`) as MemorySegment
        set(`value`) {
            TSWasmError.messageHandle.set(this.`$mem`, value)
        }

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("kind"),
            java.lang.foreign.MemoryLayout.paddingLayout(4),
            `$RuntimeHelper`.POINTER.withName("message"),
        ).withName("TSWasmError")

        @JvmField
        public val kindHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("kind"))

        @JvmField
        public val messageHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("message"))
    }
}