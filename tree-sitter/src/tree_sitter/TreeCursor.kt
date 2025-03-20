package tree_sitter

import lib.tree_sitter.*
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment

public class TreeCursor(
    public val raw: Pointer<TSTreeCursor>,
    public val owner: Arena? = null // for auto
) : Drop {


    public val node: Node get() = Node.from { ts_tree_cursor_current_node(raw) } ?: error("Node is null")

    public val fieldID: TSFieldId get() = ts_tree_cursor_current_field_id(raw)

    public val fieldName: String?
        get() {
            val ptr = ts_tree_cursor_current_field_name(raw)
            return if (ptr == MemorySegment.NULL) {
                null
            } else {
                ptr.getString(0)
            }
        }

    public val depth: UInt get() = ts_tree_cursor_current_depth(raw)
    public val descendantIndex: UInt get() = ts_tree_cursor_current_descendant_index(raw)

    public fun gotoFirstChild(): Boolean {
        return ts_tree_cursor_goto_first_child(raw)
    }

    public fun gotoLastChild(): Boolean {
        return ts_tree_cursor_goto_last_child(raw)
    }

    public fun gotoParent(): Boolean {
        return ts_tree_cursor_goto_parent(raw)
    }

    public fun gotoNextSibling(): Boolean {
        return ts_tree_cursor_goto_next_sibling(raw)
    }

    public fun gotoDescendant(descendantIndex: UInt) {
        ts_tree_cursor_goto_descendant(raw, descendantIndex)
    }

    public fun gotoPreviousSibling(): Boolean {
        return ts_tree_cursor_goto_previous_sibling(raw)
    }

    public fun gotoFirstChildForByte(index: UInt): Long {
        return ts_tree_cursor_goto_first_child_for_byte(raw, index)
    }

    public fun gotoFirstChildForPoint(point: Point): Long {
        return unsafe {
            val p = TSPoint.allocate(this)
            p.row = point.row
            p.column = point.column
            gotoFirstChildForPoint(p)
        }
    }

    public fun gotoFirstChildForPoint(point: TSPoint): Long {
        return ts_tree_cursor_goto_first_child_for_point(raw, point)
    }

    public fun reset(node: Node) {
        ts_tree_cursor_reset(raw, node.node)
    }

    public fun resetTo(cursor: TreeCursor) {
        ts_tree_cursor_reset_to(raw, cursor.raw)
    }

    public override fun drop() {
        ts_tree_cursor_delete(raw)
    }

}