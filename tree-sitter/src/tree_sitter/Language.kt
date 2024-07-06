package tree_sitter

import tree_sitter.c.*
import java.lang.foreign.Arena
import java.lang.foreign.SegmentAllocator

class Language(
    val language: Pointer<TSLanguage>,
    private val owner: Arena = Arena.ofAuto()
) : SegmentAllocator by owner {

    val version get() = ts_language_version(language)
    val symbolCount get() = ts_language_symbol_count(language)

    val stateCount get() = ts_language_state_count(language)
    val fieldCount get() = ts_language_field_count(language)


    fun getSymbolForName(name: String, isNamed: Boolean): UShort {
        return useTemp {
            ts_language_symbol_for_name(language, it.allocateFrom(name), name.length.toUInt(), isNamed)
        }
    }
}