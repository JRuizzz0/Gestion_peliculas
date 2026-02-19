package org.example;

import Recursos.Exceptions.InvalidDateException;
import Recursos.Genero;
import Recursos.MyScanner;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class GestionPeliculas {

    private static ArrayList<Pelicula> peliculas = new ArrayList<>();
    private static Map<Pelicula, Integer> visualizaciones = new HashMap<>();

    private static final Scanner sc = new Scanner(System.in);
    private static final MyScanner myScanner = new MyScanner();

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        boolean exit;
        do {
            exit = false;
            System.out.println("\n===== GESTION PELÍCULAS =====" +
                    "\n1. Registrar película" +
                    "\n2. Mostrar películas" +
                    "\n3. Ver película" +
                    "\n4. Mostrar estadísticas de visualización" +
                    "\n5. Salir" +
                    "\nInserte una opción: ");

            int opcion;
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                sc.next();
            }
            opcion = sc.nextInt();
            sc.nextLine();


            switch (opcion) {
                case 1:
                    registrarPelicula();
                    break;
                case 2:
                    mostrarPeliculas();
                    break;
                case 3:
                    verPeliculas();

                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Saliendo ....");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida!");
                    break;
            }
        } while (!exit);
    }

    public static void registrarPelicula() {
        System.out.println("--- Registrar Película ---");


        String codigo;
        boolean codigoValido;
        do {
            codigoValido = true;
            System.out.println("Introduce el código: ");
            codigo = sc.nextLine();

            if (!codigo.matches("[A-Z0-9]+") && codigo.length() >= 5) {
                System.out.println("Error: El codigo debes ser alfanumerico y contener mayusculas y tener al menos 5 caracteres.");
                codigoValido = false;
            } else {
                for (Pelicula p : peliculas) {
                    if (p.getCodigo().equals(codigo)) {
                        System.out.println("Error: El código ya existe.");
                        codigoValido = false;
                        break;
                    }
                }
            }
        } while (!codigoValido);


        String titulo = myScanner.pedirSoloTexto("Título: ");
        String director = myScanner.pideTexto("Director: ");


        System.out.println("Géneros disponibles: " + Arrays.toString(Genero.values()));
        Genero generoSeleccionado = null;
        boolean generoValido = false;

        do {
            System.out.print("Escribe el género: ");
            String scgenero = sc.nextLine().toUpperCase();
            try {
                generoSeleccionado = Genero.valueOf(scgenero);
                generoValido = true;
            } catch (Exception e) {
                System.out.println("Género no válido. Intenta de nuevo.");
            }
        } while (!generoValido);

         boolean fechaValida = false;
         LocalDate fechaEstreno = null;
        do {
            System.out.print("Escribe la fecha: ");
            String fecha = myScanner.pideTexto("Fecha: ");


            try {
                fechaEstreno = LocalDate.parse(fecha);
                if (fechaEstreno.isBefore(LocalDate.now())) {
                    fechaValida = true;
                }
            } catch (InvalidDateException | DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        } while (!fechaValida);

        Pelicula nuevaPelicula = new Pelicula(codigo, titulo, director, generoSeleccionado, fechaEstreno);

        peliculas.add(nuevaPelicula);
        visualizaciones.put(nuevaPelicula, 0);
        System.out.println("Película registrada con éxito");
    }

    public static void mostrarPeliculas() {
        System.out.println("--- Lista de Películas ---");
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas.");
        } else {
            for (Pelicula pelicula : peliculas) {
                System.out.println(pelicula.toString());
            }
        }
    }

    public static void verPeliculas() {
        String codigo = myScanner.pideTexto("Introduce el codigo de la pelicula: ");

        for (Pelicula pelicula : peliculas) {
                if (pelicula.getCodigo().equals(codigo)) {
                    visualizaciones.put(pelicula, visualizaciones.get(pelicula) + 1);
                }else{
                    System.out.println("No se esncuentra el codigo de la pelicula.");
                }
        }
        System.out.println(visualizaciones);

    }

}