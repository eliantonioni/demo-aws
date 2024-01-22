package com.aeliseev.demoaws.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "person")
@ToString
public class Person {

    @Id
    @Column
    private Long id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String state;
    @Column
    private String username;

}
