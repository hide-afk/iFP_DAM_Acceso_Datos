public class Persona {
    private String nombre;
    private String apellido;
    private String ciudad;
    private String nacionalidad;
    private int edad;

    public Persona(){}

    public Persona(String nombre) {
        setNombre(nombre);
    }

    public Persona(String nombre, String apellido, String nacionalidad, String ciudad, int edad) {
        // Reutilizamos los setters de la clase para centralizar la lógica en una sola función
        // Así si a futuro se añade alguna validación en un setter, también aplicará a este constructor
        setNombre(nombre);
        setApellido(apellido);
        setNacionalidad(nacionalidad);
        setCiudad(ciudad);
        setEdad(edad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    // Mostramos los valores en la consola línea por línea
    public void mostrarValores(){
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Apellido: " + this.apellido);
        System.out.println("Ciudad: " + this.ciudad);
        System.out.println("Nacionalidad: " + this.nacionalidad);
        System.out.println("Edad: " + this.edad);
    }
}