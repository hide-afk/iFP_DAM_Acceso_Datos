import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ej6 {
    static Scanner sc = new Scanner(System.in);
    static final int LONGITUD_NOMBRE = 20; // Fijamos los nombres en 20 caracteres (más adelante se rellenan en blanco sino)

    // 4 bytes del id + 40 bytes del nombre + 4 bytes de la edad
    static final int TAMANO_REGISTRO = Integer.BYTES + LONGITUD_NOMBRE * Character.BYTES + Integer.BYTES;

    public static void main(String[] args) {
        System.out.println("# Programa para consultar directamente en un fichero #");

        String nombreArchivo = pedirString("Ruta y nombre del archivo: ");
        Path ruta = Path.of(nombreArchivo);

        try {
            // Instanciamos el archivo y con permisos de lectura (r) y escritura (w)
            // Si el archivo no existía se creará igual
            RandomAccessFile archivo = new RandomAccessFile(ruta.toFile(), "rw");
            guardarPersonas(archivo);

            int idBuscado;
            do {
                idBuscado = pedirEntero("Introduce el identificador que quieres consultar (1-3): ");
                if (idBuscado < 1 || idBuscado > 3) {
                    System.out.println("El identificador debe ser 1, 2 o 3.");
                }
            } while (idBuscado < 1 || idBuscado > 3);

            consultarPersona(archivo, idBuscado);

        } catch (IOException e) {
            System.out.println("~ Error con el archivo: " + e.getMessage() + "~");
        }
    }

    static void guardarPersonas(RandomAccessFile archivo) throws IOException {
        // Se crea el fichero al principio de la ejecución
        archivo.setLength(0);

        // Por cada persona (de 3) pedimos el nombre y la edad
        for (int id = 1; id <= 3; id++) {
            System.out.println("Persona " + id);
            String nombre = pedirString("Nombre (máximo "
                    + LONGITUD_NOMBRE + " caracteres): ");
            int edad = pedirEntero("Edad: ");

            // Cada registro guarda los campos en el orden id, nombre y edad
            archivo.writeInt(id);
            escribirNombreFijo(archivo, nombre);
            archivo.writeInt(edad);
        }
    }

    static void consultarPersona(RandomAccessFile archivo, int id) throws IOException {
        // Como los registros empiezan en 0, le restamos 1 al id
        long posicion = (long) (id - 1) * TAMANO_REGISTRO;
        archivo.seek(posicion); // Movemos el "cursor" al inicio de donde empieza el registro de la posicion

        int idLeido = archivo.readInt();
        String nombre = leerNombre(archivo);
        int edad = archivo.readInt();

        System.out.println("\nPersona encontrada:");
        System.out.println("Identificador: " + idLeido);
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);

        System.out.println("\nCálculo de posición:");
        System.out.println("(" + id + " - 1) × " + TAMANO_REGISTRO + " = " + posicion + " bytes");
    }

    private static void escribirNombreFijo(RandomAccessFile archivo, String nombre) throws IOException {
        // Cortamos/rellenamos el nombre para que siempre tenga la misma longitud
        String nombreAjustado = nombre.length() > LONGITUD_NOMBRE ? nombre.substring(0, LONGITUD_NOMBRE) : nombre;

        for (int i = 0; i < LONGITUD_NOMBRE; i++) {
            char caracter = i < nombreAjustado.length() ? nombreAjustado.charAt(i) : ' ';
            archivo.writeChar(caracter);
        }
    }

    private static String leerNombre(RandomAccessFile archivo) throws IOException {
        StringBuilder nombre = new StringBuilder();

        for (int i = 0; i < LONGITUD_NOMBRE; i++) {
            nombre.append(archivo.readChar());
        }

        return nombre.toString().trim();
    }

    private static String pedirString(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int pedirEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea pendiente
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Introduce un número entero.");
                sc.nextLine(); // Descartar la entrada no válida
            }
        }
    }
}