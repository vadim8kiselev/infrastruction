package org.infrastruction.data.controller;

import lombok.RequiredArgsConstructor;
import org.infrastruction.data.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    @Value("${message:Stub message}")
    private String message;

    private final WeatherRepository weatherRepository;

    @RequestMapping("/message")
    public String getMessage() {
        return this.message;
    }
}
