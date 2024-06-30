package com.navasan.Literalura_Desafio.principal;

import com.navasan.Literalura_Desafio.models.*;
import com.navasan.Literalura_Desafio.repository.AutorRepository;
import com.navasan.Literalura_Desafio.repository.LibroRepository;
import com.navasan.Literalura_Desafio.service.ConsumoAPI;
import com.navasan.Literalura_Desafio.service.ConveritrDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConveritrDatos converitrDatos = new ConveritrDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private List<Autor> autors;
    private List<Libro> libros;
    private LibroRepository repository;
    private AutorRepository autorRepository;

    public Principal(AutorRepository autorRepository, LibroRepository repository) {
        this.autorRepository = autorRepository;
        this.repository = repository;
    }


    public void mostrarMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    Eliga la opcion a travez de su numero:
                                    
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idiomas
                    0 - Salir
                                    
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    todosLosLibros();
                    break;
                case 3:
                    todosLosAutores();
                    break;
                case 4:
                    autoresVivosDesdeDetrminadaFecha();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando el sistema... Nos vemos pronto =]");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }
    }

    private Datos getDatosLibros(){
        System.out.println("Ingresa el nombre del libro que deseas buscar:");
        var libro = teclado.nextLine();
        var json = consumoAPI.ConsumirDatos(URL_BASE + "?search=" + libro.replace(" ", "+"));
        System.out.println(json);
        Datos datos = converitrDatos.obtenerDatos(json, Datos.class);

        return datos;
    }

    private Libro crearLibro(DatosLibros datosLibros, Autor autor){
        Libro libro = new Libro(datosLibros, autor);
        return repository.save(libro);
    }

    private void buscarLibro(){
        Datos datos = getDatosLibros();

        if(!datos.libros().isEmpty()){
            DatosLibros datosLibros = datos.libros().get(0);
            DatosAutor datosAutor = datosLibros.autor().get(0);
            Libro libroDb = repository.findByTitulo(datosLibros.titulo());

            if(libroDb != null){
                System.out.println(libroDb);
            }else{
                Autor autorDb = autorRepository.findByNombre(datosAutor.nombre());
                if(autorDb == null){
                    Autor autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                    Libro libro = crearLibro(datosLibros, autor);
                    System.out.println(libro);
                }else{
                    Libro libro = crearLibro(datosLibros, autorDb);
                    System.out.println(libro);
                }
            }
        }
    }

    private void todosLosLibros(){
        libros = repository.findAll();

        libros.stream()
                .forEach(System.out::println);
    }

    private void todosLosAutores(){
        autors = autorRepository.findAll();

        autors.stream()
                .forEach(System.out::println);
    }

    private void autoresVivosDesdeDetrminadaFecha(){
        System.out.println("Ingresa la fecha a partir para buscar");
        String fecha = teclado.nextLine();

        try{
            List<Autor> autoresVivos = autorRepository.autorVivoDesde(fecha);

            autoresVivos.stream()
                    .forEach(System.out::println);
        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }

    private void librosPorIdioma(){
        System.out.println("""
                Indica en que idioma quieres buscar:
                
                1 - Ingles
                2 - Español
                3 - Frances
                """);
        var opcion = teclado.nextInt();

        switch (opcion){
            case 1:
                List<Libro> libroPorIdioma = repository.findByidiomoasContaining("en");
                libroPorIdioma.stream()
                        .forEach(System.out::println);
                break;
            case 2:
                libros = repository.findByidiomoasContaining("es");
                libros.stream()
                        .forEach(System.out::println);
                break;
            case 3:
                libros = repository.findByidiomoasContaining("fr");
                libros.stream()
                        .forEach(System.out::println);
                break;
            default:
                System.out.println("no se encontraron libros en el idioma seleccionado");
        }
    }
}

