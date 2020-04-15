package com.example.demo.system.shardingsphere;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KeyGeneratorUtils {
    final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static Long generateKey() {
        return Long.valueOf(dtf.format(LocalDateTime.now()));
    }
}
