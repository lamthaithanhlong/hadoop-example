#!/usr/bin/env bash

# Build src
./mvnw package

# WordCount Example
hadoop jar target/examples-0.0.1-SNAPSHOT.jar averagecomputation averagecomputation/input averagecomputation/output

# View the output files on the distributed filesystem:
hdfs dfs -cat averagecomputation/output/*
