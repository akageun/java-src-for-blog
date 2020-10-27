package kr.geun.forblog.pure.basic;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.requests.IsolationLevel;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

/**
 * BaseTestKafka
 *
 * @author akageun
 * @since 2020-10-27
 */
abstract class BaseTestKafka {

    protected static final String KAFKA_BROKERS = "localhost:9095,localhost:9096,localhost:9097";
    protected static final String CONSUMER_GROUP_ID_CONFIG = "my_test_group_1";
    protected static final String PRODUCER_CLIENT_ID = "client1";
    protected static final String CONSUMER_CLIENT_ID = "client1";
    protected static final String TOPIC = "basic-test-6";

    protected Properties getProducerConfig() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, PRODUCER_CLIENT_ID + UUID.randomUUID().toString());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // exactly once producer setup
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put("transactional.id", "my-transactional-id");

        return props;
    }

    protected Properties getConsumerConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, CONSUMER_CLIENT_ID + UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID_CONFIG);

        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, IsolationLevel.READ_COMMITTED.toString().toLowerCase(Locale.ROOT));


        return props;
    }
}
