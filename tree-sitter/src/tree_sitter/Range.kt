package tree_sitter

data class Range(
    val startByte: UInt,
    val endByte: UInt,
    val startPoint: Point,
    val endPoint: Point
)