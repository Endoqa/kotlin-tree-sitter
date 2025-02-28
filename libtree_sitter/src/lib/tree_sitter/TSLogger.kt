// this file is auto generated by endoqa kotlin ffi, modify it with caution
package lib.tree_sitter

import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.VarHandle

@JvmInline
public value class TSLogger(
    public val `$mem`: MemorySegment,
) {
    public var payload: Pointer<Unit>
        get() = payloadHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            payloadHandle.set(this.`$mem`, 0L, value)
        }

    public var log: Pointer<(
        payload: Pointer<Unit>,
        log_type: TSLogType,
        buffer: Pointer<Byte>,
    ) -> Unit>
        get() = logHandle.get(this.`$mem`, 0L) as MemorySegment
        set(`value`) {
            logHandle.set(this.`$mem`, 0L, value)
        }

    public constructor(gc: Boolean) : this(kotlin.run {
        require(gc) { "Do not call this if gc is not want" }
        Arena.ofAuto().allocate(layout)
    })

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

        @JvmStatic
        public fun allocate(alloc: SegmentAllocator): TSLogger = TSLogger(alloc.allocate(layout))

        public fun interface log {
            @CFunctionInvoke
            public fun invoke(
                payload: Pointer<Unit>,
                log_type: TSLogType,
                buffer: Pointer<Byte>,
            )

            public fun allocate(arena: Arena): MemorySegment =
                Linker.nativeLinker().upcallStub(invokeHandle.bindTo(this), fd, arena)

            public companion object {
                @JvmStatic
                public val invokeHandle: MethodHandle =
                    MethodHandles.filterArguments(
                        MethodHandles.lookup().unreflect(log::class.java.methods.find {
                            it.getAnnotation(CFunctionInvoke::class.java) != null
                        }
                        ),
                        1, null, TSLogType.fromInt, null,
                    )

                @JvmStatic
                public val fd: FunctionDescriptor = FunctionDescriptor.ofVoid(
                    `$RuntimeHelper`.POINTER,
                    ValueLayout.JAVA_INT,
                    `$RuntimeHelper`.POINTER,
                )
            }
        }
    }
}
