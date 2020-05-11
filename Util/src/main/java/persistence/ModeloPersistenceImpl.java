package persistence;

import connection.PostgreSQLConnection;
import model.Modelo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeloPersistenceImpl extends UnicastRemoteObject implements ModeloPersistence {

    public ModeloPersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Modelo modelo) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO modelo (descricao, id_marca) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, modelo.getDescricao());
            ps.setLong(2, modelo.getMarca().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean verifyById(Long id) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id FROM modelo WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
