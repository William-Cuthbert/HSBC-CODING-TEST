package com.citycounter.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CityService {

    private static final String SINGLE_LETTER_PATTERN = "^[a-zA-Z]{1}$";
    private static final String KEY_STRING = "name";

    public long countCitiesByFirstLetter(String letter, List<Map<String, Object>> cities) {
        validateLetter(letter);
        return cities.stream()
            .filter(city -> city.get(KEY_STRING) instanceof String)
            .filter(city -> ((String) city.get(KEY_STRING)).toLowerCase().startsWith(letter.toLowerCase()))
            .count();
    }

    private void validateLetter(String letter) {
        if (!letter.matches(SINGLE_LETTER_PATTERN)) {
            throw new IllegalArgumentException("Invalid letter: " + letter);
        }
    }
}
