# languages-dictionary-mapreduce
A test using Hadoop MapReduce to generate a multi-lingual dictionary

This project was implemented over Cloudera Single Node Hadoop VM:
http://www.cloudera.com/content/www/en-us/downloads/quickstart_vms/5-4.html

On terminal:

Set up CLASSPATH var:
```
export CLASSPATH=/usr/lib/hadoop/client-0.20/\*:/usr/lib/hadoop\*
```

Transfer data to HDFS:
```
hadoop fs -mkdir /user/cloudera/translator
hadoop fs -mkdir /user/cloudera/translator/input
hadoop fs -put en /user/cloudera/translator/input
hadoop fs -put es /user/cloudera/translator/input
hadoop fs -put fr /user/cloudera/translator/input
```

Compile:
```
javac -d classes/ Translator.java
```

Generate .jar:
```
jar -cvf translator.jar -C classes/ .
```

Run:
```
hadoop jar translator.jar org.myorg.Translator /user/cloudera/translator/input /user/cloudera/translator/output
```
