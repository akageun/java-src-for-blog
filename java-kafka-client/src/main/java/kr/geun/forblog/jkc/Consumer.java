package kr.geun.forblog.jkc;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Consumer
 *
 * @author akageun
 * @since 2020-05-06
 */
public class Consumer {
    public static void main(String[] args) {

        try {
            Properties properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test_group_id");

            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);    // consumer 생성
            consumer.subscribe(Arrays.asList("test20180604"));      // topic 설정
            while (true) {  // 계속 loop를 돌면서 producer의 message를 띄운다.
                ConsumerRecords<String, String> records = consumer.poll(500);
                for (ConsumerRecord<String, String> record : records) {
                    String s = record.topic();
                    if ("test20180604".equals(s)) {
                        System.out.println(record.value());
                    } else {
                        throw new IllegalStateException("get message on topic " + record.topic());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("e :" + e.getMessage() + "/" + e);
        }

    }
}
