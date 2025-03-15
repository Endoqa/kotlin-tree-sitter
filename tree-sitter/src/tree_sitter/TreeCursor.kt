package tree_sitter

import lib.tree_sitter.*
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment

class TreeCursor(
    val raw: Pointer<TSTreeCursor>,
    val owner: Arena? = null // for auto
) {


    val node get() = Node.from { ts_tree_cursor_current_node(raw) } ?: error("Node is null")

    val fieldID get() = ts_tree_cursor_current_field_id(raw)

    val fieldName: String?
        get() {
            val ptr = ts_tree_cursor_current_field_name(raw)
            return if (ptr == MemorySegment.NULL) {
                null
            } else {
                ptr.getString(0)
            }
        }

    val depth get() = ts_tree_cursor_current_depth(raw)
    val descendantIndex get() = ts_tree_cursor_current_descendant_index(raw)

    fun gotoFirstChild(): Boolean {
        return ts_tree_cursor_goto_first_child(raw)
    }

    fun gotoLastChild(): Boolean {
        return ts_tree_cursor_goto_last_child(raw)
    }

    fun gotoParent(): Boolean {
        return ts_tree_cursor_goto_parent(raw)
    }

    fun gotoNextSibling(): Boolean {
        return ts_tree_cursor_goto_next_sibling(raw)
    }

    fun gotoDescendant(descendantIndex: UInt) {
        ts_tree_cursor_goto_descendant(raw, descendantIndex)
    }

    fun gotoPreviousSibling(): Boolean {
        return ts_tree_cursor_goto_previous_sibling(raw)
    }

    fun gotoFirstChildForByte(index: UInt): Long {
        return ts_tree_cursor_goto_first_child_for_byte(raw, index)
    }

    fun gotoFirstChildForPoint(point: Point): Long {
        return unsafe {
            val p = TSPoint.allocate(this)
            p.row = point.row
            p.column = point.column
            gotoFirstChildForPoint(p)
        }
    }

    fun gotoFirstChildForPoint(point: TSPoint): Long {
        return ts_tree_cursor_goto_first_child_for_point(raw, point)
    }

    fun reset(node: Node) {
        ts_tree_cursor_reset(raw, node.node)
    }

    fun resetTo(cursor: TreeCursor) {
        ts_tree_cursor_reset_to(raw, cursor.raw)
    }

    fun drop() {
        ts_tree_cursor_delete(raw)
    }

}