package com.challenge.dijsktra.app;

import com.challenge.dijsktra.app.DTOs.CityDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ModelMapperTest {
	

    private ModelMapper modelMapper = new ModelMapper();



    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {
        CityDTO madridDto = new CityDTO("Madrid");
        long id = 2;
        madridDto.setId(id);
        City madrid = modelMapper.map(madridDto, City.class);
   //     assertEquals(madridDto.getId(), madrid.getId());
        assertEquals(madridDto.getName(), madrid.getName());
    }


}