#!/bin/bash

# Make the HDFS directories
hdfs dfs -mkdir -p /user/hadoop/wordcount/input

# Create the input files
echo "Hello World Bye World" | hdfs dfs -put - wordcount/input/file01
echo "Hello Hadoop Goodbye Hadoop" | hdfs dfs -put - wordcount/input/file02

hdfs dfs -cat wordcount/input/*