package arda.stream;

import org.apache.spark.*;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.mqtt.MQTTUtils;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		SparkConf sparkConf = new SparkConf()
				.setAppName("SparkStreamingMqttTest").setMaster("local[4]")
				.set("spark.driver.allowMultipleContexts", "true");
		
		// spark streaming context with a 10 second batch size
		JavaStreamingContext ssc = new JavaStreamingContext(sparkConf,
				Durations.seconds(10));

		// Define MQTT url and topic
		String brokerUrl = args[0];//"tcp://localhost:1883";
		String topic = args[1];//"/csr";

		// collect MQTT data using streaming context and MQTTUtils library
		JavaReceiverInputDStream<String> lines = MQTTUtils.createStream(ssc,
				brokerUrl, topic, StorageLevels.MEMORY_AND_DISK_SER);

		// display collected MQTT batch messages
		lines.print();
	}
}
