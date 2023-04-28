module ConsumerModule {

  import opened LibraryModule

  method Main() {
    var n := 21;
    var TwoN := TimesTwo(n);
    print "Two times ", n, " is ", TwoN, "\n";
  }
}
