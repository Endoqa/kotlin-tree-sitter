// this file is auto generated by endoqa kotlin ffi, modify it with caution
package tree_sitter

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle
import java.lang.invoke.VarHandle
import kotlin.UInt
import kotlin.jvm.JvmField
import kotlin.jvm.JvmInline

@JvmInline
public value class TSInputEdit(
    public val `$mem`: MemorySegment,
) {
    public var start_byte: uint32_t
        get() = (TSInputEdit.start_byteHandle.get(this.`$mem`) as UInt).toUInt()
        set(`value`) {
            TSInputEdit.start_byteHandle.set(this.`$mem`, value.toInt())
        }

    public var old_end_byte: uint32_t
        get() = (TSInputEdit.old_end_byteHandle.get(this.`$mem`) as UInt).toUInt()
        set(`value`) {
            TSInputEdit.old_end_byteHandle.set(this.`$mem`, value.toInt())
        }

    public var new_end_byte: uint32_t
        get() = (TSInputEdit.new_end_byteHandle.get(this.`$mem`) as UInt).toUInt()
        set(`value`) {
            TSInputEdit.new_end_byteHandle.set(this.`$mem`, value.toInt())
        }

    public var start_point: TSPoint
        get() = TSPoint(TSInputEdit.start_pointHandle.invokeExact(this.`$mem`) as MemorySegment)
        set(`value`) {
            MemorySegment.copy(value.`$mem`, 0L, this.start_point.`$mem`, 0L, TSPoint.layout.byteSize())
        }

    public var old_end_point: TSPoint
        get() = TSPoint(TSInputEdit.old_end_pointHandle.invokeExact(this.`$mem`) as MemorySegment)
        set(`value`) {
            MemorySegment.copy(value.`$mem`, 0L, this.old_end_point.`$mem`, 0L, TSPoint.layout.byteSize())
        }

    public var new_end_point: TSPoint
        get() = TSPoint(TSInputEdit.new_end_pointHandle.invokeExact(this.`$mem`) as MemorySegment)
        set(`value`) {
            MemorySegment.copy(value.`$mem`, 0L, this.new_end_point.`$mem`, 0L, TSPoint.layout.byteSize())
        }

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("start_byte"),
            ValueLayout.JAVA_INT.withName("old_end_byte"),
            ValueLayout.JAVA_INT.withName("new_end_byte"),
            TSPoint.layout.withName("start_point"),
            TSPoint.layout.withName("old_end_point"),
            TSPoint.layout.withName("new_end_point"),
        ).withName("TSInputEdit")

        @JvmField
        public val start_byteHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("start_byte"))

        @JvmField
        public val old_end_byteHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("old_end_byte"))

        @JvmField
        public val new_end_byteHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("new_end_byte"))

        @JvmField
        public val start_pointHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("start_point"))

        @JvmField
        public val old_end_pointHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("old_end_point"))

        @JvmField
        public val new_end_pointHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("new_end_point"))
    }
}
