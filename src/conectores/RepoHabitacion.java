package conectores;

import java.sql.*;
import java.util.ArrayList;
import model.Habitacion;

public class RepoHabitacion {

    private ArrayList<String> SQLScripts = new ArrayList<>();

    private void inicializarArray() {
        // Insertar
        SQLScripts.add("INSERT INTO Habitacion (ID, Num, tipoHab) VALUES (?, ?, ?)");

        // Eliminar
        SQLScripts.add("DELETE FROM Habitacion WHERE ID = ? AND Num = ?");

        // Modificar
        SQLScripts.add("UPDATE Habitacion SET tipoHab = ? WHERE ID = ? AND Num = ?");

        // Comprobar existencia
        SQLScripts.add("SELECT * FROM Habitacion WHERE ID = ? AND Num = ?");

        // Obtener información
        SQLScripts.add("SELECT * FROM Habitacion WHERE ID = ? AND Num = ?");
    }

    public boolean insert(Habitacion nuevo) {
        if (!(nuevo instanceof Habitacion)) return false;
        Habitacion habitacion = (Habitacion) nuevo;

        if (SQLScripts.isEmpty()) {
            inicializarArray();
        }

        if (!check(habitacion)) {
            try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(0))) {
                preparedStatement.setInt(1, habitacion.getID());
                preparedStatement.setInt(2, habitacion.getNum());
                preparedStatement.setString(3, habitacion.getTipo().name());
                preparedStatement.executeUpdate();
                return check(habitacion);
            } catch (SQLException e) {
                System.out.println("Error al insertar la habitación");
                return false;
            }
        }
        return false;
    }

    public boolean delete(Habitacion aBorrar) {
        if (!(aBorrar instanceof Habitacion)) return false;
        Habitacion habitacion = (Habitacion) aBorrar;

        if (SQLScripts.isEmpty()) {
            inicializarArray();
        }

        if (check(habitacion)) {
            try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(1))) {
                preparedStatement.setInt(1, habitacion.getID());
                preparedStatement.setInt(2, habitacion.getNum());
                preparedStatement.executeUpdate();
                return !check(habitacion);
            } catch (SQLException e) {
                System.out.println("Error al eliminar la habitación");
                return false;
            }
        }
        return false;
    }

    public boolean update(Habitacion modificaciones) {
        if (!(modificaciones instanceof Habitacion)) return false;
        Habitacion habitacion = (Habitacion) modificaciones;

        if (SQLScripts.isEmpty()) {
            inicializarArray();
        }

        Habitacion original = get(habitacion.getID(), habitacion.getNum());
        if (original == null) {
            System.out.println("Error: habitación no encontrada");
            return false;
        }

        if (!habitacion.getTipo().isEmpty()) original.setTipo(habitacion.getTipo());

        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(2))) {
        	preparedStatement.setString(1, original.getTipo().name());
            preparedStatement.setInt(2, original.getID());
            preparedStatement.setInt(3, original.getNum());
            preparedStatement.executeUpdate();
            return check(original);
        } catch (SQLException e) {
            System.out.println("Error al actualizar la habitación");
            return false;
        }
    }

    public boolean check(Habitacion objeto) {
        if (!(objeto instanceof Habitacion)) return false;
        Habitacion habitacion = (Habitacion) objeto;

        if (SQLScripts.isEmpty()) {
            inicializarArray();
        }

        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(3))) {
            preparedStatement.setInt(1, habitacion.getID());
            preparedStatement.setInt(2, habitacion.getNum());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error al comprobar la existencia de la habitación");
            return false;
        }
    }

    public Habitacion get(Habitacion primaryKey) {
        return null;
    }

    public Habitacion get(int ID, int Num) {
        if (SQLScripts.isEmpty()) {
            inicializarArray();
        }

        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(4))) {
            preparedStatement.setInt(1, ID);
            preparedStatement.setInt(2, Num);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Habitacion(
                    resultSet.getInt("ID"),
                    resultSet.getInt("Num"),
                    resultSet.getString("tipoHab")
                );
            }
        } catch (SQLException e) {
        	System.out.println("Error al obtener la habitación: " + e.getMessage());
        }
        return null;
    }
}
