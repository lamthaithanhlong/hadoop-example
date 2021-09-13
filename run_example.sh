#!/usr/bin/env bash

# WordCount Example
hadoop jar target/examples-0.0.1-SNAPSHOT.jar wordcount wordcount/input wordcount/output

# View the output files on the distributed filesystem:
hdfs dfs -cat wordcount/output/*
