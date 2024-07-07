package tree_sitter

import java.lang.ref.Cleaner

private val cleaner = Cleaner.create() ?: error("Failed to create cleaner")

internal fun cleaner(obj: Any, action: Runnable) {
    cleaner.register(obj, action)
}