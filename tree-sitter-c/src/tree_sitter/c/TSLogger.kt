// this file is auto generated by endoqa kotlin ffi, modify it with caution
package tree_sitter.c

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.invoke.VarHandle

@JvmInline
public value class TSLogger(
    public val `$mem`: MemorySegment,
) {
    public var payload: Pointer<Unit>
        get() = TSLogger.payloadHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            TSLogger.payloadHandle.set(this.`$mem`, 0L, value)
        }

    public var log: Pointer<(
        payload: Pointer<Unit>,
        log_type: TSLogType,
        buffer: Pointer<Byte>,
    ) -> Unit>
        get() = TSLogger.logHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            TSLogger.logHandle.set(this.`$mem`, 0L, value)
        }

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            `$RuntimeHelper`.POINTER.withName("payload"),
            `$RuntimeHelper`.POINTER.withName("log"),
        ).withName("TSLogger")

        @JvmField
        public val payloadHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("payload"))

        @JvmField
        public val logHandle: VarHandle = layout.varHandle(MemoryLayout.PathElement.groupElement("log"))
    }
}