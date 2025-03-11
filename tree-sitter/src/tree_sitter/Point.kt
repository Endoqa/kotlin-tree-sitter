package tree_sitter

import lib.tree_sitter.TSPoint
import java.lang.foreign.Arena

data class Point(val row: UInt, val column: UInt) {
    constructor(point: lib.tree_sitter.TSPoint) : this(point.row, point.column)

    context(Arena)
    fun into() : TSPoint {
        val p = TSPoint.allocate(this@Arena)
        p.row = this.row
        p.column = this.column
        return p
    }
}