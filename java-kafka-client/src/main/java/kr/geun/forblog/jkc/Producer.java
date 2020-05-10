package kr.geun.forblog.jkc;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;

/**
 * Producer
 *
 * @author akageun
 * @since 2020-05-06
 */
@Slf4j
public class Producer {

    public static void main(String[] args) throws IOException {
        Properties configs = new Properties();
        configs.put("bootstrap.servers", "localhost:9092"); // kafka host 및 server 설정
        configs.put("acks", "-1");                         // 자신이 보낸 메시지에 대해 카프카로부터 확인을 기다리지 않습니다.
        //configs.put("block.on.buffer.full", "true");        // 서버로 보낼 레코드를 버퍼링 할 때 사용할 수 있는 전체 메모리의 바이트수
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");   // serialize 설정
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // serialize 설정
        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(configs);) {
            log.info("Test!!");
            // message 전달
            for (int i = 0; i < 5; i++) {
                String v = "hello" + i;
                RecordMetadata meta = producer.send(new ProducerRecord<>("test20180604", v)).get();
                System.out.println("Test!! : " + meta.offset());
            }
        } catch (Exception e) {
            System.out.println("e :" + e.getMessage() + "/" + e);
        }

        System.out.println("?????");
    }

}

