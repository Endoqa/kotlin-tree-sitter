package tree_sitter

import tree_sitter.c.TSNode
import tree_sitter.c.ts_node_is_null
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

internal inline fun <R : Any> safeNode(node: TSNode, action: (node: TSNode) -> R): R? {
    return if (ts_node_is_null(node)) {
        null
    } else {
        action(node)
    }
}


internal inline fun Arena.isolateNode(node: TSNode): Node {
    return Node(node, this)
}