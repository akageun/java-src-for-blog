package kr.geun.forblog.pj.basic;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;

/**
 * BasicKafkaConsumer
 *
 * @author akageun
 * @since 2020-05-08
 */
@Slf4j
public class BasicKafkaConsumer extends BaseBasicKafka {

    private void run() {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerConfig());) {
            consumer.subscribe(Arrays.asList(TOPIC));      // topic 설정

            log.info("Subscribed to topic : {}", TOPIC);


            while (true) {  // 계속 loop를 돌면서 producer의 message를 띄운다.
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));

                consumer.commitSync();
                for (ConsumerRecord<String, String> record : records) {
                    log.info("topic : {}, partition : {}, offset : {}, key : {}, value : {} \n {}", record.topic(), record.partition(), record.offset(), record.key(), record.value(), record);


                }
            }
        }
    }

    public static void main(String[] args) {
        new BasicKafkaConsumer().run();
    }
}
