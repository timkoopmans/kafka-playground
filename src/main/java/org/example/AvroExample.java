package org.example;

import org.apache.kafka.clients.producer.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AvroExample {

    public static void main(final String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Please provide the configuration file path as a command line argument");
            System.exit(1);
        }

        // Load producer configuration settings from a local file
        final Properties props = loadConfig(args[0]);
        final String topic = "svt.raw.rfsb.tclnmst";

        Producer<String, String> producer = new KafkaProducer<>(props);

        System.out.println(producer.metrics().toString());

//            producer.send(
//                    new ProducerRecord<>(topic, user, item),
//                    (event, ex) -> {
//                        if (ex != null)
//                            ex.printStackTrace();
//                        else
//                            System.out.printf("Produced event to topic %s: key = %-10s value = %s%n", topic, user, item);
//                    });

        producer.flush();
        System.out.printf("events was produced to topic %s%n", topic);
        producer.close();
    }


    /**
     * We'll reuse this function to load properties from the Consumer as well
     */
    public static Properties loadConfig(final String configFile) throws IOException {
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            cfg.load(inputStream);
        }
        return cfg;
    }
}
