package persistence;

import connection.PostgreSQLConnection;
import model.Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class ClientePersistenceImpl extends UnicastRemoteObject implements ClientePersistence {

    public ClientePersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Cliente cliente) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO cliente (cpf, nome, data_nascimento, telefone, email, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_localidade, endereco_uf) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setDate(3, (Date) cliente.getDataNascimento());
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getEnderecoLogradouro());
            ps.setInt(7, cliente.getEnderecoNumero());
            ps.setString(8, cliente.getEnderecoComplemento());
            ps.setString(9, cliente.getEnderecoBairro());
            ps.setString(10, cliente.getEnderecoCep());
            ps.setString(11, cliente.getEnderecoLocalidade());
            ps.setString(12, cliente.getEnderecoUf());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente findById(Long id) {
        Cliente cliente = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT id, cpf, nome, data_nascimento, telefone, email, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_localidade, endereco_uf " +
                    "FROM cliente WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEnderecoLogradouro(rs.getString("endereco_logradouro"));
                cliente.setEnderecoNumero(rs.getInt("endereco_numero"));
                cliente.setEnderecoComplemento(rs.getString("endereco_complemento"));
                cliente.setEnderecoBairro(rs.getString("endereco_bairro"));
                cliente.setEnderecoCep(rs.getString("endereco_cep"));
                cliente.setEnderecoLocalidade(rs.getString("endereco_localidade"));
                cliente.setEnderecoUf(rs.getString("endereco_uf"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}
