package persistence;

import connection.PostgreSQLConnection;
import model.Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientePersistenceImpl extends UnicastRemoteObject implements ClientePersistence {

    public ClientePersistenceImpl() throws RemoteException {
        super();
    }

    public void create(Cliente cliente) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "INSERT INTO cliente (cpf, nome, data_nascimento, telefone, email, end_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_localidade, endereco_uf) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setDate(3, Date.valueOf(cliente.getDataNascimento()));
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
}
