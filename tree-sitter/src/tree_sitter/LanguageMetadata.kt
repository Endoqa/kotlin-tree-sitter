package tree_sitter

import lib.tree_sitter.TSLanguageMetadata

public data class LanguageMetadata(
    val majorVersion: UByte,
    val minorVersion: UByte,
    val patchVersion: UByte
) {

    public companion object {
        public fun from(v: TSLanguageMetadata): LanguageMetadata {
            return LanguageMetadata(v.major_version, v.minor_version, v.patch_version)
        }
    }

}