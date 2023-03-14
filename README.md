# Hadoop maven examples

## Build
``` bash
git clone https://github.com/hibuz/hadoop-example

cd hadoop-example

# Run and Execute bash on docker container
./docker_up.sh

# Build in the docker container
./mvnw package
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

## Modify source to double count:
```java
// WordCount.java(reduce func)
...
    for (IntWritable val : values) {
        sum += val.get() * 2;  // modify this line
    }
...
```

```bash
#build and run using shell script
./run_example.sh

...
[INFO] BUILD SUCCESS
...
2022-02-27 07:43:00,199 INFO mapreduce.Job: Job job_1645946721365_0006 completed successfully
...
Bye     2
Goodbye 2
Hadoop  4
Hello   4
World   4
```

## Stops containers and removes containers, networks, and volumes
``` bash
exit

./docker_down.sh -v

[+] Running 3/3
 ⠿ Container hadoop                  Removed
 ⠿ Volume hadoop-example_hadoop-vol  Removed
 ⠿ Network hadoop-example_default    Removed
 ```

## Run  ./main.sh linecount



 # Reference
- Docker env: https://github.com/hibuz/ubuntu-docker/tree/main/hadoop
- Reference: https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html#Example:_WordCount_v1.0
