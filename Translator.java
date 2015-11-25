package org.myorg; 	
 	import java.io.IOException;
 	import java.util.*;
 	
 	import org.apache.hadoop.fs.Path;
 	import org.apache.hadoop.conf.*;
 	import org.apache.hadoop.io.*;
 	import org.apache.hadoop.mapred.*;
 	import org.apache.hadoop.util.*;
 	
 	public class Translator {
 	
 	   public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

 	     public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
               String filename = ((FileSplit) reporter.getInputSplit()).getPath().getName();
	       String line = value.toString();
	       String[] items = line.split(",",-1); 
 	       output.collect(new Text(items[0]), new Text(items[1]+"("+filename+")"));
 	       
 	     }
 	   }
 	
 	   public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
 	     public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
 	       String translations = "";
 	       while (values.hasNext()) {
 	         translations += values.next().toString();
 	       }
 	       output.collect(key, new Text(translations));
 	     }
 	   }
 	
 	   public static void main(String[] args) throws Exception {
 	     JobConf conf = new JobConf(Translator.class);
 	     conf.setJobName("translate");
 	
 	     conf.setOutputKeyClass(Text.class);
 	     conf.setOutputValueClass(Text.class);
 	
 	     conf.setMapperClass(Map.class);
 	     conf.setCombinerClass(Reduce.class);
 	     conf.setReducerClass(Reduce.class);
 	
 	     conf.setInputFormat(TextInputFormat.class);
 	     conf.setOutputFormat(TextOutputFormat.class);
 	
 	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
 	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
 	
 	     JobClient.runJob(conf);
 	   }
 	}
