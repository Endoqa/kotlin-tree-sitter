// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.foreign.*

@JvmInline
public value class TSQueryCursor(
    public val `$mem`: MemorySegment,
) {
    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
        ).withName("TSQueryCursor")

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): TSQueryCursor =
            TSQueryCursor(alloc.allocate(layout))
    }
}