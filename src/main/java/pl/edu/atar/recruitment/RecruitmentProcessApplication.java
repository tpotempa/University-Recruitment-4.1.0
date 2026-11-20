package pl.edu.atar.recruitment;

import io.camunda.client.annotation.Deployment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.mail.*;
import jakarta.mail.Transport;
import java.util.Properties;

@SpringBootApplication
@Deployment(resources = "classpath*:/model/*.*")
public class RecruitmentProcessApplication {

    @Value("${spring.mail.password:N/A}")
    private static String password;

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentProcessApplication.class, args);
    }
}