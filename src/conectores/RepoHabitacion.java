package conectores;

import java.sql.*;
import java.util.ArrayList;
import model.Habitacion;

public class RepoHabitacion {

    private ArrayList<String> SQLScripts = new ArrayList<>();
    
    public RepoHabitacion() {
        inicializarArray();
    }
    
    private void inicializarArray() {
        SQLScripts.add("INSERT INTO habitacion VALUES (?, ?, ?, ?, ?)");
        SQLScripts.add("DELETE FROM habitacion WHERE id = ?");
        SQLScripts.add("UPDATE habitacion SET num = ?, capacidad = ?, tlfno = ?, tipo = ? WHERE id = ?");
        SQLScripts.add("SELECT * FROM habitacion WHERE id = ?");
    }
    
    public boolean insert(Habitacion nuevo) {
        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(0))) {
            preparedStatement.setInt(1, nuevo.getNum());
            preparedStatement.setInt(2, nuevo.getCapacidad());
            preparedStatement.setInt(3, nuevo.getTlfno());
            preparedStatement.setString(4, nuevo.getTipo().toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar la habitación");
            return false;
        }
    }
    
    public boolean delete(Habitacion aBorrar) {
        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(1))) {
            preparedStatement.setInt(1, aBorrar.getNum());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar la habitación");
            return false;
        }
    }
    
    public boolean update(Habitacion modificaciones) {
        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(2))) {
            preparedStatement.setInt(1, modificaciones.getNum());
            preparedStatement.setInt(2, modificaciones.getCapacidad());
            preparedStatement.setInt(3, modificaciones.getTlfno());
            preparedStatement.setString(4, modificaciones.getTipo().toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar la habitación");
            return false;
        }
    }
    
    public boolean check(Habitacion habitacion) {
        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(3))) {
            preparedStatement.setInt(1, habitacion.getNum());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error al comprobar la existencia de la habitación");
            return false;
        }
    }
    
    public Habitacion get(int idHotel, int numSala) {
        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(3))) {
            preparedStatement.setInt(1, numSala);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Habitacion(resultSet.getInt("num"), resultSet.getInt("capacidad"), resultSet.getInt("tlfno"), null, Habitacion.tipoHab.valueOf(resultSet.getString("tipo")));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la habitación");
        }
        return null;
    }
}

