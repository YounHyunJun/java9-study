package java9.client;

import java9.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ClientApp {

    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class);
    }

    @Component
    class CLRClient implements CommandLineRunner {

        @Autowired
        FlowService flowService;

        @Override
        public void run(String... args) throws Exception {
            System.out.println(flowService.flowService());
        }
    }

    @Bean
    public FlowService flowService() {
        return new FlowService();
    }

}
