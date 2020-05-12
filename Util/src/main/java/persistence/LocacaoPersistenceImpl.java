package persistence;

import connection.PostgreSQLConnection;
import model.Locacao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocacaoPersistenceImpl extends UnicastRemoteObject implements LocacaoPersistence {

    public LocacaoPersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Locacao locacao) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO locacao (data_locacao, data_devolucao, valor, id_automovel, id_cliente) " +
                    "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, (Date) locacao.getDataLocacao());
            ps.setDate(2, (Date) locacao.getDataDevolucao());
            ps.setBigDecimal(3, locacao.getValor());
            ps.setLong(4, locacao.getAutomovel().getId());
            ps.setLong(5, locacao.getCliente().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "DELETE FROM locacao WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Locacao> listAll() {
        List<Locacao> locacoes = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id, data_locacao, data_devolucao, valor, id_automovel, id_cliente " +
                    "FROM locacao;";
            PreparedStatement ps = connection.prepareStatement(sql);
            locacoes = readValues(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locacoes;
    }

    private List<Locacao> readValues(ResultSet rs) throws SQLException {
        List<Locacao> locacoes = new ArrayList<>();
        while (rs.next()) {
            Locacao locacao = new Locacao();
            locacao.setId(rs.getLong("id"));
            locacao.setDataLocacao(rs.getDate("data_locacao"));
            locacao.setDataDevolucao(rs.getDate("data_devolucao"));
            locacao.setValor(rs.getBigDecimal("valor"));
        }
        return locacoes;
    }
}
