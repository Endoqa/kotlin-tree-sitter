package tree_sitter

import lib.tree_sitter.TSParseState

@JvmInline
value class ParseState(val state: TSParseState) {

    val currentByteOffset get() = state.current_byte_offset
    val hasError get() = state.has_error

}