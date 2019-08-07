package com.arno.miaoshao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @Author arno
 * @date 2019-08-04 23:03
 */
@Slf4j
@SpringBootApplication
public class DataSourceDemoApplicationBackup implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;
    public static void main(String[] args) {
        SpringApplication.run(DataSourceDemoApplicationBackup.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        log.info(dataSource.toString());
        Connection connection = dataSource.getConnection();

        log.info(connection.toString());
        connection.close();
    }
}
