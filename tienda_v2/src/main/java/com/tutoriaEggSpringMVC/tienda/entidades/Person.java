package com.tutoriaEggSpringMVC.tienda.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String personId;
    private String name;
    @ManyToOne
    private Fabricante fabricante;

    public Person() {
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}