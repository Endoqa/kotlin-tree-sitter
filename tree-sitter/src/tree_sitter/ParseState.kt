package tree_sitter

import lib.tree_sitter.TSParseState

@JvmInline
public value class ParseState(public val state: TSParseState) {

    public val currentByteOffset: UInt get() = state.current_byte_offset
    public val hasError: Boolean get() = state.has_error

}