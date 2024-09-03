package tree_sitter

import lib.tree_sitter.*
import java.lang.foreign.*

class Language(
    internal val language: Pointer<TSLanguage>,
    private val owner: Arena = Arena.ofAuto()
) : SegmentAllocator by owner {

    init {
        cleaner(this) {
            ts_language_delete(language)
        }
    }


    val version get() = ts_language_version(language)
    val symbolCount get() = ts_language_symbol_count(language)

    val stateCount get() = ts_language_state_count(language)
    val fieldCount get() = ts_language_field_count(language)


    fun getSymbolForName(name: String, isNamed: Boolean): UShort {
        return useTemp {
            ts_language_symbol_for_name(language, it.allocateFrom(name), name.length.toUInt(), isNamed)
        }
    }


    companion object {

        private val languageMethodDesc = FunctionDescriptor.of(ValueLayout.ADDRESS)
        private val languageCache = hashMapOf<String, Language>()


        fun getLanguage(languageId: String): Language {

            return languageCache.getOrPut(languageId) {
                val languageMH = Linker.nativeLinker().downcallHandle(
                    `$RuntimeHelper`.symbolLookup.find("tree_sitter_$languageId").get(),
                    languageMethodDesc
                )

                val language: Pointer<TSLanguage> = languageMH.invokeExact() as MemorySegment
                Language(language)
            }

        }
    }

}