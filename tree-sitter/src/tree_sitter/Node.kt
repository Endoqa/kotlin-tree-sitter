package tree_sitter

import tree_sitter.c.*
import java.lang.foreign.Arena
import java.lang.foreign.SegmentAllocator

class Node(
    internal val node: TSNode,
    private val owner: Arena = Arena.ofAuto()
) : SegmentAllocator by owner {

    val symbol get() = ts_node_symbol(node)
    val grammarSymbol get() = ts_node_grammar_symbol(node)

    val isNamed get() = ts_node_is_named(node)
    val isExtra get() = ts_node_is_extra(node)

    val isError get() = ts_node_is_error(node)
    val isMissing get() = ts_node_is_missing(node)
    val hasChanges get() = ts_node_has_changes(node)
    val hasError get() = ts_node_has_error(node)
    val parseState get() = ts_node_parse_state(node)
    val nextParseState get() = ts_node_next_parse_state(node)

    val startByte get() = ts_node_start_byte(node)
    val endByte get() = ts_node_end_byte(node)

    val childCount get() = ts_node_child_count(node)
    val namedChildCount get() = ts_node_named_child_count(node)

    val desendantCount get() = ts_node_descendant_count(node)


    fun getChild(index: UInt): Node? {
        return isolateOwner { safeNode(ts_node_child(node, index), ::isolateNode) }
    }

    fun getNamedChild(index: UInt): Node? {
        return isolateOwner { safeNode(ts_node_named_child(node, index), ::isolateNode) }
    }

    fun getChildByFieldName(name: String): Node? {
        return isolateOwner {
            val node = useTemp { temp ->
                ts_node_child_by_field_name(node, temp.allocateFrom(name), name.length.toUInt())
            }
            safeNode(node) { Node(it, this) }
        }
    }


    fun getChildByFieldId(id: UShort): Node? {
        return isolateOwner { safeNode(ts_node_child_by_field_id(node, id), ::isolateNode) }
    }


    val children: List<Node>
        get() {
            return List(childCount.toInt()) {
                isolateOwner { getChild(it.toUInt()) ?: error("impossible") }
            }
        }

    val namedChildren: List<Node>
        get() {
            return List(namedChildCount.toInt()) {
                isolateOwner { getNamedChild(it.toUInt()) ?: error("impossible") }
            }
        }


    // extensions
    operator fun get(fieldName: String): Node? {
        return getChildByFieldName(fieldName)
    }
}
