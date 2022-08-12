package com.jjeopjjeop.recipe;

import com.jjeopjjeop.recipe.dto.ProduceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RecipeApplication {
	public static final Logger log = LoggerFactory.getLogger(RecipeApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RecipeApplication.class, args);
		log.info("******************** server start ********************");
	}
}
