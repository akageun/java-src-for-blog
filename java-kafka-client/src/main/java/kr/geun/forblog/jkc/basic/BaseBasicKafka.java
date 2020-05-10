package kr.geun.forblog.jkc.basic;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * BaseBasicKafka
 *
 * @author akageun
 * @since 2020-05-08
 */
abstract class BaseBasicKafka {

    protected static final String KAFKA_BROKERS = "localhost:9092";
    protected static final String CONSUMER_GROUP_ID_CONFIG = "my-test-group";
    protected static final String CLIENT_ID = "client1";
    protected static final String TOPIC = "basic-topic";

    protected Properties getProducerConfig() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.put(ProducerConfig.ACKS_CONFIG, "all");

        return props;
    }

    protected Properties getConsumerConfig() {
        Properties props = new Properties();


        return props;
    }
}
