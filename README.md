# Building the Twitter Classifier Assembly

Steps for building the assembly:  `$ ./sbt/sbt clean assembly`

Upon successfully building the assembly, you can classify the tweet stream from Twitter using K-means algorithm

# Running the Twitter Classifier

The model is trained with K-means using 10 cluster, 20 iteration, and the sample is in ./tweets/

Command : spark-submit --class  SCALA-OBJECT --master $YOU_SPARK_MASTER JARFILE CLUSTER_NUMBER

Example : `$ spark-submit --class Predict  --master local[4]  target/scala-2.10/spark-twitter-lang-classifier-assembly-1.0.jar ./model 7 `

The default setting is to use Spark 1.4.  You should able to run this coding with other versions of Spark 1.X and higher by changing the Spark version in the build file (assuming no API changes).
