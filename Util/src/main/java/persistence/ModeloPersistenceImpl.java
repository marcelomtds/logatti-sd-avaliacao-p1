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

    public Modelo findById(Long id) {
        Modelo modelo = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id, descricao, id_marca " +
                    "FROM modelo WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo = new Modelo();
                modelo.setId(rs.getLong("id"));
                modelo.setDescricao(rs.getString("descricao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }
}
