package com.tutoriaEggSpringMVC.tienda.entidades;

import javax.persistence.Entity;

@Entity
public class MyEmployee extends Person {
    private String company;

    public MyEmployee() {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
