package com.citycounter.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.citycounter.service.CityService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final CityService cityService;
    private final String ENDPOINT = "https://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b6907d289e10d714a6e88b30761fae22";


    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/count")
    public Map<String, Integer> getCountCitiesByFirstLetter(@RequestParam(required = true) String letter) {
        List<Map<String, Object>> cities = fetchCitiesFromApi();
        Long citiesCount = cityService.countCitiesByFirstLetter(letter, cities);
        return Map.of("count", citiesCount.intValue());
    }
    
    private List<Map<String, Object>> fetchCitiesFromApi() {
        Map response = Optional.ofNullable(restTemplate.getForObject(ENDPOINT, Map.class))
            .orElseThrow(() -> new RuntimeException("Failed to fetch data from API"));
        List<Map<String, Object>> cities = (List<Map<String, Object>>) response.get("list");
    
        if (cities == null) {
            throw new RuntimeException("API response does not contain a valid 'list' of cities");
        }
    
        return cities;
    }
}
