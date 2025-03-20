package tree_sitter

import lib.tree_sitter.*

public class Tree(
    internal val raw: Pointer<TSTree>,
) : Drop {

    public constructor(raw: Pointer<TSTree>, managed: Boolean) : this(raw) {
        if (managed) {
            autoCleaner(this)
        }
    }

    public val rootNode: Node
        get() = Node.from { ts_tree_root_node(raw) } ?: error("Failed to get root node")

    public val language: Language get() = Language(ts_tree_language(raw))

    public fun walk(): TreeCursor {
        return rootNode.walk()
    }

    public override fun drop() {
        ts_tree_delete(raw)
    }

}