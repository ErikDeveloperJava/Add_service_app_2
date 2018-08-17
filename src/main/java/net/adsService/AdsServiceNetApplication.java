package net.adsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:field-error.properties","classpath:file-config.properties"})
public class AdsServiceNetApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdsServiceNetApplication.class, args);
    }
}