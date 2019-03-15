package com.endava.examplekafka.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class Message {
    private String sender;
    private String receiver;
    private String subject;
    private String body;
    private Instant timestamp;
}
