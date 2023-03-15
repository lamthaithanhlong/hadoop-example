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
      pgd.addClass("linecount", LineCount.class,
          "count the number of lines that contain the IP address 64.242.88.10 in the input file");
      pgd.addClass("linecounte2", LineCountEx2.class,
          "count the number of lines that contain the 64.242.88.10 - - [07/Mar/2004:16:1:58 -0800] GET /twiki/bin/view/TWiki/WikiSyntax HTTP/1.1 200 7352");
      pgd.addClass("stripe", WordCountStripe.class, "This is the example of word stripe");
      exitCode = pgd.run(argv);
    } catch (Throwable e) {
      e.printStackTrace();
    }

    System.exit(exitCode);
  }
}