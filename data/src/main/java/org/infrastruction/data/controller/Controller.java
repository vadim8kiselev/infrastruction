package org.infrastruction.data.controller;

import org.infrastruction.data.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final WeatherRepository weatherRepository;

    @Value("${message:Stub message}")
    private String message;

    @Autowired
    public Controller(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @RequestMapping("/message")
    public String getMessage() {
        return this.message;
    }
}
