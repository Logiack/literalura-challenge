package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libros;
import com.alura.literalura.model.Resultado;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibrosRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConvierteDatos;
import org.hibernate.type.descriptor.sql.internal.Scale6IntervalSecondDdlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibrosRepository librosRepository;
    private AutorRepository autorRepository;

    public Principal() {
    }

    @Autowired
    public Principal(LibrosRepository repository, AutorRepository autorRepository) {
        this.librosRepository = repository;
        this.autorRepository = autorRepository;
    }


    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n
                    **** MENÚ ****
                                          
                    1 - Buscar Libro
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en un determinado año
                    5 - Mostrar libros por idioma
                    6 - Top 10 libros mas descargados
                                          
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    autoresVivosDelAño();
                    break;
                case 5:
                    mostrarLibrosIdioma();
                    break;
                case 6:
                    top10MasDescargados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion");
                    break;
                default:
                    System.out.println("opcion invalida");
            }
        }
    }

    private Resultado getLibros() {
        System.out.println("Escriba el nombre del libro deseado");
        var nombreLibro = scanner.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+").toLowerCase());
        Resultado resultado = convierteDatos.obtenerDatos(json, Resultado.class);
        return resultado;
    }

    private void buscarLibro() {
        Resultado resultado = getLibros();
        Optional<Libros> libroEncontrado = resultado.datos().stream()
                .map(Libros::new)
                .findFirst();
        if (libroEncontrado.isPresent()) {
            String nombreAutor = libroEncontrado.get().getAutor().getNombre();
            Autor autor = autorRepository.buscarAutor(nombreAutor);

            if (autor == null) {
                Autor nuevoAutor = libroEncontrado.get().getAutor();
                autor = autorRepository.save(nuevoAutor);
            }
            Libros libro = libroEncontrado.get();
            libro.setAutor(autor);

            Boolean libroRegistrado = librosRepository.existsByTitulo(libro.getTitulo());

            if (!libroRegistrado) {
                try {
                    librosRepository.save(libro);
                    System.out.println(libro);
                } catch (DataIntegrityViolationException ex) {
                    System.out.println("El libro" + libro.getTitulo() + " ya se a registrado en la base de datos");
                }
            } else {
                System.out.println("El libro " + libro.getTitulo() + " ya se a registrado en la base de datos");
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void mostrarLibros() {
        List<Libros> libros = librosRepository.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.stream().forEach(System.out::println);
    }

    private void autoresVivosDelAño() {
        System.out.println("Escriba la fecha de nacimiento del autor");


        try {
            double fechaNac = scanner.nextDouble();

            List<Autor> autoresVivos = autorRepository.buscarAutorAño(fechaNac);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraro Autores con esa fecha");
            } else {
                System.out.println("\nAutores vivos en el año " + fechaNac + ": ");
                autoresVivos.forEach(a -> System.out.println("\nAutor " + a.getNombre() +
                        "\nfecha de nacimiento " + a.getYearBorn() + "\nmuerte en " + a.getYearDead()));
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("\n////// Escriba una fecha valida //////");
            scanner.next();
        }
    }

    private void mostrarLibrosIdioma() {
        System.out.println("""
                ----- Elige el idioma -----
                es / Español
                en / Ingles
                fr / Francés
                pt / Portugues
                """);
        var idioma = scanner.nextLine().toLowerCase();
        if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("fr") && !idioma.equals("pt")) {
            System.out.println("\nIdioma no valido. Por favor, selecciona un idioma valido");
            return;
        }
        try {
            List<Libros> librosIdiomas = librosRepository.librosIdiomas(idioma);
            if (librosIdiomas.isEmpty()) {
                System.out.println("No se encontraron libros en ese idioma.");
            } else {
                librosIdiomas.forEach(l -> System.out.println("\nNombre: " + l.getTitulo() + "\nIdioma: " + l.getLenguaje() +
                        "\nAutor: " + l.getAutor().getNombre()));
            }
        } catch (Exception e) {
            System.out.println("No se encontraron libros con el idioma especificado");
        }
    }
    private void top10MasDescargados(){
        System.out.println("Los top 10 libros mas descargados");
        List<Libros> top10 = librosRepository.findTop10ByOrderByDescargasDesc();
        top10.stream().forEach(System.out::println);
    }

}
