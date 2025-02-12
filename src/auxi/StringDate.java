package auxi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Clase de apoyo que gestiona fechas en formato string
 */
public class StringDate {

	//Atributos
    private String fecha;

    //	GETTER & SETTERS
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * CONVIERTE EL STRING A LOCALDATE
     * @return
     */
    public LocalDate getFechaAsLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(fecha, formatter);
    }

    /**
     * Metodo para que user introduzca la fecha
     * @param sc
     */
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

    /**
     * Funcion que comprueba si un string esta en formato date
     * @param year
     * @param month
     * @param day
     * @return
     */
	public static boolean isStringDate(String year, String month, String day) {
		if (isYear(year) && isMonth(month) && isDay(day)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ¿Este string es un año?
	 * @param year
	 * @return
	 */
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
	
	/**
	 * ¿Este string es un mes?
	 * @param month
	 * @return
	 */
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
	
	/**
	 * ¿Este string es un dia?
	 * @param day
	 * @return
	 */
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

	/**
	 * Convierte en formato mes de date un string
	 * @param month
	 * @return
	 */
	public static String monthFormat(String month) {
		if (month.length() == 1) {
			return 0+month;
		} else {
			return month;
		}
	}

	/**
	 * Este año es bisiesto
	 * @param año
	 * @return
	 */
    private static boolean esBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }
	
    /**
     * Convierte en formato dia de date un string
     * @param day
     * @param month
     * @param year
     * @return
     */
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