// this file is auto generated by endoqa kotlin ffi, modify it with caution
package tree_sitter

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle
import java.lang.invoke.VarHandle
import kotlin.Int
import kotlin.jvm.JvmField
import kotlin.jvm.JvmInline

@JvmInline
public value class TSRange(
    public val `$mem`: MemorySegment,
) {
    public var start_point: TSPoint
        get() = TSPoint(TSRange.start_pointHandle.invokeExact(this.`$mem`) as MemorySegment)
        set(`value`) {
            MemorySegment.copy(value.`$mem`, 0L, this.start_point.`$mem`, 0L, TSPoint.layout.byteSize())
        }

    public var end_point: TSPoint
        get() = TSPoint(TSRange.end_pointHandle.invokeExact(this.`$mem`) as MemorySegment)
        set(`value`) {
            MemorySegment.copy(value.`$mem`, 0L, this.end_point.`$mem`, 0L, TSPoint.layout.byteSize())
        }

    public var start_byte: uint32_t
        get() = (TSRange.start_byteHandle.get(this.`$mem`) as Int).toUInt()
        set(`value`) {
            TSRange.start_byteHandle.set(this.`$mem`, value.toInt())
        }

    public var end_byte: uint32_t
        get() = (TSRange.end_byteHandle.get(this.`$mem`) as Int).toUInt()
        set(`value`) {
            TSRange.end_byteHandle.set(this.`$mem`, value.toInt())
        }

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            TSPoint.layout.withName("start_point"),
            TSPoint.layout.withName("end_point"),
            ValueLayout.JAVA_INT.withName("start_byte"),
            ValueLayout.JAVA_INT.withName("end_byte"),
        ).withName("TSRange")

        @JvmField
        public val start_pointHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("start_point"))

        @JvmField
        public val end_pointHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("end_point"))

        @JvmField
        public val start_byteHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("start_byte"))

        @JvmField
        public val end_byteHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("end_byte"))
    }
}
