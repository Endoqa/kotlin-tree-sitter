// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.VarHandle

@JvmInline
public value class TSTreeCursor(
    public val `$mem`: MemorySegment,
) {
    public var tree: Pointer<Unit>
        get() = treeHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            treeHandle.set(this.`$mem`, 0L, value)
        }

    public var id: Pointer<Unit>
        get() = idHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            idHandle.set(this.`$mem`, 0L, value)
        }

    public val context: NativeArray<UInt>
        get() = contextHandle.invokeExact(this.`$mem`, 0L) as MemorySegment

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            `$RuntimeHelper`.POINTER.withName("tree"),
            `$RuntimeHelper`.POINTER.withName("id"),
            MemoryLayout.sequenceLayout(3L, ValueLayout.JAVA_INT).withName("context"),
            MemoryLayout.paddingLayout(4),
        ).withName("TSTreeCursor")

        @JvmField
        public val treeHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("tree"))

        @JvmField
        public val idHandle: VarHandle = layout.varHandle(MemoryLayout.PathElement.groupElement("id"))

        @JvmField
        public val contextHandle: MethodHandle =
            layout.sliceHandle(MemoryLayout.PathElement.groupElement("context"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): TSTreeCursor = TSTreeCursor(alloc.allocate(layout))
    }
}
