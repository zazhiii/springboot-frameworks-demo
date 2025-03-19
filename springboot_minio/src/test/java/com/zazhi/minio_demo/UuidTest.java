package com.zazhi.minio_demo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author zazhi
 * @date 2025/3/19
 * @description: TODO
 */
public class UuidTest {
    @Test
    public void test(){
        System.out.println(UUID.randomUUID().toString());
    }
}
