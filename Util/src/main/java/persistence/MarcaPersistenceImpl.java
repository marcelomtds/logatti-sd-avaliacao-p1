package persistence;

import connection.PostgreSQLConnection;
import exception.ResourceCannotRemovedException;
import exception.ResourceNotFoundException;
import model.Marca;
import org.apache.commons.lang3.ObjectUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaPersistenceImpl extends UnicastRemoteObject implements MarcaPersistence {

    private ModeloPersistenceImpl modeloPersistenceImpl;

    public MarcaPersistenceImpl() throws RemoteException {
        super();
        modeloPersistenceImpl = new ModeloPersistenceImpl();
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

    public void update(Marca marca) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "UPDATE marca SET descricao = ? WHERE id = (?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, marca.getDescricao());
            ps.setLong(2, marca.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) throws ResourceNotFoundException, ResourceCannotRemovedException {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            findById(id);
            modeloPersistenceImpl.checkLinkWithMarca(id);
            String sql = "DELETE FROM marca WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Marca findById(Long id) throws ResourceNotFoundException {
        Marca marca = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id, descricao " +
                    "FROM marca WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            List<Marca> marcas = readValues(ps.executeQuery());
            if (ObjectUtils.isNotEmpty(marcas)) {
                marca = marcas.get(0);
            } else {
                throw new ResourceNotFoundException("A marca informada não foi encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marca;
    }

    public List<Marca> listAll() {
        List<Marca> marcas = new ArrayList<>();
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id, descricao " +
                    "FROM marca " +
                    "ORDER BY descricao ASC;";
            PreparedStatement ps = connection.prepareStatement(sql);
            marcas = readValues(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marcas;
    }

    private List<Marca> readValues(ResultSet rs) throws SQLException {
        List<Marca> marcas = new ArrayList<>();
        while (rs.next()) {
            Marca marca = new Marca();
            marca.setId(rs.getLong(1));
            marca.setDescricao(rs.getString(2));
            marcas.add(marca);
        }
        return marcas;
    }
}
