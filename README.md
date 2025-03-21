# Kotlin Tree Sitter SDK

Kotlin sdk for [tree-sitter](https://tree-sitter.github.io/tree-sitter/)

## Install

### Prepare `tree-sitter` binaries

Prepare binaries with tree-sitter and required languages.

[Optional prebuilt binaries](https://github.com/Endoqa/tree-sitter-prebuilt)

### Add maven repository

```
https://maven.endoqa.io
```

### Add dependencies

#### C-style api

```
io.endoqa:libtree_sitter:0.0.8
```

#### Object-style api:

```
io.endoqa:tree-sitter:0.0.8
```

## Before Start

### Load native libraries

```kotlin
System.load("/absolute/path/to/tree-sitter.*")
System.load("/absolute/path/to/tree-sitter-<language>.*")
```

Pick api style:

- [Kotlin](#get-started)
- [C-style](#get-started-c-style)

## Get Started

```kotlin
val parser = Parser()
val clang = Language.getLanguage("c")
parser.setLanguage(clang)

val tree = parser.parse(sourceCode)
val rootNode = tree.rootNode
```

## Get Started (C-Style)

All api are identical to c api

[Using Parser - Get Started](https://tree-sitter.github.io/tree-sitter/using-parsers)

### Obtain a language

```kotlin
val languageMethod = Linker.nativeLinker().downcallHandle(
    RuntimeHelper.symbolLookup.find("tree_sitter_<languageId>").get(),
    FunctionDescriptor.of(ValueLayout.ADDRESS)
)

val language: Pointer<TSLanguage> = languageMethod.invokeExact() as MemorySegment
```

### Use parser

```kotlin
val parser = ts_parser_new()
ts_parser_set_language(parser, language)
val source: MemorySegment = TODO("allocate source")
ts_parser_parse_string(
    parser,
    MemorySegment.NULL,
    source,
    sourceLen
)
val rootNode = ts_tree_root_node(tree)
ts_tree_delete(tree)
ts_parser_delete(parser)
```

