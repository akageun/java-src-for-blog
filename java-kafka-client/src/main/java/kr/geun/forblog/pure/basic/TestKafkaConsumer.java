package kr.geun.forblog.pure.basic;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.requests.IsolationLevel;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * TestKafkaConsumer
 *
 * @author akageun
 * @since 2020-10-27
 */
@Slf4j
public class TestKafkaConsumer extends BaseTestKafka {

    private void run() {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerConfig());) {
            consumer.subscribe(Arrays.asList(TOPIC));      // topic 설정

            log.info("Subscribed to topic : {}", TOPIC);


            while (true) {  // 계속 loop를 돌면서 producer의 message를 띄운다.
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : records) {

                    try {

                        log.info("topic : {}, partition : {}, offset : {}, key : {}, value : {} \n {}", record.topic(), record.partition(), record.offset(), record.key(), record.value(), record);

                        consumer.commitSync();


                    } catch (CommitFailedException e) {
                        log.error(e.getMessage(), e);
                    }


                }
            }
        }
    }

    private void test() {

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerConfig());) {
            consumer.subscribe(Arrays.asList(TOPIC));      // topic 설정
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));

            Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();
            log.info("records ; {}", records.partitions());
            for (TopicPartition partition : records.partitions()) {
                List<ConsumerRecord<String, String>> partitionedRecords = records.records(partition);
                long offset = partitionedRecords.get(partitionedRecords.size() - 1).offset();
                offsetsToCommit.put(partition, new OffsetAndMetadata(offset + 1));
            }

            log.info("offsetsToCommit : {}", offsetsToCommit);
        }


    }

    private void seek() {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, CONSUMER_CLIENT_ID + UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID_CONFIG);

        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, IsolationLevel.READ_UNCOMMITTED.toString().toLowerCase(Locale.ROOT));

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);) {
            TopicPartition partition = new TopicPartition(TOPIC, 0);
            consumer.assign(Arrays.asList(partition));
            consumer.seek(partition, 78);

            while (true) {  // 계속 loop를 돌면서 producer의 message를 띄운다.
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : records) {

                    try {

                        log.info("topic : {}, partition : {}, offset : {}, key : {}, value : {} \n {}", record.topic(), record.partition(), record.offset(), record.key(), record.value(), record);
                        consumer.commitSync();


                    } catch (CommitFailedException e) {
                        log.error(e.getMessage(), e);
                    }


                }
            }
        }

    }

    public static void main(String[] args) {
        new TestKafkaConsumer().seek();
        //new TestKafkaConsumer().run();
    }
}
