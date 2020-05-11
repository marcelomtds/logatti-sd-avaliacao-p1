package persistence;

import connection.PostgreSQLConnection;
import model.Locacao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LocacaoPersistenceImpl extends UnicastRemoteObject implements LocacaoPersistence {

    public LocacaoPersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Locacao locacao) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO locacao (data_locacao, data_devolucao, valor, id_automovel, id_cliente) " +
                    "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(locacao.getDataLocacao()));
            ps.setDate(2, Date.valueOf(locacao.getDataDevolucao()));
            ps.setBigDecimal(3, locacao.getValor());
            ps.setLong(4, locacao.getAutomovel().getId());
            ps.setLong(5, locacao.getCliente().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
