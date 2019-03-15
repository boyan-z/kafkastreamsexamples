package com.endava.examplekafka.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class Message {
    private String sender;
    private String body;
    private String receiver;
    private Instant timestamp;
}
