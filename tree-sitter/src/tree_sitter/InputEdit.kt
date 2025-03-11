package tree_sitter

import lib.tree_sitter.TSInputEdit
import java.lang.foreign.Arena

data class InputEdit(
    val startByte: UInt,
    val oldEndByte: UInt,
    val newEndByte: UInt,
    val startPosition: Point,
    val oldEndPosition: Point,
    val newEndPosition: Point
) {

    companion object {
        context(Arena)
        fun InputEdit.into(): TSInputEdit {
            val edit = TSInputEdit.allocate(this@Arena)
            edit.start_byte = startByte
            edit.old_end_byte = oldEndByte
            edit.new_end_byte = newEndByte
            edit.start_point = startPosition.into()
            edit.old_end_point = oldEndPosition.into()
            edit.new_end_point = newEndPosition.into()
            return edit
        }
    }

}