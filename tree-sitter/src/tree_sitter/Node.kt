package tree_sitter

import lib.tree_sitter.*
import java.lang.foreign.*

private val freeFunc = Linker.nativeLinker().downcallHandle(FunctionDescriptor.ofVoid(ValueLayout.ADDRESS))

private val defaultFreeFunction by lazy {
    val freePtr = SymbolLookup.loaderLookup().find("free")
    freePtr.get()
}


private fun free(free: MemorySegment, ptr: MemorySegment) {
    freeFunc.bindTo(free).invokeExact(ptr)
}

public class Node(
    internal val node: TSNode,
    private val owner: Arena,
) {

    public val id: ULong get() = node.id.address().toULong()

    public val kindID: TSSymbol get() = ts_node_symbol(node)
    public val grammarID: TSSymbol get() = ts_node_grammar_symbol(node)
    public val kind: String get() = ts_node_type(node).getString(0)
    public val grammarName: String get() = ts_node_grammar_type(node).getString(0)
    public val language: Language get() = Language(ts_node_language(node))

    public val isNamed: Boolean get() = ts_node_is_named(node)
    public val isExtra: Boolean get() = ts_node_is_extra(node)
    public val hasChanges: Boolean get() = ts_node_has_changes(node)
    public val hasError: Boolean get() = ts_node_has_error(node)
    public val isError: Boolean get() = ts_node_is_error(node)
    public val parseState: TSStateId get() = ts_node_parse_state(node)
    public val nextParseState: TSStateId get() = ts_node_next_parse_state(node)
    public val isMissing: Boolean get() = ts_node_is_missing(node)


    public val startByte: UInt get() = ts_node_start_byte(node)
    public val endByte: UInt get() = ts_node_end_byte(node)
    public val byteRange: UIntRange get() = startByte..endByte

    public val range: Range get() = Range(startByte, endByte, startPosition, endPosition)

    public val startPosition: Point get() = unsafe { Point(ts_node_start_point(node)) }
    public val endPosition: Point get() = unsafe { Point(ts_node_end_point(node)) }


    public fun getChild(index: UInt): Node? {
        return from { ts_node_child(node, index) }
    }

    public val childCount: UInt get() = ts_node_child_count(node)

    public fun getNamedChild(index: UInt): Node? {
        return from { ts_node_named_child(node, index) }
    }

    public val namedChildCount: UInt get() = ts_node_named_child_count(node)

    public fun getChildByFieldName(name: String): Node? {
        return from {
            useUnsafe { onStack ->
                ts_node_child_by_field_name(node, onStack.allocateFrom(name), name.length.toUInt())
            }
        }
    }


    public fun getChildByFieldID(id: UShort): Node? {
        return from { ts_node_child_by_field_id(node, id) }
    }

    public fun fieldNameForChild(childIndex: UInt): String? {
        val ptr = ts_node_field_name_for_child(node, childIndex)
        return if (ptr == MemorySegment.NULL) null else ptr.getString(0)
    }

    public fun fieldNameForNamedChild(nameChildIndex: UInt): String? {
        val ptr = ts_node_field_name_for_named_child(node, nameChildIndex)
        return if (ptr == MemorySegment.NULL) null else ptr.getString(0)
    }


    public val children: List<Node>
        get() {
            return List(childCount.toInt()) {
                getChild(it.toUInt()) ?: error("impossible")
            }
        }

    public fun children(cursor: TreeCursor): List<Node> {
        cursor.reset(this)
        cursor.gotoFirstChild()
        return List(childCount.toInt()) {
            val result = cursor.node
            cursor.gotoNextSibling()
            result
        }
    }

    public val namedChildren: List<Node>
        get() {
            return List(namedChildCount.toInt()) {
                getNamedChild(it.toUInt()) ?: error("impossible")
            }
        }

    public fun namedChildren(cursor: TreeCursor): List<Node> {
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

    public fun childrenByFieldName(fieldName: String, cursor: TreeCursor): Iterator<Node> {
        val fieldID = language.fieldIDForName(fieldName)
        return childrenByFieldID(fieldID, cursor)
    }


    public fun childrenByFieldID(fieldID: UShort, cursor: TreeCursor): Iterator<Node> {
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

    public val parent: Node? get() = from { ts_node_parent(node) }

    public fun childWithDescendant(descendant: Node): Node? {
        return from { ts_node_child_with_descendant(node, descendant.node) }
    }

    public val nextSibling: Node? get() = from { ts_node_next_sibling(node) }
    public val prevSibling: Node? get() = from { ts_node_prev_sibling(node) }

    public val nextNamedSibling: Node? get() = from { ts_node_next_named_sibling(node) }
    public val prevNamedSibling: Node? get() = from { ts_node_prev_named_sibling(node) }


    public fun firstChildForByte(byte: UInt): Node? {
        return from { ts_node_first_child_for_byte(node, byte) }
    }

    public fun firstNamedChildForByte(byte: UInt): Node? {
        return from { ts_node_first_named_child_for_byte(node, byte) }
    }


    public val descendantCount: UInt get() = ts_node_descendant_count(node)

    public fun descendantForByteRange(startByte: UInt, endByte: UInt): Node? {
        return from { ts_node_descendant_for_byte_range(node, startByte, endByte) }
    }

    public fun namedDescendantForByteRange(startByte: UInt, endByte: UInt): Node? {
        return from { ts_node_named_descendant_for_byte_range(node, startByte, endByte) }
    }

    public fun descendantForPointRange(start: Point, end: Point): Node? {
        return unsafe {
            val s = start.into()
            val e = end.into()

            from { ts_node_descendant_for_point_range(node, s, e) }
        }
    }


    public fun namedDescendantForPointRange(start: Point, end: Point): Node? {
        return unsafe {
            val s = start.into()
            val e = end.into()
            from { ts_node_named_descendant_for_point_range(node, s, e) }
        }
    }

    public fun toSExp(freeFunc: MemorySegment = defaultFreeFunction): String {
        val cString = ts_node_string(node)
        val result = cString.getString(0)
        free(freeFunc, cString)
        return result
    }

    public fun walk(): TreeCursor {
        return autoDrop {
            val cursor = ts_tree_cursor_new(node)
            TreeCursor(cursor.`$mem`, owner)
        }
    }


    public fun edit(edit: InputEdit) {
        unsafe {
            val e = edit.into()
            ts_node_edit(node.`$mem`, e.`$mem`)
        }
    }

    // extensions
    public operator fun get(fieldName: String): Node? {
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

    public fun into() : TSNode {
        return node
    }

    public companion object {
        /**
         * Create a node with its lifecycle bind to a separate [Node.owner]
         *
         * @param [action] a function takes a context [Node.owner], the [Arena] is same as the new [Node.owner].
         * If the function returns a [TSNode] [ts_node_is_null], the lifecycle will be end right away.
         *
         * @return A [Node] with its own lifecycle.
         */
        public fun from(action: Arena.() -> TSNode): Node? {
            val arena = Arena.ofShared()

            try {
                with(arena) {
                    val node = action()
                    return if (ts_node_is_null(node)) {
                        this.close()
                        null
                    } else {
                        val n = Node(node, this)
                        cleaner(n) {
                            this.close()
                        }
                        n
                    }
                }
            } catch (e: Throwable) {
                arena.close()
                throw e
            }
        }
    }
}