public class Main {
    public static void main() {
        // Instanciamos 3 objetos de la clase Persona
        Persona marc = new Persona("Marc", "Abad Platas", "Barcelona", "Española", 19);
        Persona jordi = new Persona("Jordi", "Morata Reyes", "Barcelona", "Española", 36);
        Persona shuqi = new Persona("Shu Qi", "Dong", "Barcelona", "China", 20);

        // Creamos un array que contengan las 3 personas instanciadas
        Persona[] arrayPersonas = {marc, jordi, shuqi};
        // Recorremos el array mostrando la información de cada persona
        for (Persona persona : arrayPersonas){
            System.out.println("~~~~ Información de la persona llamada " + persona.getNombre() + " ~~~~");
            persona.mostrarValores();
        }
    }
}
