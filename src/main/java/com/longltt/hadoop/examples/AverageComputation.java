package com.longltt.hadoop.examples;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class AverageComputation {

  public static class LogMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Text ipAddress = new Text();
    private IntWritable quantity = new IntWritable();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      String line = value.toString();
      String[] fields = line.split("\\s+");
      String ip = line.split(" ")[0];
      if (fields.length >= 2 && line.matches("(\\d{1,3}\\.){3}\\d{1,3}\\s.+")) {
        try {
          Integer comp = Integer.parseInt(line.split(" ")[line.split(" ").length - 1]);
          ipAddress.set(ip);
          quantity.set(comp);
          context.write(ipAddress, quantity);
        } catch (NumberFormatException e) {
          // ignore lines that do not contain a valid integer
        }
      }
    }
  }

  public static class LogReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
        throws IOException, InterruptedException {
      int sum = 0;
      int count = 0;
      for (IntWritable value : values) {
        sum += value.get();
        count++;
      }
      if (count > 0) {
        float average = ((float) sum) / count;
        context.write(key, new FloatWritable(average));
      }
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length < 2) {
      System.err.println("Usage: log average <in> [<in>...] <out>");
      System.exit(2);
    }

    FileSystem hdfs = FileSystem.get(conf);
    Path output = new Path(args[1]);
    if (hdfs.exists(output)) {
      hdfs.delete(output, true);
    }

    Job job = Job.getInstance(conf, "log average");
    job.setJarByClass(AverageComputation.class);
    job.setMapperClass(LogMapper.class);
    job.setReducerClass(LogReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FloatWritable.class);
    job.setInputFormatClass(TextInputFormat.class);
    TextInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}