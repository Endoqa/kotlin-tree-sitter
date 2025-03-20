package tree_sitter

import lib.tree_sitter.TSPoint
import java.lang.foreign.Arena

public data class Point(val row: UInt, val column: UInt) {
    public constructor(point: TSPoint) : this(point.row, point.column)


    public fun into(p: TSPoint): TSPoint {
        p.row = this.row
        p.column = this.column
        return p
    }


    context(Arena)
    public fun into(): TSPoint {
        return into(TSPoint.allocate(this@Arena))
    }
}