package com.longltt.hadoop.examples;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<LongWritable, Text, Text, MapWritable> {

	HashMap<String, MapWritable> hashmap;

	@Override
	protected void setup(
			Mapper<LongWritable, Text, Text, MapWritable>.Context context)
			throws IOException, InterruptedException {
		hashmap = new HashMap<String, MapWritable>();
	}

	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, MapWritable>.Context context)
			throws IOException, InterruptedException {
		String line = value.toString().trim();
		String[] input = line.split(" ");

		for (int i = 0; i < input.length; i++) {
			for (int j = i + 1; j < input.length && !input[i].equals(input[j]); j++) {

				MapWritable mapWritable = hashmap.get(input[i]);
				if (mapWritable == null) {
					mapWritable = new MapWritable();
					hashmap.put(input[i], mapWritable);
				}

				if (mapWritable.get(new Text(input[j])) == null) {
					mapWritable.put(new Text(input[j]), new IntWritable(1));
				} else {
					IntWritable inWritable = (IntWritable) mapWritable
							.get(new Text(input[j]));
					mapWritable.put(new Text(input[j]), new IntWritable(
							inWritable.get() + 1));
				}
			}
		}
	}

	@Override
	protected void cleanup(
			Mapper<LongWritable, Text, Text, MapWritable>.Context context)
			throws IOException, InterruptedException {
		super.cleanup(context);
		for (Entry<String, MapWritable> entry : hashmap.entrySet()) {
			context.write(new Text(entry.getKey()), entry.getValue());
		}
	}
}
