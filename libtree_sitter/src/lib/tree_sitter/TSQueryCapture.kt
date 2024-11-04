// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.VarHandle

@JvmInline
public value class TSQueryCapture(
    public val `$mem`: MemorySegment,
) {
    public var node: TSNode
        get() = TSNode(nodeHandle.invokeExact(this.`$mem`, 0L) as MemorySegment)
        set(`value`) {
            MemorySegment.copy(value.`$mem`, 0L, this.node.`$mem`, 0L, TSNode.layout.byteSize())
        }

    public var index: UInt
        get() = (indexHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            indexHandle.set(this.`$mem`, 0L, value.toInt())
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            TSNode.layout.withName("node"),
            ValueLayout.JAVA_INT.withName("index"),
            MemoryLayout.paddingLayout(4),
        ).withName("TSQueryCapture")

        @JvmField
        public val nodeHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("node"))

        @JvmField
        public val indexHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("index"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): TSQueryCapture =
            TSQueryCapture(alloc.allocate(layout))
    }
}