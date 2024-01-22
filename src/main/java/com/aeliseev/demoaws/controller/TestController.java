package com.aeliseev.demoaws.controller;

import com.aeliseev.demoaws.model.Person;
import com.aeliseev.demoaws.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/some")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final DataService dataService;

    @GetMapping("/test1")
    public List<Person> testEndpoint(@RequestParam(required = false) String id) {
        log.info("test1 endpoint called, id = {}", id);
        return Optional.ofNullable(id)
                .map(Long::valueOf)
                .map(dataService::getPersonById)
                .map(List::of)
                .orElse(dataService.getAllPersons());
    }

}
