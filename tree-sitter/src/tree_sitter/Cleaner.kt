package tree_sitter

import java.lang.ref.Cleaner

public interface Drop {
    public fun drop()
}

private val cleaner = Cleaner.create() ?: error("Failed to create cleaner")

internal fun cleaner(obj: Any, action: Runnable) {
    cleaner.register(obj, action)
}

internal fun autoCleaner(obj: Drop) {
    cleaner(obj, Runnable(obj::drop))
}