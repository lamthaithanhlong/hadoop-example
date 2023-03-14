#!/usr/bin/env bash

if [ $# -ne 1 ]; then
    echo "Usage: $0 <input_dir>"
    exit 1
fi

INPUT_DIR=$1

# Build src
./mvnw package

# WordCount Example
hadoop jar target/examples-0.0.1-SNAPSHOT.jar ${INPUT_DIR} ${INPUT_DIR}/input ${INPUT_DIR}/output

# View the output files on the distributed filesystem:
hdfs dfs -cat ${INPUT_DIR}/output/*
