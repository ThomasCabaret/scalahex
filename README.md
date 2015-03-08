//Work in progress, right now nothing works
Hex game API written in scala.
It is functional, immutable, and free of side effects.

INSTALL
-------

Build dependencies

    git clone https://github.com/ornicar/scalalib
    cd scalalib
    sbt publish-local

Clone scalahex

    git clone git://github.com/ThomasCabaret/scalahex

Get latest sbt on http://www.scala-sbt.org/download.html

Start sbt in scalahex directory

    sbt

In the sbt shell, to compile scalahex, run

    compile

To run the tests:

    test
