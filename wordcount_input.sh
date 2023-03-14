#!/bin/bash

# Make the HDFS directories
hdfs dfs -mkdir -p /user/hadoop/wordcount/input

# Set default input values
default_input1="Hello World Bye World"
default_input2="Hello Hadoop Goodbye Hadoop"
input_dir="wordcount/input"
file01="$input_dir/file01"
file02="$input_dir/file02"

# Ask user for input
echo "Which input file(s) would you like to use?"
echo "Enter 1 for input 1 only"
echo "Enter 2 for input 2 only"
echo "Enter 12 for both inputs"
read input_choice

# Process input choice
if [[ "$input_choice" == "1" || "$input_choice" == "12" ]]; then
    # Check if external input 1 file is specified
    echo "Would you like to use an external file for input 1? (y/n)"
    read external_input1_choice

    if [[ "$external_input1_choice" == "y" ]]; then
        echo "Please enter the path to the external input 1 file:"
        read external_input1_file

        # Check if file exists
        if [[ -f "$external_input1_file" ]]; then
            echo "Putting contents of $external_input1_file into HDFS input 1 file"
            cat $external_input1_file | hdfs dfs -put - $file01
        else
            echo "File not found, using default input 1"
            echo "$default_input1" | hdfs dfs -put - $file01
        fi
    else
        echo "Using default input 1"
        echo "$default_input1" | hdfs dfs -put - $file01
    fi
fi

if [[ "$input_choice" == "2" || "$input_choice" == "12" ]]; then
    # Check if external input 2 file is specified
    echo "Would you like to use an external file for input 2? (y/n)"
    read external_input2_choice

    if [[ "$external_input2_choice" == "y" ]]; then
        echo "Please enter the path to the external input 2 file:"
        read external_input2_file

        # Check if file exists
        if [[ -f "$external_input2_file" ]]; then
            echo "Putting contents of $external_input2_file into HDFS input 2 file"
            cat $external_input2_file | hdfs dfs -put - $file02
        else
            echo "File not found, using default input 2"
            echo "$default_input2" | hdfs dfs -put - $file02
        fi
    else
        echo "Using default input 2"
        echo "$default_input2" | hdfs dfs -put - $file02
    fi
fi

# Ask user if they want to remove input 1 or input 2
if [[ "$input_choice" == "1" || "$input_choice" == "12" ]]; then
    echo "Would you like to remove input 1? (y/n)"
    read remove_input1_choice

    if [[ "$remove_input1_choice" == "y" ]]; then
        echo "Removing input 1"
        hdfs dfs -rm $file01
    fi
fi

if [[ "$input_choice" == "2" || "$input_choice" == "12" ]]; then
    echo "Would you like to remove input 2? (y/n)"
    read remove_input2_choice

    if [[ "$remove_input2_choice" == "y" ]]; then
        echo "Removing input 2"
        hdfs dfs -rm $file02
    fi
fi
