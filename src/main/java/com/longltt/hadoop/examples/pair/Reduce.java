package com.longltt.hadoop.examples.pair;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, MapWritable, Text, myMapWritable> {

	@Override
	protected void reduce(Text item, Iterable<MapWritable> values,
			Reducer<Text, MapWritable, Text, myMapWritable>.Context context)
			throws IOException, InterruptedException {

		MapWritable sumMap = new MapWritable();
		double total = 0.0;
		for (MapWritable v : values) {
			for (Entry<Writable, Writable> entry : v.entrySet()) {
				if (sumMap.containsKey(entry.getKey())) {
					int t = ((IntWritable) sumMap.get(entry.getKey())).get();
					sumMap.put(entry.getKey(), new IntWritable(t
							+ ((IntWritable) entry.getValue()).get()));
				} else {
					sumMap.put(entry.getKey(), entry.getValue());
				}
				total += ((IntWritable) entry.getValue()).get();
			}
		}

		myMapWritable finalMap = new myMapWritable();
		for (Entry<Writable, Writable> entry : sumMap.entrySet()) {
			double r = ((IntWritable) entry.getValue()).get() / total;
			DecimalFormat df = new DecimalFormat("#.###");
			finalMap.put(entry.getKey(), new DoubleWritable(Double.valueOf(df.format(r))));
		}
		context.write(item, finalMap);
	}
}
