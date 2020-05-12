package kr.geun.forblog.jkc.basic;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * BasicKafkaProducer
 *
 * @author akageun
 * @since 2020-05-08
 */
@Slf4j
public class BasicKafkaProducer extends BaseBasicKafka {

    private void run() {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig());) {

            while (true) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Kafka Producer : ");
                String record = sc.nextLine();

                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, record);
                sync(producer, producerRecord);
                //async(producer, producerRecord);

            }
        }
    }

    private void sync(KafkaProducer<String, String> producer, ProducerRecord<String, String> producerRecord) {
        Future<RecordMetadata> recordMetadata = producer.send(producerRecord);

        try {
            RecordMetadata metadata = recordMetadata.get();
            log.info("topic : {}, partition : {}, offset : {}, timestamp : {}", metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void async(KafkaProducer<String, String> producer, ProducerRecord<String, String> producerRecord) {
        producer.send(producerRecord, (metadata, e) -> {
            if (e != null) {
                log.error(e.getMessage(), e);
                return;
            }

            log.info("topic : {}, partition : {}, offset : {}, timestamp : {}", metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
        });
    }

    public static void main(String[] args) {
        new BasicKafkaProducer().run();
    }
}
