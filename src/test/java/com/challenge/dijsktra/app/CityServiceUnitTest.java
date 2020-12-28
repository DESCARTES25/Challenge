package com.challenge.dijsktra.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.dijsktra.app.model.City;
import com.challenge.dijsktra.app.repository.CityRepository;
import com.challenge.dijsktra.app.service.CityService;
import com.challenge.dijsktra.app.service.ICityService;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { ConfiguradorSpring.class })
public class CityServiceUnitTest {

	private CityService cityService;

	private CityRepository cityRepository;

	@Test
	public void citiesNotFound() {
		// Checking Cities without Inserting them
		cityRepository = mock(CityRepository.class);
		cityService = new CityService(cityRepository);
		

		try {
			cityService.findAll();
		} catch (Exception ex) {

			Assertions.assertEquals(ex.getMessage(), "No Cities found");
		}

	}

	@Test
	public void cityNotFound() {
		// Searching for a City that is not in the database
		cityRepository = mock(CityRepository.class);
		cityService = new CityService(cityRepository);
		
		// Save a few cities
		
		City madrid = new City("Madrid");
		City londres = new City("London");
		City berlin = new City("Berlin");
		City tokyo = new City("Tokyo");
		City paris = new City("Paris");
		City newYork = new City("New York");

		cityService.save(madrid);
		cityService.save(londres);
		cityService.save(berlin);
		cityService.save(tokyo);
		cityService.save(paris);
		cityService.save(newYork);
		
		String myCityName = "Split";

		try {
			cityService.findByName(myCityName);
		} catch (Exception ex) {

			Assertions.assertEquals(ex.getMessage(), "City with name " + myCityName + " not found");
		}

	}
	
	@Test
	public void RepeatedCity() {
		// Test if a City is already in database before inserting it
		cityRepository = mock(CityRepository.class);
		cityService = new CityService(cityRepository);
		
		// Save a few cities
		
		
		City london = new City("London");
		
		cityService.save(london);
		
		try {
			cityService.save(london);
		} catch (Exception ex) {

			Assertions.assertEquals(ex.getMessage(), "City with name " + london.getName() + " is already in the database");
		}
		

	}

}
