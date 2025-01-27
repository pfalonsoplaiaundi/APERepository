package auxi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StringDate {

    private String fecha;

    //GETTER & SETTERS
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    //CONVIERTE EL STRING A LOCALDATE
    public LocalDate getFechaAsLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(fecha, formatter);
    }

    //METODO PARA QUE USER INTRODUZCA LA FECHA
    public void tranformarFecha(Scanner sc) {
        System.out.print("Introduce la fecha en formato yyyyMMdd: ");
        String fechaUsuario = sc.nextLine();
        
        //SE VERIFICA QUE EL FORMATO LA FECHA SEA VALIDO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            LocalDate fechaLocal = LocalDate.parse(fechaUsuario, formatter);
            this.fecha = fechaUsuario; 
            System.out.println("Fecha ingresada correctamente: " + fechaLocal);
        } catch (Exception e) {
            System.out.println("Fecha inválida. Asegúrate de seguir el formato yyyyMMdd.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringDate prueba = new StringDate();

        prueba.tranformarFecha(sc); //LLAMADA AL METODO
        
    }
}