package examplekafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaStreamsInApplicationDLQ {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsInApplicationDLQ.class, args);
    }
}
