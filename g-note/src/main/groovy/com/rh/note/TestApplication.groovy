package com.rh.note

class TestApplication {
    static main(args) {
        println test1() && test2() || test3() && test4()
        println (test1() && test2()) || (test3() && test4())
        println test1() && (test2() || test3()) && test4()
    }

    static boolean test1() {
        true
    }

    static boolean test2() {
        true
    }

    static boolean test3() {
        false
    }

    static boolean test4() {
        false
    }
}
