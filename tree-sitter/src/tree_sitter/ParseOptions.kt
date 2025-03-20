package tree_sitter

import lib.tree_sitter.Pointer
import lib.tree_sitter.TSParseOptions
import lib.tree_sitter.TSParseState
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandles

// (state: Pointer<TSParseState>) -> Boolean
private val callbackFunctionDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_BOOLEAN, ValueLayout.ADDRESS)

private annotation class ProgressCallbackFn

public class ParseOptions(
    public val progressCallback: ((ParseState) -> Boolean)? = null
) {


    @ProgressCallbackFn
    private fun progressCallback(state: Pointer<TSParseState>): Boolean {
        return progressCallback?.invoke(ParseState(TSParseState(state))) ?: true
    }

    private companion object {
        private val callbackMethodHandle by lazy {
            val method = ParseOptions::class.java.methods.find {
                it.getAnnotation(ProgressCallbackFn::class.java) != null
            }

            method ?: throw NoSuchMethodException("ParseOptions::progressCallback")

            MethodHandles
                .lookup()
                .unreflect(method)
        }
    }

    context(Arena)
    public fun into(): TSParseOptions {
        val options = TSParseOptions.allocate(this@Arena)

        if (progressCallback != null) {
            val func = Linker.nativeLinker()
                .upcallStub(callbackMethodHandle.bindTo(options), callbackFunctionDescriptor, this@Arena)
            options.progress_callback = func
        }

        return options
    }

}