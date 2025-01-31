package conectores;

import java.util.ArrayList;
import java.util.List;
import model.Reserva;

public class RepoReserva {
    private List<Reserva> reservas;
    // Atributo privado
    private ArrayList<String> SQLScripts;

    // Constructor
    public RepoReserva() {
        this.reservas = new ArrayList<>();
    }

    // Insertar una nueva reserva
    public boolean insert(Reserva nuevaReserva) {
        System.out.println("RepoReserva: Insertando una nueva reserva...");
        return reservas.add(nuevaReserva);
    }

    // Obtener una reserva por ID
    public Reserva get(String id) {
        System.out.println("RepoReserva: Buscando una reserva con ID " + id + "...");
        for (Reserva reserva : reservas) {
            if (reserva.getId().equals(id)) {
                System.out.println("RepoReserva: Reserva encontrada.");
                return reserva;
            }
        }
        System.out.println("RepoReserva: Reserva no encontrada.");
        return null;
    }

    // Actualizar una reserva existente
    public boolean update(Reserva reservaActualizada) {
        System.out.println("RepoReserva: Actualizando la reserva con ID " + reservaActualizada.getId() + "...");
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId().equals(reservaActualizada.getId())) {
                reservas.set(i, reservaActualizada);
                System.out.println("RepoReserva: Reserva actualizada exitosamente.");
                return true;
            }
        }
        System.out.println("RepoReserva: No se pudo actualizar la reserva.");
        return false;
    }
}
