package com.longltt.hadoop.examples;

import org.apache.hadoop.util.ProgramDriver;

public class ExampleDriver {

  public static void main(String argv[]) {
    int exitCode = -1;
    ProgramDriver pgd = new ProgramDriver();
    try {
      pgd.addClass("wordcount", WordCount.class,
          "A map/reduce program that counts the words in the input files.");
      pgd.addClass("grep", Grep.class,
          "A map/reduce program that counts the matches of a regex in the input.");
      pgd.addClass("averagecomputation", AverageComputation.class,
          "Implement Average Computation Algorithm to compute the average of the last quantity (7352) in a Apache log.");
      pgd.addClass("linecount", InMapperCombiningWordCount.class,
          "count the number of lines that contain the IP address 64.242.88.10 in the input file");
      pgd.addClass("stripe", CrystalBallStripe.class, "This is the example of word stripe");
      pgd.addClass("pair", CrystalBallPair.class, "This is the example of word pair");
      exitCode = pgd.run(argv);
    } catch (Throwable e) {
      e.printStackTrace();
    }

    System.exit(exitCode);
  }
}