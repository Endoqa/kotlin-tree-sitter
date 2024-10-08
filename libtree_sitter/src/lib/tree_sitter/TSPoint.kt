// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.foreign.*
import java.lang.invoke.VarHandle

@JvmInline
public value class TSPoint(
    public val `$mem`: MemorySegment,
) {
    public var row: UInt
        get() = (rowHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            rowHandle.set(this.`$mem`, 0L, value.toInt())
        }

    public var column: UInt
        get() = (columnHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            columnHandle.set(this.`$mem`, 0L, value.toInt())
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("row"),
            ValueLayout.JAVA_INT.withName("column"),
        ).withName("TSPoint")

        @JvmField
        public val rowHandle: VarHandle = layout.varHandle(MemoryLayout.PathElement.groupElement("row"))

        @JvmField
        public val columnHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("column"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): TSPoint = TSPoint(alloc.allocate(layout))
    }
}
