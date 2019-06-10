package com.chrisssanti.tacocloud;

import com.chrisssanti.tacocloud.data.jpa.IngredientRepository;
import com.chrisssanti.tacocloud.model.Ingredient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args); // Specify the main class so that the main method will be run when executing the jar
    }

}
