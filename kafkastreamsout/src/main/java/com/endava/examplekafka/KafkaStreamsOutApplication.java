package com.endava.examplekafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class KafkaStreamsOutApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsOutApplication.class, args);
    }
}
