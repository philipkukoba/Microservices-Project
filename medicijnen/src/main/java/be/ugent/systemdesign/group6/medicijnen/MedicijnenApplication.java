package be.ugent.systemdesign.group6.medicijnen;

import be.ugent.systemdesign.group6.medicijnen.API.bus.Channels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@EnableAsync
@EnableScheduling
@EnableBinding(Channels.class)
@SpringBootApplication
public class MedicijnenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicijnenApplication.class, args);
    }

    // https://howtodoinjava.com/spring-boot2/resttemplate/spring-restful-client-resttemplate-example/
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }
}
