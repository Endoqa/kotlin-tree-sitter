package tree_sitter

import lib.tree_sitter.*
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.SegmentAllocator

class Parser(
    internal val parser: Pointer<TSParser> = ts_parser_new(),
    private val owner: Arena = Arena.ofAuto()
) : SegmentAllocator by owner {

    init {
        cleaner(this) {
            ts_parser_delete(parser)
        }
    }

    fun setLanguage(language: Language) {
        ts_parser_set_language(parser, language.language)
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

}
