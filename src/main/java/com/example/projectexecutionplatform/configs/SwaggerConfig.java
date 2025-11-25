package com.example.projectexecutionplatform.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI myCustomConfig(){
        return new OpenAPI().info(

                // Adding title and subtitle to the swagger doc :
                new Info()
                        .title("Hira")
                        .description("Project Execution Platform by Eshwari Chavan")
        )

                .servers(List.of(new io.swagger.v3.oas.models.servers.Server()
                        .url("http://localhost:8080").description("local"),
                        new Server()
                                .url("http://localhost:8081").description("live")));

//                .tags(Arrays.asList(
//                        new Tag().name()
//                ));
    }
}
