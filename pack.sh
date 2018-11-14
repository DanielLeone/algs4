#!/usr/bin/env bash

# This script takes a copy of java files, strips the package line, and zips them up

set -e

mkdir -p build
rm ./build/*

cp ./src/com/danielleone/kdtree/KdTree.java ./build/KdTree.java
cp ./src/com/danielleone/kdtree/PointSET.java ./build/PointSET.java

sed -i .bak '/package com/d' ./build/KdTree.java
sed -i .bak '/package com/d' ./build/PointSET.java

rm -f ./build/*.bak
rm -f ./build/*.zip

zip -r ./build/archive.zip ./build/*

rm -f ./build/*.java