package com.aeliseev.demoaws.repo;

import com.aeliseev.demoaws.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Long> {
}
