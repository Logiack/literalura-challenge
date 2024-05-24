package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double yearBorn;
    private Double yearDead;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros;


    public Autor(){}

    public Autor(AutorDatos autorDatos) {
        this.nombre = autorDatos.nombre();
        this.yearBorn = autorDatos.fechaNacimiento();
        this.yearDead = autorDatos.fechaMuerte();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(Double yearBorn) {
        this.yearBorn = yearBorn;
    }

    public Double getYearDead() {
        return yearDead;
    }

    public void setYearDead(Double yearDead) {
        this.yearDead = yearDead;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  "\nNombre: " + nombre +
                "\nFecha de nacimiento: " + yearBorn +
                "\nFecha de fallecido: " + yearDead ;
    }
}
