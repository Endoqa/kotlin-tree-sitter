package tree_sitter

import lib.tree_sitter.TSLanguageMetadata

data class LanguageMetadata(
    val majorVersion: UByte,
    val minorVersion: UByte,
    val patchVersion: UByte
) {

    companion object {
        fun from(v : TSLanguageMetadata) = LanguageMetadata(v.major_version, v.minor_version, v.patch_version)
    }

}