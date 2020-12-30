package com.challenge.dijsktra.app;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringBootChallengeDijsktraApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootChallengeDijsktraApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootChallengeDijsktraApplication.class, args);
	}

}
