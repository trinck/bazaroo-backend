package org.mts.keycloak;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerConfig {


    private static final String TOPIC = "keycloak-user-events";
    private final KafkaProducer<String, String> producer;

    public KafkaProducerConfig() {
        Properties props = new Properties();
        String bootstrapServers = System.getenv("KAFKA_BOOTSTRAP_SERVERS");
        if (bootstrapServers == null || bootstrapServers.isEmpty()) {
            bootstrapServers = "localhost:9092"; // Valeur par défaut (dev)
        }
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
    }

    public void sendMessage(String topic,String message) {
        producer.send(new ProducerRecord<>(topic, message));
    }

    public void close() {
        producer.close();
    }

}
