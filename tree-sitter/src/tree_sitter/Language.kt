package tree_sitter

import lib.tree_sitter.*
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

public class Language(
    internal val language: Pointer<TSLanguage>,
) {


    public val name: String get() = ts_language_name(language).getString(0)
    public val abiVersion: UInt get() = ts_language_abi_version(language)

    public val fieldCount: UInt get() = ts_language_field_count(language)
    public val metadata: LanguageMetadata get() = LanguageMetadata.from(TSLanguageMetadata(ts_language_metadata(language)))
    public val nodeKindCount: UInt get() = ts_language_symbol_count(language)
    public val parseStateCount: UInt get() = ts_language_state_count(language)

    public val supertypes: UShortArray
        get() : UShortArray {
            return unsafe {
                val length = allocateFrom(ValueLayout.JAVA_INT)
                val ptr = ts_language_supertypes(language, length)

                val len = length.getAtIndex(ValueLayout.JAVA_INT, 0)

                if (len == 0) {
                    UShortArray(0)
                } else {
                    val arr = ptr.reinterpret(len * ValueLayout.JAVA_SHORT.byteSize())
                    arr.toArray(ValueLayout.JAVA_SHORT).toUShortArray()
                }
            }
        }

    public fun subtypesForSupertype(supertype: UShort): UShortArray {
        return unsafe {
            val length = allocateFrom(ValueLayout.JAVA_INT)
            val ptr = ts_language_subtypes(language, supertype, length)

            val len = length.getAtIndex(ValueLayout.JAVA_INT, 0)

            if (len == 0) {
                UShortArray(0)
            } else {
                val arr = ptr.reinterpret(len * ValueLayout.JAVA_SHORT.byteSize())
                arr.toArray(ValueLayout.JAVA_SHORT).toUShortArray()
            }
        }
    }

    public fun nodeKindForID(id: UShort): String {
        return ts_language_symbol_name(language, id).getString(0)
    }

    public fun idForNodeKind(name: String, named: Boolean): UShort {
        return unsafe {
            val n = allocateFrom(name)
            ts_language_symbol_for_name(language, n, name.length.toUInt(), named)
        }
    }

    public fun nodeKindIsNamed(id: UShort): Boolean {
        return ts_language_symbol_type(language, id) == TSSymbolType.Regular
    }

    public fun nodeKindIsVisible(id: UShort): Boolean {
        return ts_language_symbol_type(language, id).value <= TSSymbolType.Anonymous.value
    }

    public fun nodeKindIsSupertype(id: UShort): Boolean {
        return ts_language_symbol_type(language, id) == TSSymbolType.Supertype
    }

    public fun fieldNameForID(fieldID: UShort): String {
        return ts_language_field_name_for_id(language, fieldID).getString(0)
    }


    public fun fieldIDForName(name: String): UShort {
        return unsafe {
            val n = allocateFrom(name)
            ts_language_field_id_for_name(language, n, name.length.toUInt())
        }
    }

    public fun nextState(state: UShort, id: UShort): UShort {
        return ts_language_next_state(language, state, id)
    }

    public fun delete() {
        ts_language_delete(language)
    }

    override fun equals(other: Any?): Boolean {
        return this === other || other is Language && language == other.language
    }

    override fun hashCode(): Int {
        return language.hashCode()
    }

    public companion object {

        private val languageMethodDesc = FunctionDescriptor.of(ValueLayout.ADDRESS)
        private val languageCache = hashMapOf<String, Language>()


        public fun getLanguage(languageId: String): Language {

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