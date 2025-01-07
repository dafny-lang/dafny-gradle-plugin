module Foo {
  datatype Bar = Create(baz: string) {
        // Wrong indentation
                const x: string := baz
  }
}
