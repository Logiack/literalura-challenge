package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombreContainsIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombre%")
    Autor buscarAutor(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.yearBorn >= :fechaNac ")
    List<Autor> buscarAutorAño(double fechaNac);
}
