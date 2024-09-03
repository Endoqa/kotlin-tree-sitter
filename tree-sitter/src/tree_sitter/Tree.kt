package tree_sitter

import lib.tree_sitter.Pointer
import lib.tree_sitter.TSTree
import lib.tree_sitter.ts_tree_delete
import lib.tree_sitter.ts_tree_root_node
import java.lang.foreign.Arena
import java.lang.foreign.SegmentAllocator

class Tree(
    internal val tree: Pointer<TSTree>,
    private val owner: Arena = Arena.ofAuto()
) : SegmentAllocator by owner {

    init {
        cleaner(this) {
            ts_tree_delete(tree)
        }
    }


    val rootNode
        get() = isolateOwner {
            Node(ts_tree_root_node(tree), this)
        }

}