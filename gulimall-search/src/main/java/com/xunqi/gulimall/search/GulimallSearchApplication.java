package com.xunqi.gulimall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

<<<<<<< HEAD
/**
 * @author Jerry
 */

=======
>>>>>>> 72bc0255c064281d23566ef8d7c84b1a747d7842
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class GulimallSearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(GulimallSearchApplication.class, args);
  }
}
