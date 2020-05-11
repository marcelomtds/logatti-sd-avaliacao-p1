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
}
