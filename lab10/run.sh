#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
find src -name '*.java' | sort > out/sources.txt
javac --release 17 -encoding UTF-8 -d out @out/sources.txt
java -cp out Main
