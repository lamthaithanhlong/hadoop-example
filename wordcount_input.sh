#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Usage: $0 <input_dir>"
    exit 1
fi

INPUT_DIR=$1

# Make the HDFS directories
hdfs dfs -mkdir -p /user/hadoop/$INPUT_DIR/input

input_dir=$INPUT_DIR"/input"
file01="$input_dir/file01"
file02="$input_dir/file02"

# Check if the files already exist and remove them if necessary
if hdfs dfs -test -e "$file01"; then
    read -p "File $file01 already exists. Do you want to remove it? (y/n) " remove_file1
    if [ "$remove_file1" == "y" ]; then
        hdfs dfs -rm "$file01"
        echo "Removed $file01"
    fi
fi

if hdfs dfs -test -e "$file02"; then
    read -p "File $file02 already exists. Do you want to remove it? (y/n) " remove_file2
    if [ "$remove_file2" == "y" ]; then
        hdfs dfs -rm "$file02"
        echo "Removed $file02"
    fi
fi

# Default input files
input1="Hello World Bye World"
input2="Hello Hadoop Goodbye Hadoop"

# Check if the user wants to use external file for input 1
read -p "Do you want to use an external file for input 1? (y/n) " use_external_input1
if [ "$use_external_input1" == "y" ]; then
    read -p "Enter the path of the external file: " external_input1_file
    if [ -f "$external_input1_file" ]; then
        hdfs dfs -put "$external_input1_file" "$file01"
    else
        echo "File not found, using default input 1"
        echo "$input1" | hdfs dfs -put - "$file01"
    fi
else
    echo "$input1" | hdfs dfs -put - "$file01"
fi

# Check if the user wants to use external file for input 2
read -p "Do you want to use an external file for input 2? (y/n) " use_external_input2
if [ "$use_external_input2" == "y" ]; then
    read -p "Enter the path of the external file: " external_input2_file
    if [ -f "$external_input2_file" ]; then
        hdfs dfs -put "$external_input2_file" "$file02"
    else
        echo "File not found, using default input 2"
        echo "$input2" | hdfs dfs -put - "$file02"
    fi
else
    echo "$input2" | hdfs dfs -put - "$file02"
fi

# Print the contents of the input files
if hdfs dfs -test -e "$file01"; then
    echo "Contents of input 1:"
    hdfs dfs -cat "$file01"
fi

if hdfs dfs -test -e "$file02"; then
    echo "Contents of input 2:"
    hdfs dfs -cat "$file02"
fi
