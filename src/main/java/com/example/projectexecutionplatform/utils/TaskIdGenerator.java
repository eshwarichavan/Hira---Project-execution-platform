package com.example.projectexecutionplatform.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskIdGenerator {

    // for sprint_id generation :
    public static String generate() {
        return "TASK-" + UUID.randomUUID().toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
