package persistence;

import connection.PostgreSQLConnection;
import model.Marca;
import model.Modelo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void update(Modelo modelo) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "UPDATE modelo SET descricao = ?, id_marca = ? WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, modelo.getDescricao());
            ps.setLong(2, modelo.getMarca().getId());
            ps.setLong(3, modelo.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "DELETE FROM modelo WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Modelo findById(Long id) {
        Modelo modelo = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT mo.id, mo.descricao, ma.id, ma.descricao " +
                    "FROM modelo AS mo " +
                    "INNER JOIN marca AS ma ON mo.id_marca = ma.id " +
                    "WHERE mo.id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            modelo = readValues(ps.executeQuery()).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }

    public List<Modelo> listAll() {
        List<Modelo> modelos = new ArrayList<>();
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT mo.id, mo.descricao, ma.id, ma.descricao " +
                    "FROM modelo AS mo " +
                    "INNER JOIN marca AS ma ON mo.id_marca = ma.id " +
                    "ORDER BY mo.descricao ASC";
            PreparedStatement ps = connection.prepareStatement(sql);
            modelos = readValues(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelos;
    }

    private List<Modelo> readValues(ResultSet rs) throws SQLException {
        List<Modelo> modelos = new ArrayList<>();
        while (rs.next()) {
            Modelo modelo = new Modelo();
            modelo.setId(rs.getLong(1));
            modelo.setDescricao(rs.getString(2));
            Marca marca = new Marca();
            marca.setId(rs.getLong(3));
            marca.setDescricao(rs.getString(4));
            modelo.setMarca(marca);
            modelos.add(modelo);
        }
        return modelos;
    }
}
