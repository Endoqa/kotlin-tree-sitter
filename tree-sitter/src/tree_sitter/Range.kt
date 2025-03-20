package tree_sitter

public data class Range(
    val startByte: UInt,
    val endByte: UInt,
    val startPoint: Point,
    val endPoint: Point
)