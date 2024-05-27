# Kotlin Tree Sitter SDK

Kotlin sdk for [tree-sitter](https://tree-sitter.github.io/tree-sitter/)

## Usage

### Load native libraries

```kotlin
System.load("/absolute/path/to/tree-sitter.*")
System.load("/absolute/path/to/tree-sitter-<language>.*")
```

### Obtain a language

```kotlin
val languageMethod = Linker.nativeLinker().downcallHandle(
    RuntimeHelper.symbolLookup.find("tree_sitter_<languageId>").get(),
    FunctionDescriptor.of(ValueLayout.ADDRESS)
)

val language: Pointer<TSLanguage> = languageMethod.invokeExact() as MemorySegment
```

All api are identical to c api

[Using Parser - Get Started](https://tree-sitter.github.io/tree-sitter/using-parsers)