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

    public Marca findById(Long id) {
        Marca marca = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id, descricao " +
                    "FROM marca WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                marca = new Marca();
                marca.setId(rs.getLong("id"));
                marca.setDescricao(rs.getString("descricao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marca;
    }
}
