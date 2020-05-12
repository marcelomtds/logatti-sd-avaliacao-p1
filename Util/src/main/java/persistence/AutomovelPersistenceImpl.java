package persistence;

import connection.PostgreSQLConnection;
import model.Automovel;
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

public class AutomovelPersistenceImpl extends UnicastRemoteObject implements AutomovelPersistence {

    public AutomovelPersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Automovel automovel) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO automovel " +
                    "(placa, " +
                    "numero_portas, " +
                    "tipo_combustivel, " +
                    "cor, " +
                    "ano, " +
                    "chassi, " +
                    "valor_diaria, " +
                    "id_modelo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, automovel.getPlaca());
            ps.setInt(2, automovel.getNumeroPortas());
            ps.setString(3, automovel.getTipoCombustivel());
            ps.setString(4, automovel.getCor());
            ps.setInt(5, automovel.getAno());
            ps.setString(6, automovel.getChassi());
            ps.setBigDecimal(7, automovel.getValorDiaria());
            ps.setLong(8, automovel.getModelo().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Automovel automovel) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "UPDATE automovel SET " +
                    "placa = ?, " +
                    "numero_portas = ?, " +
                    "tipo_combustivel = ?, " +
                    "cor = ?, " +
                    "ano = ?, " +
                    "chassi = ?, " +
                    "valor_diaria = ?, " +
                    "id_modelo = ? " +
                    "WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, automovel.getPlaca());
            ps.setLong(2, automovel.getNumeroPortas());
            ps.setString(3, automovel.getTipoCombustivel());
            ps.setString(4, automovel.getCor());
            ps.setInt(5, automovel.getAno());
            ps.setString(6, automovel.getChassi());
            ps.setBigDecimal(7, automovel.getValorDiaria());
            ps.setLong(8, automovel.getModelo().getId());
            ps.setLong(9, automovel.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "DELETE FROM automovel WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Automovel findById(Long id) {
        Automovel automovel = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT " +
                    "a.id, " +
                    "a.placa, " +
                    "a.numero_portas, " +
                    "a.tipo_combustivel, " +
                    "a.cor, " +
                    "a.ano, " +
                    "a.chassi, " +
                    "a.valor_diaria, " +
                    "mo.id, " +
                    "mo.descricao, " +
                    "ma.id, " +
                    "ma.descricao " +
                    "FROM automovel AS a " +
                    "INNER JOIN modelo AS mo ON a.id_modelo = mo.id " +
                    "INNER JOIN marca AS ma ON mo.id_marca = ma.id " +
                    "WHERE a.id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            automovel = readValues(ps.executeQuery()).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return automovel;
    }

    public List<Automovel> listAll() {
        List<Automovel> automoveis = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT " +
                    "a.id, " +
                    "a.placa, " +
                    "a.numero_portas, " +
                    "a.tipo_combustivel, " +
                    "a.cor, " +
                    "a.ano, " +
                    "a.chassi, " +
                    "a.valor_diaria, " +
                    "mo.id, " +
                    "mo.descricao, " +
                    "ma.id, " +
                    "ma.descricao " +
                    "FROM automovel AS a " +
                    "INNER JOIN modelo AS mo ON a.id_modelo = mo.id " +
                    "INNER JOIN marca AS ma ON mo.id_marca = ma.id " +
                    "ORDER BY mo.descricao ASC;";
            PreparedStatement ps = connection.prepareStatement(sql);
            automoveis = readValues(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return automoveis;
    }

    private List<Automovel> readValues(ResultSet rs) throws SQLException {
        List<Automovel> automoveis = new ArrayList<>();
        while (rs.next()) {
            Automovel automovel = new Automovel();
            automovel.setId(rs.getLong(1));
            automovel.setPlaca(rs.getString(2));
            automovel.setNumeroPortas(rs.getInt(3));
            automovel.setTipoCombustivel(rs.getString(4));
            automovel.setCor(rs.getString(5));
            automovel.setAno(rs.getInt(6));
            automovel.setChassi(rs.getString(7));
            automovel.setValorDiaria(rs.getBigDecimal(8));
            Modelo modelo = new Modelo();
            modelo.setId(rs.getLong(9));
            modelo.setDescricao(rs.getString(10));
            Marca marca = new Marca();
            marca.setId(rs.getLong(11));
            marca.setDescricao(rs.getString(12));
            modelo.setMarca(marca);
            automovel.setModelo(modelo);
            automoveis.add(automovel);
        }
        return automoveis;
    }
}
