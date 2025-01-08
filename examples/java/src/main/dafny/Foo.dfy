module Foo {
  datatype Bar = Create(baz: string) {
    const x: string := baz
  }
}