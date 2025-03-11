package tree_sitter

import lib.tree_sitter.TSNode
import lib.tree_sitter.TSPoint
import lib.tree_sitter.ts_node_is_null
import java.lang.foreign.Arena

internal inline fun <R> tempAllocate(action: Arena.() -> R): R {
    return Arena.ofConfined().use(action)
}

internal inline fun <R> useTemp(action: (Arena) -> R): R {
    return Arena.ofConfined().use(action)
}

internal inline fun <R> isolateOwner(action: Arena.() -> R): R {
    return action(Arena.ofAuto())
}


internal inline fun <R> unsafe(action: Arena.() -> R): R {
    return Arena.ofConfined().use { action(it) }
}

internal inline fun <R : Any> safeNode(node: TSNode, action: (node: TSNode) -> R): R? {
    return if (ts_node_is_null(node)) {
        null
    } else {
        action(node)
    }
}

