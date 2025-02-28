// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.foreign.*
import java.lang.invoke.VarHandle

@JvmInline
public value class TSQueryPredicateStep(
    public val `$mem`: MemorySegment,
) {
    public var type: TSQueryPredicateStepType
        get() = TSQueryPredicateStepType.fromInt(typeHandle.get(this.`$mem`, 0L) as Int)
        set(`value`) {
            typeHandle.set(this.`$mem`, 0L, value.value)
        }

    public var value_id: UInt
        get() = (value_idHandle.get(this.`$mem`, 0L) as Int).toUInt()
        set(`value`) {
            value_idHandle.set(this.`$mem`, 0L, value.toInt())
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

    public companion object {
        public val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("type"),
            ValueLayout.JAVA_INT.withName("value_id"),
        ).withName("TSQueryPredicateStep")

        @JvmField
        public val typeHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("type"))

        @JvmField
        public val value_idHandle: VarHandle =
            layout.varHandle(MemoryLayout.PathElement.groupElement("value_id"))

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): TSQueryPredicateStep =
            TSQueryPredicateStep(alloc.allocate(layout))
    }
}
