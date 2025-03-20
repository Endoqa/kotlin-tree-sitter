package tree_sitter

import jdk.internal.foreign.MemorySessionImpl
import jdk.internal.ref.CleanerFactory
import java.lang.foreign.Arena


internal inline fun <R> managedMemory(action: Arena.() -> R): R {
    return action(Arena.ofAuto())
}

internal inline fun <R : Drop> autoDrop(action: Arena.() -> R): R {
    val session = MemorySessionImpl.createImplicit(CleanerFactory.cleaner())
    val arena = session.asArena()
    val droppable = action(arena)

    session.addCloseAction {
        droppable.drop()
    }

    return droppable
}


internal inline fun <R> unsafe(action: Arena.() -> R): R {
    return Arena.ofConfined().use(action)
}

internal inline fun <R> useUnsafe(action: (Arena) -> R): R {
    return Arena.ofConfined().use(action)
}

