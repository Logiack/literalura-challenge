package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDatos(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Double fechaNacimiento,
        @JsonAlias("death_year") Double fechaMuerte
        ) {
}
