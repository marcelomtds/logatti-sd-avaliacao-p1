package persistence;

import connection.PostgreSQLConnection;
import model.Automovel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutomovelPersistenceImpl extends UnicastRemoteObject implements AutomovelPersistence {

    public AutomovelPersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Automovel automovel) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO automovel (placa, numero_portas, tipo_combustivel, quilometragem, cor, ano, chassi, valor_diaria, id_modelo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, automovel.getPlaca());
            ps.setInt(2, automovel.getNumeroPortas());
            ps.setString(3, automovel.getTipoCombustivel());
            ps.setInt(4, automovel.getQuilometragem());
            ps.setString(5, automovel.getCor());
            ps.setInt(6, automovel.getAno());
            ps.setString(7, automovel.getChassi());
            ps.setBigDecimal(8, automovel.getValorDiaria());
            ps.setLong(9, automovel.getModelo().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Automovel findById(Long id) {
        Automovel automovel = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id, placa, numero_portas, tipo_combustivel, quilometragem, cor, ano, chassi, valor_diaria, id_modelo " +
                    "FROM automovel WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                automovel = new Automovel();
                automovel.setId(rs.getLong("id"));
                automovel.setPlaca(rs.getString("placa"));
                automovel.setNumeroPortas(rs.getInt("numero_portas"));
                automovel.setTipoCombustivel(rs.getString("tipo_combustivel"));
                automovel.setQuilometragem(rs.getInt("quilometragem"));
                automovel.setCor(rs.getString("cor"));
                automovel.setAno(rs.getInt("ano"));
                automovel.setChassi(rs.getString("chassi"));
                automovel.setValorDiaria(rs.getBigDecimal("valor_diaria"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return automovel;
    }
}
