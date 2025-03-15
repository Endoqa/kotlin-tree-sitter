package tree_sitter

import lib.tree_sitter.*
import java.lang.foreign.MemorySegment

class Parser(
    internal val parser: Pointer<TSParser>,
) {


    constructor() : this(ts_parser_new())

    var language: Language? = null
        set(value) {
            field = value
            if (value != null) {
                ts_parser_set_language(parser, value.language)
            }
        }


    fun parse(source: String, oldTree: Tree? = null): Tree {
        val ptr = tempAllocate {
            val s = allocateFrom(source)
            val tree = ts_parser_parse_string(
                parser,
                oldTree?.tree ?: MemorySegment.NULL,
                s,
                source.length.toUInt()
            )
            tree
        }

        return Tree(ptr)
    }

    fun reset() {
        ts_parser_reset(parser)
    }

    var timeoutMacros: ULong
        get() = ts_parser_timeout_micros(parser)
        set(value) {
            ts_parser_set_timeout_micros(parser, value)
        }


    fun drop() {
        ts_parser_delete(parser)
    }

}
