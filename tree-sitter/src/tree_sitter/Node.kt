package tree_sitter

import lib.tree_sitter.*
import tree_sitter.InputEdit.Companion.into
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment

class Node(
    internal val node: TSNode,
    private val owner: Arena,
) {

    val id get() = node.id.address().toULong()

    val kindID get() = ts_node_symbol(node)
    val grammarID get() = ts_node_grammar_symbol(node)
    val kind get() = ts_node_type(node).getString(0)
    val grammarName get() = ts_node_grammar_type(node).getString(0)
    val language get() = Language(ts_node_language(node))

    val isNamed get() = ts_node_is_named(node)
    val isExtra get() = ts_node_is_extra(node)
    val hasChanges get() = ts_node_has_changes(node)
    val hasError get() = ts_node_has_error(node)
    val isError get() = ts_node_is_error(node)
    val parseState get() = ts_node_parse_state(node)
    val nextParseState get() = ts_node_next_parse_state(node)
    val isMissing get() = ts_node_is_missing(node)


    val startByte get() = ts_node_start_byte(node)
    val endByte get() = ts_node_end_byte(node)
    val byteRange get() = startByte..endByte

    val range get() = Range(startByte, endByte, startPosition, endPosition)

    val startPosition get() = tempAllocate { Point(ts_node_start_point(node)) }
    val endPosition get() = tempAllocate { Point(ts_node_end_point(node)) }


    fun getChild(index: UInt): Node? {
        return isolateOwner { safeNode(ts_node_child(node, index)) { Node(it, this) } }
    }

    val childCount get() = ts_node_child_count(node)

    fun getNamedChild(index: UInt): Node? {
        return isolateOwner { safeNode(ts_node_named_child(node, index)) { Node(it, this) } }
    }

    val namedChildCount get() = ts_node_named_child_count(node)

    fun getChildByFieldName(name: String): Node? {
        return isolateOwner {
            val node = useTemp { temp ->
                ts_node_child_by_field_name(node, temp.allocateFrom(name), name.length.toUInt())
            }
            safeNode(node) { Node(it, this) }
        }
    }


    fun getChildByFieldID(id: UShort): Node? {
        return isolateOwner { safeNode(ts_node_child_by_field_id(node, id)) { Node(it, this) } }
    }

    fun fieldNameForChild(childIndex: UInt): String? {
        val ptr = ts_node_field_name_for_child(node, childIndex)
        return if (ptr == MemorySegment.NULL) null else ptr.getString(0)
    }

    fun fieldNameForNamedChild(nameChildIndex: UInt): String? {
        val ptr = ts_node_field_name_for_named_child(node, nameChildIndex)
        return if (ptr == MemorySegment.NULL) null else ptr.getString(0)
    }


    val children: List<Node>
        get() {
            return List(childCount.toInt()) {
                getChild(it.toUInt()) ?: error("impossible")
            }
        }

    fun children(cursor: TreeCursor): List<Node> {
        cursor.reset(this)
        cursor.gotoFirstChild()
        return List(childCount.toInt()) {
            val result = cursor.node
            cursor.gotoNextSibling()
            result
        }
    }

    val namedChildren: List<Node>
        get() {
            return List(namedChildCount.toInt()) {
                getNamedChild(it.toUInt()) ?: error("impossible")
            }
        }

    fun namedChildren(cursor: TreeCursor): List<Node> {
        cursor.reset(this)
        cursor.gotoFirstChild()
        return List(namedChildCount.toInt()) {
            while (!cursor.node.isNamed) {
                if (!cursor.gotoNextSibling()) {
                    break
                }
            }
            val result = cursor.node
            cursor.gotoNextSibling()
            result
        }
    }

    fun childrenByFieldName(fieldName: String, cursor: TreeCursor): Iterator<Node> {
        val fieldID = language.fieldIDForName(fieldName)
        return childrenByFieldID(fieldID, cursor)
    }


    fun childrenByFieldID(fieldID: UShort, cursor: TreeCursor): Iterator<Node> {
        cursor.reset(this)
        cursor.gotoFirstChild()

        var done = false

        return iterator<Node> {
            while (!done) {
                while (cursor.fieldID != fieldID) {
                    if (!cursor.gotoNextSibling()) {
                        return@iterator
                    }
                }

                val result = cursor.node
                if (!cursor.gotoNextSibling()) {
                    done = true
                }
                yield(result)
            }
        }
    }

    val parent get() = from { ts_node_parent(node) }

    fun childWithDescendant(descendant: Node): Node? {
        return from { ts_node_child_with_descendant(node, descendant.node) }
    }

    val nextSibling get() = from { ts_node_next_sibling(node) }
    val prevSibling get() = from { ts_node_prev_sibling(node) }

    val nextNamedSibling get() = from { ts_node_next_named_sibling(node) }
    val prevNamedSibling get() = from { ts_node_prev_named_sibling(node) }


    fun firstChildForByte(byte: UInt): Node? {
        return from { ts_node_first_child_for_byte(node, byte) }
    }

    fun firstNamedChildForByte(byte: UInt): Node? {
        return from { ts_node_first_named_child_for_byte(node, byte) }
    }


    val descendantCount get() = ts_node_descendant_count(node)

    fun descendantForByteRange(startByte: UInt, endByte: UInt): Node? {
        return from { ts_node_descendant_for_byte_range(node, startByte, endByte) }
    }

    fun namedDescendantForByteRange(startByte: UInt, endByte: UInt): Node? {
        return from { ts_node_named_descendant_for_byte_range(node, startByte, endByte) }
    }

    fun descendantForPointRange(start: Point, end: Point): Node? {
        return tempAllocate {
            val s = start.into()
            val e = end.into()

            from { ts_node_descendant_for_point_range(node, s, e) }
        }
    }


    fun namedDescendantForPointRange(start: Point, end: Point): Node? {
        return tempAllocate {
            val s = start.into()
            val e = end.into()
            from { ts_node_named_descendant_for_point_range(node, s, e) }
        }
    }

    fun toSExp(): String {
        val cString = ts_node_string(node)
        val result = cString.getString(0)
        TODO("free cString")
        return result
    }

    fun walk(): TreeCursor {
        return isolateOwner {
            val cursor = ts_tree_cursor_new(node)
            TreeCursor(cursor.`$mem`, owner)
        }
    }


    fun edit(edit: InputEdit) {
        tempAllocate {
            val e = edit.into()
            ts_node_edit(node.`$mem`, e.`$mem`)
        }
    }

    // extensions
    operator fun get(fieldName: String): Node? {
        return getChildByFieldName(fieldName)
    }

    override fun hashCode(): Int {
        val id = node.id.address()
        val tree = node.tree.address()
        val hash = if (id == tree) id else id xor tree
        return hash.toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Node) return false

        return ts_node_eq(this.node, other.node)
    }

    companion object {
        fun from(action: Arena.() -> TSNode): Node? {
            return isolateOwner {
                val node = action()
                if (ts_node_is_null(node)) {
                    null
                } else {
                    Node(node, this)
                }
            }
        }
    }
}
