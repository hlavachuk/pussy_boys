#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
find src tests -name '*.java' | sort > out/test-sources.txt
javac --release 17 -encoding UTF-8 -d out @out/test-sources.txt
java -cp out RegressionTests
