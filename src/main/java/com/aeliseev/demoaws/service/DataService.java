package com.aeliseev.demoaws.service;

import com.aeliseev.demoaws.model.Person;
import com.aeliseev.demoaws.repo.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {

    private final PersonRepo personRepo;

    public Person getPersonById(Long id) {
        return personRepo.findById(id).orElse(null);
    }

    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

}
