// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.foreign.*
import java.lang.invoke.VarHandle

@JvmInline
public value class TSQueryMatch(
    public val `$mem`: MemorySegment,
) {
    public var id: UInt
        get() = (idHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            idHandle.set(this.`$mem`, 0L, value.toInt())
        }

    public var pattern_index: UShort
        get() = (pattern_indexHandle.get(this.`$mem`, 0L) as Short).toUShort()
        set(`value`) {
            pattern_indexHandle.set(this.`$mem`, 0L, value.toShort())
        }

    public var capture_count: UShort
        get() = (capture_countHandle.get(this.`$mem`, 0L) as Short).toUShort()
        set(`value`) {
            capture_countHandle.set(this.`$mem`, 0L, value.toShort())
        }

    public var captures: Pointer<TSQueryCapture>
        get() = capturesHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            capturesHandle.set(this.`$mem`, 0L, value)
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("id"),
            ValueLayout.JAVA_SHORT.withName("pattern_index"),
            ValueLayout.JAVA_SHORT.withName("capture_count"),
            `$RuntimeHelper`.POINTER.withName("captures"),
        ).withName("TSQueryMatch")

        @JvmField
        public val idHandle: VarHandle = layout.varHandle(MemoryLayout.PathElement.groupElement("id"))

        @JvmField
        public val pattern_indexHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("pattern_index"))

        @JvmField
        public val capture_countHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("capture_count"))

        @JvmField
        public val capturesHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("captures"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): TSQueryMatch = TSQueryMatch(alloc.allocate(layout))
    }
}
