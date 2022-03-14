package com.transaction;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.transaction.mapper")
public class TransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class,args);
    }
}
