package com.tutoriaEggSpringMVC.tienda.entidades;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity

@PrimaryKeyJoinColumn(name = "penId")
public class Pen extends Person {
    private String color;

    public Pen() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}