package persistence;

import connection.PostgreSQLConnection;
import model.Marca;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarcaPersistenceImpl extends UnicastRemoteObject implements MarcaPersistence {

    public MarcaPersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Marca marca) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO marca (descricao) VALUES (?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, marca.getDescricao());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean verifyById(Long id) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id FROM marca WHERE id = ?;";
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
