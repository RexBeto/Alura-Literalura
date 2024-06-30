# Desafío de Literalura en Java
En este desafío, realizamos una solicitud a una API de libros, donde obtenemos un JSON y guardamos los datos más relevantes en una base de datos PostgreSQL. 

Primero, nos conectamos a la API utilizando el HttpClient de Java, que nos permite establecer una conexión HTTP. Luego, usamos HttpRequest para acceder a los datos proporcionados mediante la URL y HttpResponse para extraer toda la información. Adicionalmente, creamos un archivo para convertir la respuesta en un JSON y manejar mejor la información.

Para crear los modelos, extrajimos la información que queríamos guardar en la base de datos utilizando las anotaciones @Entity y @Table. Para transformar un modelo de datos a datos reales, aplicamos un filtro en los archivos de Datos, extrayendo todo lo que estaba en la propiedad "result" del JSON, gracias a la anotación @JsonAlias. Para ignorar los campos no relevantes del JSON, utilizamos @JsonIgnoreProperties(ignoreUnknown = true). Las demás clases, DatosLibro y DatosAutor, siguen la misma lógica.
En los repositorios, realizamos las consultas necesarias para la clase principal, usando la función integrada findAll para seleccionar todos los registros de un modelo específico.
Toda la lógica se encuentra en la clase principal del proyecto, dividida en la creación de menús y las funciones de cada uno. Para ejecutar correctamente el sistema, se debe iniciar desde el archivo LiteraluraDesafioApplication, que contiene los métodos necesarios para ejecutar la aplicación. Para que fuera una aplicación de línea de comandos, implementamos la clase CommandLineRunner, que crea un método llamado run para lanzar la aplicación en la consola.

Este desafío incluye los siguientes objetivos:

* Conectar y consumir una API.
* Crear modelos de datos para mapear la respuesta del JSON.
* Crear repositorios para las consultas de cada tabla.
* Implementar la lógica para almacenar todo en la base de datos.