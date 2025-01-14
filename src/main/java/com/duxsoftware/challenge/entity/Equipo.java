package com.duxsoftware.challenge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipos")
public class Equipo {

    public Equipo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipos_seq")
    @SequenceGenerator(name = "equipos_seq", sequenceName = "equipos_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "liga")
    private String liga;

    @Column(name = "pais")
    private String pais;

    public Equipo(int id, String nombre, String liga, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.liga = liga;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
