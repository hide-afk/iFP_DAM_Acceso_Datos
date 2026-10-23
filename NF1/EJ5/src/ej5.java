import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ej5 {
    static Scanner sc; // Inicializamos el escaner para que los métodos de utilidad puedan acceder al mismo

    public static void main(String[] args){
        sc = new Scanner(System.in);
        boolean inputValido = false;
        int opcion = 0;

        System.out.println("# Programa iniciado, ¿que desea hacer? #");
        System.out.println("1. Escribir personas en un fichero de texto");
        System.out.println("2. Leer personas de un fichero de texto");
        System.out.println("3. Escribir personas en un fichero binario");
        System.out.println("4. Leer personas de un fichero binario");
        while (!inputValido){
            opcion = pedirEntero("-> Elige una opción (1 al 4): ");
            if (opcion < 1 || opcion > 4){
                System.out.println("~ Error: Introduce un número válido (1 al 4) ~");
            } else {
                inputValido = true;
            }
        }

        String archivo = pedirString("-> Introduce el archivo sobre el cual realizar la acción: ");
        Path ruta = Path.of(archivo);
        Path directorio = ruta.toAbsolutePath().getParent();

        // Comprobamos que el directorio del archivo especificado exista, sino, cerramos el programa
        if (!Files.isDirectory(directorio)) {
            System.out.println("Error: el directorio indicado no existe.");
            return;
        }

        // Según la opcion del usuario procedemos con una acción u otra
        switch (opcion){
            case 1:
                escribirArchivoTexto(archivo, ruta);
                break;
            case 2:
                leerArchivoTexto(archivo, ruta);
                break;
            case 3:
                escribirArchivoBinario(ruta);
                break;
            case 4:
                leerArchivoBinario(ruta);
                break;
        }
    }

    public static void leerArchivoBinario(Path ruta) {
        if (!Files.isRegularFile(ruta)) { // Comprobamos que el archivo exista
            System.out.println("~ Error: el archivo no existe o la ruta no es un archivo. ~");
            return;
        }

        System.out.println("# ¿Cómo quieres leer el archivo? #");
        System.out.println("1. Mostrar todas las personas");
        System.out.println("2. Buscar personas por nombre");

        int opcion;
        do {
            opcion = pedirEntero("Elige una opción (1 o 2): ");
            if (opcion < 1 || opcion > 2) {
                System.out.println("Introduce 1 o 2.");
            }
        } while (opcion < 1 || opcion > 2);

        String nombreBuscado = "";
        if (opcion == 2) { // En el caso de la segunda opcion, almacenamos el nombre a buscar
            nombreBuscado = pedirString("Introduce el nombre que quieres buscar: ");
        }

        boolean encontrado = false;

        try {
            // Definimos el archivo binario mediante la ruta indicada en el parámetro
            ObjectInputStream binario = new ObjectInputStream(new FileInputStream(ruta.toFile()));

            while (true) {
                // Instanciamos la variable de tipo persona en base a la lectura del archivo binario
                Persona persona = (Persona) binario.readObject();
                if (opcion == 1){
                    persona.mostrarValores();
                    encontrado = true; // Así evitamos que salte el aviso más adelante (linea 109)
                } else if (persona.getNombre().equalsIgnoreCase(nombreBuscado)) {
                    // Se muestran solo los datos de las personas con el nombre buscado
                    persona.mostrarValores();
                    System.out.println("---------------");
                    encontrado = true;
                }
            }
        } catch (EOFException e) {
            // Se acaba el programa, no quedan objetos para leer
        } catch (FileNotFoundException e) {
            System.out.println("~ Error: archivo no encontrado. ~");
        } catch (IOException e) {
            System.out.println("~Error al leer el archivo: " + e.getMessage() + "~");
        } catch (ClassNotFoundException e) {
            System.out.println("~ Error: no se encontró la clase Persona ~");
        }

        if (!encontrado) {
            System.out.println("No se encontraron personas con ese nombre.");
        }
    }

    public static void escribirArchivoBinario(Path ruta) {
        if (Files.exists(ruta)) { // Si el archivo existe, avisaremos que se va a sobreescribir
            System.out.println("Aviso: se sobrescribirán los datos existentes.");
        }

        int personas;
        do {
            personas = pedirEntero("¿Cuántas personas quieres guardar? (1-3): ");
            if (personas < 1 || personas > 3) {
                System.out.println("~ Error: Introduce un número entre 1 y 3 ~");
            }
        } while (personas < 1 || personas > 3);

        try {
            // Instanciamos el archivo binario del cual vamos a leer los streams en base a la ruta especificada
            ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(ruta.toFile()));
            for (int i = 0; i < personas; i++) {
                // Preparamos el objeto persona para almacenar sus datos
                Persona persona = new Persona();

                System.out.println("- Persona nº" + (i + 1) + " -");
                persona.setNombre(pedirString("Nombre: "));
                persona.setApellido(pedirString("Apellidos: "));
                persona.setCiudad(pedirString("Ciudad: "));
                persona.setNacionalidad(pedirString("Nacionalidad: "));
                persona.setEdad(pedirEntero("Edad: "));

                binario.writeObject(persona);
            }
            binario.close();
            System.out.println("Archivo binario guardado correctamente.");
        } catch (IOException e) {
            System.out.println("~ Error al escribir el archivo binario: " + e.getMessage() + "~");
        }
    }

    public static void leerArchivoTexto(String archivo, Path ruta){
        boolean inputValido = false;
        int opcion = 0;

        // Abrimos menú para elegir la siguiente acción del usuario
        System.out.println("# ¿Cómo desea continuar? #");
        System.out.println("1. Mostrar todos los registros almacenados");
        System.out.println("2. Mostrar todos los campos de una persona específica");
        while (!inputValido){
            opcion = pedirEntero("-> Elige una opción (1 o 2): ");
            if (opcion != 1 && opcion != 2){
                System.out.println("~ Error: Introduce un número válido (1 o 2) ~");
            } else {
                inputValido = true;
            }
        }
        try {
            // Inicializamos las variables para el control del flujo
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            switch (opcion){
                case 1: // En el caso de mostrar todos los datos, mostramos todas las lineas
                    while((linea = br.readLine()) != null){
                        System.out.println(linea);
                    }
                    break;
                case 2:
                    // Si quiere buscar por nombre, pedimos el nombre de la persona, y condicionamos la muestra de los datos
                    // según como empiece la línea (ya que el primer campo es el nombre)
                    String nombreBuscado = pedirString("-> Introduce el nombre de la persona: ");
                    boolean encontrado = false;
                    while ((linea = br.readLine()) != null) {
                        String[] campos = linea.split(",", -1);

                        if (campos.length >= 5
                                && campos[0].trim().equalsIgnoreCase(nombreBuscado)) {
                            System.out.println("Nombre: " + campos[0]);
                            System.out.println("Apellido: " + campos[1]);
                            System.out.println("Ciudad: " + campos[2]);
                            System.out.println("Nacionalidad: " + campos[3]);
                            System.out.println("Edad: " + campos[4]);
                            encontrado = true;
                        }
                    }
                    // Si no encuentra ninguna persona, salta un aviso
                    if (!encontrado) {
                        System.out.println("No se encontraron personas con ese nombre.");
                    }
                    break;
            }

        } catch (FileNotFoundException e) {
            System.out.println("~ Error: Archivo no encontrado ~");
        } catch (IOException e) {
            System.out.println("~ Error: Ha ocurrido un error al leer el archivo ~");
        }
    }

    // Lógica del ejercicio 2 modularizada en una función reutilizable
    public static void escribirArchivoTexto(String archivo, Path ruta){
        // Inicializamos variables que nos ayudarán a organizar el flujo
        boolean sobreEscribir = false;
        boolean crear;


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
            FileWriter fw = new FileWriter(archivo, !sobreEscribir); //
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