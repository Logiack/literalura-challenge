package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String lenguaje;
    private Double descargas;
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    public Libros(){}

    public Libros(LibroDatos d){
        this.titulo = d.titulo();
        this.lenguaje = d.lenguaje().get(0);
        this.descargas = d.descargas();
        this.autor = new Autor(d.autor().get(0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  "\nAutor: " + autor +
                "\ntitulo: " + titulo +
                "\nlenguaje: " + lenguaje +
                "\ndescargas: " + descargas;
    }
}
