package tree_sitter

import lib.tree_sitter.TSInputEdit
import java.lang.foreign.Arena

public data class InputEdit(
    val startByte: UInt,
    val oldEndByte: UInt,
    val newEndByte: UInt,
    val startPosition: Point,
    val oldEndPosition: Point,
    val newEndPosition: Point
) {


    public fun into(edit: TSInputEdit): TSInputEdit {
        edit.start_byte = startByte
        edit.old_end_byte = oldEndByte
        edit.new_end_byte = newEndByte
        startPosition.into(edit.start_point)
        oldEndPosition.into(edit.new_end_point)

        return edit
    }

    context(Arena)
    public fun into(): TSInputEdit {
        return into(TSInputEdit.allocate(this@Arena))
    }

}