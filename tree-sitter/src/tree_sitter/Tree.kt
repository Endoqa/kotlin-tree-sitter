package tree_sitter

import lib.tree_sitter.*

class Tree(
    internal val tree: Pointer<TSTree>,
) {

    init {
        cleaner(this) {
            ts_tree_delete(tree)
        }
    }


    val rootNode
        get() = Node.from { ts_tree_root_node(tree) } ?: error("Failed to get root node")

    val language get() = Language(ts_tree_language(tree))

    fun walk(): TreeCursor {
        return rootNode.walk()
    }

    fun drop() {
        ts_tree_delete(tree)
    }

}