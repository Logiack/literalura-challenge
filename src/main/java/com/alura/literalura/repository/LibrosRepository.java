package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibrosRepository extends JpaRepository<Libros, Long> {


    Boolean existsByTitulo(String titulo);

    @Query("SELECT l FROM Libros l WHERE l.lenguaje ILIKE %:idioma%")
    List<Libros> librosIdiomas(String idioma);

    List<Libros> findTop10ByOrderByDescargasDesc();

}
