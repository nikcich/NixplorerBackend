package com.cichosz.explorer;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ExplorerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ExplorerApplication.class);

        // Check if a port argument was provided, otherwise use the default (8080)
        String port = Arrays.stream(args)
            .filter(arg -> arg.startsWith("--port="))
            .findFirst()
            .map(arg -> arg.replace("--port=", ""))
            .orElse("8081");
        
        for(String s : args) {
        	System.out.println(s);
        }

        app.setDefaultProperties(Collections.singletonMap("server.port", port));

        app.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ExplorerApplication.class);
    }
}
