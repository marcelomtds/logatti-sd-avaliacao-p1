package persistence;

import connection.PostgreSQLConnection;
import model.Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientePersistenceImpl extends UnicastRemoteObject implements ClientePersistence {

    public ClientePersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Cliente cliente) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO cliente " +
                    "(cpf, " +
                    "nome, " +
                    "data_nascimento, " +
                    "telefone, " +
                    "email, " +
                    "endereco_logradouro, " +
                    "endereco_numero, " +
                    "endereco_complemento, " +
                    "endereco_bairro, " +
                    "endereco_cep, " +
                    "endereco_cidade, " +
                    "endereco_uf) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setDate(3, new Date(cliente.getDataNascimento().getTime()));
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getEnderecoLogradouro());
            ps.setInt(7, cliente.getEnderecoNumero());
            ps.setString(8, cliente.getEnderecoComplemento());
            ps.setString(9, cliente.getEnderecoBairro());
            ps.setString(10, cliente.getEnderecoCep());
            ps.setString(11, cliente.getEnderecoCidade());
            ps.setString(12, cliente.getEnderecoUf());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Cliente cliente) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "UPDATE cliente SET " +
                    "cpf = ?, " +
                    "nome = ?, " +
                    "data_nascimento = ?, " +
                    "telefone = ?, " +
                    "email = ?, " +
                    "endereco_logradouro = ?, " +
                    "endereco_numero = ?, " +
                    "endereco_complemento = ?, " +
                    "endereco_bairro = ?, " +
                    "endereco_cep = ?, " +
                    "endereco_cidade = ?, " +
                    "endereco_uf = ?" +
                    "WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setDate(3, new Date(cliente.getDataNascimento().getTime()));
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getEnderecoLogradouro());
            ps.setInt(7, cliente.getEnderecoNumero());
            ps.setString(8, cliente.getEnderecoComplemento());
            ps.setString(9, cliente.getEnderecoBairro());
            ps.setString(10, cliente.getEnderecoCep());
            ps.setString(11, cliente.getEnderecoCidade());
            ps.setString(12, cliente.getEnderecoUf());
            ps.setLong(13, cliente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "DELETE FROM cliente WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente findById(Long id) {
        Cliente cliente = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT " +
                    "id, " +
                    "cpf, " +
                    "nome, " +
                    "data_nascimento, " +
                    "telefone, " +
                    "email, " +
                    "endereco_logradouro, " +
                    "endereco_numero, " +
                    "endereco_complemento, " +
                    "endereco_bairro, " +
                    "endereco_cep, " +
                    "endereco_cidade, " +
                    "endereco_uf " +
                    "FROM cliente " +
                    "WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            cliente = readValues(ps.executeQuery()).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public List<Cliente> listAll() {
        List<Cliente> clientes = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT " +
                    "id, " +
                    "cpf, " +
                    "nome, " +
                    "data_nascimento, " +
                    "telefone, " +
                    "email, " +
                    "endereco_logradouro, " +
                    "endereco_numero, " +
                    "endereco_complemento, " +
                    "endereco_bairro, " +
                    "endereco_cep, " +
                    "endereco_cidade, " +
                    "endereco_uf " +
                    "FROM cliente " +
                    "ORDER BY nome ASC;";
            PreparedStatement ps = connection.prepareStatement(sql);
            clientes = readValues(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    private List<Cliente> readValues(ResultSet rs) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong(1));
            cliente.setCpf(rs.getString(2));
            cliente.setNome(rs.getString(3));
            cliente.setDataNascimento(rs.getDate(4));
            cliente.setTelefone(rs.getString(5));
            cliente.setEmail(rs.getString(6));
            cliente.setEnderecoLogradouro(rs.getString(7));
            cliente.setEnderecoNumero(rs.getInt(8));
            cliente.setEnderecoComplemento(rs.getString(9));
            cliente.setEnderecoBairro(rs.getString(10));
            cliente.setEnderecoCep(rs.getString(11));
            cliente.setEnderecoCidade(rs.getString(12));
            cliente.setEnderecoUf(rs.getString(13));
            clientes.add(cliente);
        }
        return clientes;
    }
}
