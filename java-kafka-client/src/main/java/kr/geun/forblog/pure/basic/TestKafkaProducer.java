package kr.geun.forblog.pure.basic;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * TestKafkaProducer
 *
 * @author akageun
 * @since 2020-10-27
 */
@Slf4j
public class TestKafkaProducer extends BaseTestKafka {

    public static void main(String[] args) throws InterruptedException {
        new TestKafkaProducer().run();
//        Thread.sleep(1000L);
//        new TestKafkaProducer().offsetCommit();
    }

    private void offsetCommit() {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig());) {

            Map<TopicPartition, OffsetAndMetadata> groupCommit = new HashMap<TopicPartition, OffsetAndMetadata>() {
                {
                    put(new TopicPartition(TOPIC, 0), new OffsetAndMetadata(79L ));
                }
            };
            producer.initTransactions();
            producer.beginTransaction();
            producer.sendOffsetsToTransaction(groupCommit, CONSUMER_GROUP_ID_CONFIG);
            producer.commitTransaction();
        }
    }

    private void run() {

        for (int i = 0; i < 10; i++) {
            TransferModel model = TransferModel.builder()
                .id(UUID.randomUUID().toString())
                .name(String.valueOf(i))
                .date(LocalDateTime.now())
                .build();

            try (KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig());) {
                producer.initTransactions();
                producer.beginTransaction();


                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, model.toString());
                Future<RecordMetadata> recordMetadata = producer.send(producerRecord);


                RecordMetadata metadata = recordMetadata.get();
                if (i % 10 != 9) {
                    producer.commitTransaction();
                } else {
                    log.info("no Commit topic : {}, partition : {}, offset : {}, timestamp : {}", metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }


    }

}
