# Hadoop maven examples
- Docker env: https://github.com/hibuz/ubuntu-docker/tree/main/hadoop
- Reference: https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html#Example:_WordCount_v1.0

## Build
``` bash
git clone https://github.com/hibuz/hadoop-example
cd hadoop-example
./mvnw package

# Build error when docker mount after checkout from windows(crlf -> lf)
git config core.autocrlf false
git checkout mvnw
```

## Prepare input files into the distributed filesystem
``` bash
# Make the HDFS directories
hdfs dfs -mkdir -p /user/hadoop/wordcount/input

# Create the input files
echo "Hello World Bye World" | hdfs dfs -put - wordcount/input/file01
echo "Hello Hadoop Goodbye Hadoop" | hdfs dfs -put - wordcount/input/file02
```

## Run some of the examples provided:
``` bash
# WordCount Example
hadoop jar target/examples-0.0.1-SNAPSHOT.jar wordcount wordcount/input wordcount/output
# View the output files on the distributed filesystem:
hdfs dfs -cat wordcount/output/*
# Result of the output files 
Bye	1
Goodbye	1
Hadoop	2
Hello	2
World	2

# Grep Example
hadoop jar target/examples-0.0.1-SNAPSHOT.jar grep wordcount/input output '([G-H])\w+'
# View the output files on the distributed filesystem:
hdfs dfs -cat output/*
# Result of the output files 
2	Hello
2	Hadoop
1	Goodbye
```
