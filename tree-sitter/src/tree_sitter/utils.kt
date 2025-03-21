package tree_sitter

import java.lang.foreign.Arena


internal inline fun <R> managedMemory(action: Arena.() -> R): R {
    return action(Arena.ofAuto())
}


public class ManagedArena(
    private val arena: Arena,
) : Arena by arena {
    public lateinit var droppable: Drop
    override fun close() {
        droppable.drop()
        arena.close()
    }
}

internal inline fun <R : Drop> autoDrop(action: Arena.() -> R): R {
    val arena = ManagedArena(Arena.ofAuto())
    val droppable = action(arena)
    arena.droppable = droppable

    return droppable
}


internal inline fun <R> unsafe(action: Arena.() -> R): R {
    return Arena.ofConfined().use(action)
}

internal inline fun <R> useUnsafe(action: (Arena) -> R): R {
    return Arena.ofConfined().use(action)
}

