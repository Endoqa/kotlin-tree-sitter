// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.foreign.*

@JvmInline
public value class TSQuery(
    public val `$mem`: MemorySegment,
) {
    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
        ).withName("TSQuery")

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): TSQuery = TSQuery(alloc.allocate(layout))
    }
}