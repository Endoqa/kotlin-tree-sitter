package tree_sitter

import lib.tree_sitter.*
import java.lang.foreign.MemorySegment

public class Parser(
    internal val parser: Pointer<TSParser>,
) : Drop {


    public constructor(managed: Boolean = false) : this(ts_parser_new()) {
        if (managed) {
            autoCleaner(this)
        }
    }

    public var language: Language? = null
        set(value) {
            field = value
            if (value != null) {
                ts_parser_set_language(parser, value.language)
            }
        }


    public fun parse(source: String, oldTree: Tree? = null): Tree {
        val ptr = unsafe {
            val s = allocateFrom(source)
            val tree = ts_parser_parse_string(
                parser,
                oldTree?.raw ?: MemorySegment.NULL,
                s,
                source.length.toUInt()
            )
            tree
        }

        return Tree(ptr)
    }

    public fun reset() {
        ts_parser_reset(parser)
    }

    public var timeoutMacros: ULong
        get() = ts_parser_timeout_micros(parser)
        set(value) {
            ts_parser_set_timeout_micros(parser, value)
        }


    public override fun drop() {
        ts_parser_delete(parser)
    }

}
