import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ej2 {
    static Scanner sc; // Inicializamos el escaner para que los métodos de utilidad puedan acceder al mismo

    public static void main(String[] args){
        // Si no se pasa ningun archivo como parámetro, salimos del programa
        if (args.length == 0) {
            System.out.println("~ Error: Se debe de indicar una ruta/archivo ~");
            return;
        }

        // Inicializamos variables que nos ayudarán a organizar el flujo
        sc = new Scanner(System.in);
        boolean sobreEscribir = false;
        boolean crear;
        Path ruta = Path.of(args[0]);
        Path directorio = ruta.toAbsolutePath().getParent();

        // Comprobamos que el directorio del archivo especificado exista, sino, cerramos el programa
        if (!Files.isDirectory(directorio)) {
            System.out.println("Error: el directorio indicado no existe.");
            return;
        }


        if (Files.exists(ruta)){
            // Si ya existe el archivo decidimos si sobreescribirlo
            sobreEscribir = pedirSiONo("El archivo indicado ya existe, ¿desea sobreescribirlo? (Y/N): ");
        } else {
            // Si no existe, preguntamos al usuario si quiere crearlo
            crear = pedirSiONo("El archivo especificado no existe, ¿desea crearlo y continuar? (Y/N): ");
            if (crear){
                try {
                    Files.createFile(ruta);
                    System.out.println("Archivo creado en la ruta especificada: " + ruta);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // En el caso de no querer crearlo, se cierra el programa
                System.out.println("Saliendo del programa...");
                return;
            }
        }

        Persona persona = new Persona();

        try {
            // Definimos el archivo al que escribir, con la decision del usuario para sobreescribir (append) o no
            FileWriter fw = new FileWriter(args[0], !sobreEscribir); //
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < 3; i++) {
                System.out.println("- Persona nº" + (i + 1) + " -");
                // Seteamos las propiedades del objeto persona
                persona.setNombre(pedirString("Introduce el nombre de la persona: "));
                persona.setApellido(pedirString("Introduce los apellidos de la persona: "));
                persona.setCiudad(pedirString("Introduce la ciudad de la persona: "));
                persona.setNacionalidad(pedirString("Introduce la nacionalidad de la persona: "));
                persona.setEdad(pedirEntero("Introduce la edad de la persona: "));
                // Escribimos en el archivo las propiedades del objeto persona separadas con "," (como un CSV)
                bw.write(persona.devolverStringConcatenado());
                bw.newLine(); // Salto de linea
            }
            // Cerramos el búffer
            bw.close();
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al escribir sobre el archivo: " + e);
        }
    }

    // Función reutilizable para pedir input de tipo String
    public static String pedirString(String mensaje){
        System.out.print(mensaje);
        String input = sc.nextLine();

        return input.trim();
    }

    // Función reutilizable para pedir input de tipo Int, con validación de entrada
    public static int pedirEntero(String mensaje){
        int input = 0;
        boolean inputValido = false;

        do{
            System.out.print(mensaje);
            try {
                input = sc.nextInt();
                inputValido = true;
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("~~Se ha introducido un valor inesperado, introduzca un número entero~~");
                sc.nextLine(); // Limpiamos el scanner para evitar un bucle de errores.
            }
        } while(!inputValido);

        return input;
    }

    // Función reutilizable para pedir una decisión de si o no al usuario
    public static boolean pedirSiONo(String mensaje){
        while (true){
            System.out.print(mensaje);
            String decision = sc.nextLine();
            if (decision.equalsIgnoreCase("Y")){
                return true;
            } else if (decision.equalsIgnoreCase("N")){
                return false;
            } else {
                System.out.println("~ Error: El carácter introducido no es Y/N/y/n ~");
            }
        }
    }
}