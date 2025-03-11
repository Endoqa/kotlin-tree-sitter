package tree_sitter

import lib.tree_sitter.TSParseOptions
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandles

// (state: Pointer<TSParseState>) -> Boolean
private val callbackFunctionDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_BOOLEAN, ValueLayout.ADDRESS)

private annotation class ProgressCallbackFn

class ParseOptions(
    val progressCallback: ((ParseState) -> Boolean)? = null
) {


    @ProgressCallbackFn
    private fun progressCallback(state: ParseState): Boolean {
        return progressCallback?.invoke(state) ?: true
    }


    context(Arena)
    fun into(): TSParseOptions {
        val options = TSParseOptions.allocate(this@Arena)

        if (progressCallback != null) {
            val target = MethodHandles
                .lookup()
                .unreflect(
                    ParseOptions::class.java.methods.find {
                        it.getAnnotation(ProgressCallbackFn::class.java) != null
                    }
                )

            val func = Linker.nativeLinker().upcallStub(target, callbackFunctionDescriptor, this@Arena)
            options.progress_callback = func
        }

        return options
    }

}