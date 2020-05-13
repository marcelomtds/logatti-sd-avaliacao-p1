package persistence;

import connection.PostgreSQLConnection;
import exception.ResourceCannotRemovedException;
import exception.ResourceNotFoundException;
import model.Marca;
import model.Modelo;
import org.apache.commons.lang3.ObjectUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloPersistenceImpl extends UnicastRemoteObject implements ModeloPersistence {

    private AutomovelPersistenceImpl automovelPersistenceImpl;

    public ModeloPersistenceImpl() throws RemoteException {
        super();
        automovelPersistenceImpl = new AutomovelPersistenceImpl();
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

    public void delete(Long id) throws ResourceNotFoundException, ResourceCannotRemovedException, RemoteException {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            findById(id);
            automovelPersistenceImpl.checkLinkWithModelo(id);
            String sql = "DELETE FROM modelo WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Modelo findById(Long id) throws ResourceNotFoundException {
        Modelo modelo = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT mo.id, mo.descricao, ma.id, ma.descricao " +
                    "FROM modelo AS mo " +
                    "INNER JOIN marca AS ma ON mo.id_marca = ma.id " +
                    "WHERE mo.id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            List<Modelo> modelos = readValues(ps.executeQuery());
            if (ObjectUtils.isNotEmpty(modelos)) {
                modelo = modelos.get(0);
            } else {
                throw new ResourceNotFoundException("O modelo informado não foi encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }

    public void checkLinkWithMarca(Long id) throws ResourceCannotRemovedException {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT * " +
                    "FROM modelo " +
                    "WHERE id_marca = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            if (ps.executeQuery().next()) {
                throw new ResourceCannotRemovedException("A marca informada não pode ser removida porque está vinculada à um modelo.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
