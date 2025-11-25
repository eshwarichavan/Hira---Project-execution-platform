package com.example.projectexecutionplatform.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SprintIdGenerator {

    // for sprint_id generation :
    public static String generate() {
        return "USR-" + UUID.randomUUID().toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
