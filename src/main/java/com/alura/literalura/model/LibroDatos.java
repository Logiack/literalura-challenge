package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDatos(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorDatos> autor,
        @JsonAlias("languages") List<String> lenguaje,
        @JsonAlias("download_count") Double descargas
) {
}
