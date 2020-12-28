package com.challenge.dijsktra.app;

import com.challenge.dijsktra.app.algorithm.*;
import com.challenge.dijsktra.app.model.City;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiConnectionTest {

	private URL base;
	private TestRestTemplate template;

	

	@Test
	public void testingAPIConnection() {
		try {
			this.base = new URL("http://localhost:8082");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		template = new TestRestTemplate();
		ResponseEntity<String> response = template.getForEntity(base + "/showCities", String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

}