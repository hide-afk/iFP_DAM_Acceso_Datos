public class Main {
    public static void main(String[] args) {
        // Instanciamos 3 objetos de la clase Persona
        Persona marc = new Persona("Marc", "Abad Platas", "Española", "Barcelona", 19);
        Persona jordi = new Persona("Jordi", "Morata Reyes", "Española", "Barcelona", 36);
        Persona shuqi = new Persona("Shu Qi", "Dong", "China", "Barcelona", 20);

        // Creamos un array que contengan las 3 personas instanciadas
        Persona[] arrayPersonas = {marc, jordi, shuqi};
        // Recorremos el array mostrando la información de cada persona
        for (Persona persona : arrayPersonas){
            System.out.println("~~~~ Información de la persona llamada " + persona.getNombre() + " ~~~~");
            persona.mostrarValores();
        }
    }
}
