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

	public static boolean isStringDate(String year, String month, String day) {
		if (isYear(year) && isMonth(month) && isDay(day)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isYear(String year) {
		if (
				year.length() == 4 &&
				Integer.parseInt(year) > 999 &&
				Integer.parseInt(year) < 10000
		) {
			return true;
		} else {		
			return false;
		}
	}
	
	private static boolean isMonth(String month) {
		if (
				(month.length() == 2 || month.length() == 1)  &&
				Integer.parseInt(month) > 0 &&
				Integer.parseInt(month) < 13
		) {
			return true;
		} else {		
			return false;
		}
	}
	
	private static boolean isDay(String day) {
		if (
				(day.length() == 2 || day.length() == 1)  &&
				Integer.parseInt(day) > 0 &&
				Integer.parseInt(day) < 32
		) {
			return true;
		} else {		
			return false;
		}
	}

	public static String monthFormat(String month) {
		if (month.length() == 1) {
			return 0+month;
		} else {
			return month;
		}
	}

    private static boolean esBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }
	
	public static String dayFormat(String day, String month, String year) {
		switch (Integer.parseInt(month)) {
		case 1, 3, 5, 7, 8, 10, 12:
			if (day.length() == 1) {
				return 0+day;
			} else {
				return day;
			}
		case 4, 6, 9, 11:
			if (Integer.parseInt(day) < 31) {
				if (day.length() == 1) {
					return 0+day;
				} else {
					return day;
				}
			}
			else {
				return "";	
			}
		case 2:
			
			if (Integer.parseInt(day) < 29 && !esBisiesto(Integer.parseInt(year))) {
				if (day.length() == 1) {
					return 0+day;
				} else {
					return day;
				}
			} else if (Integer.parseInt(day) < 30 && esBisiesto(Integer.parseInt(year))) {
				if (day.length() == 1) {
					return 0+day;
				} else {
					return day;
				}
			} else {
				return "";	
			}
		default: 
			return "";
		}
	}
}