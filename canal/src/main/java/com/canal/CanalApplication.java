package com.canal;

import com.canal.config.CanalUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;
@EnableAsync
@SpringBootApplication
public class CanalApplication implements CommandLineRunner {

    @Resource
    private CanalUtil canalUtil;

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class,args);
    }
    @Override
    public void run(String... strings){
        canalUtil.startMonitorSQL();
    }

}
