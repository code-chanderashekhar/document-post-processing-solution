package com.synechisveltiosi.documentpostprocessingsolution;

import org.springframework.boot.SpringApplication;

public class TestDocumentPostProcessingSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.from(DocumentPostProcessingSolutionApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
