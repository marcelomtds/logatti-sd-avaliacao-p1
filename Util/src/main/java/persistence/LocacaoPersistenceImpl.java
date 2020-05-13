package persistence;

import connection.PostgreSQLConnection;
import exception.ResourceCannotRemovedException;
import exception.ResourceNotFoundException;
import model.*;
import org.apache.commons.lang3.ObjectUtils;

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
            String sql = "INSERT INTO locacao " +
                    "(data_locacao, " +
                    "data_devolucao, " +
                    "valor, " +
                    "id_automovel, " +
                    "id_cliente) " +
                    "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, new Date(locacao.getDataLocacao().getTime()));
            ps.setDate(2, new Date(locacao.getDataDevolucao().getTime()));
            ps.setBigDecimal(3, locacao.getValor());
            ps.setLong(4, locacao.getAutomovel().getId());
            ps.setLong(5, locacao.getCliente().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Locacao locacao) {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "UPDATE locacao SET " +
                    "data_locacao = ?, " +
                    "data_devolucao = ?, " +
                    "valor = ?, " +
                    "id_automovel = ?, " +
                    "id_cliente = ? " +
                    "WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, new Date(locacao.getDataLocacao().getTime()));
            ps.setDate(2, new Date(locacao.getDataDevolucao().getTime()));
            ps.setBigDecimal(3, locacao.getValor());
            ps.setLong(4, locacao.getAutomovel().getId());
            ps.setLong(5, locacao.getCliente().getId());
            ps.setLong(6, locacao.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            findById(id);
            String sql = "DELETE FROM locacao WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkLinkWithAutomovel(Long id) throws ResourceCannotRemovedException {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT * " +
                    "FROM locacao " +
                    "WHERE id_automovel = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            if (ps.executeQuery().next()) {
                throw new ResourceCannotRemovedException("O automóvel informado não pode ser removido porque está vinculado à uma locação.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkLinkWithCliente(Long id) throws ResourceCannotRemovedException {
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT * " +
                    "FROM locacao " +
                    "WHERE id_cliente = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            if (ps.executeQuery().next()) {
                throw new ResourceCannotRemovedException("O cliente informado não pode ser removido porque está vinculado à uma locação.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Locacao findById(Long id) throws ResourceNotFoundException {
        Locacao locacao = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT " +
                    "l.id, " +
                    "l.data_locacao, " +
                    "l.data_devolucao, " +
                    "l.valor, " +
                    "a.id, " +
                    "a.placa, " +
                    "a.numero_portas, " +
                    "a.tipo_combustivel, " +
                    "a.cor, " +
                    "a.ano, " +
                    "a.chassi, " +
                    "a.valor_diaria, " +
                    "c.id, " +
                    "c.cpf, " +
                    "c.nome, " +
                    "c.data_nascimento, " +
                    "c.telefone, " +
                    "c.email, " +
                    "c.endereco_logradouro, " +
                    "c.endereco_numero, " +
                    "c.endereco_complemento, " +
                    "c.endereco_bairro, " +
                    "c.endereco_cep, " +
                    "c.endereco_cidade, " +
                    "c.endereco_uf, " +
                    "mo.id, " +
                    "mo.descricao, " +
                    "ma.id, " +
                    "ma.descricao " +
                    "FROM locacao AS l " +
                    "INNER JOIN automovel AS a ON l.id_automovel = a.id " +
                    "INNER JOIN cliente AS c ON l.id_cliente = c.id " +
                    "INNER JOIN modelo AS mo ON a.id_modelo = mo.id " +
                    "INNER JOIN marca AS ma ON mo.id_marca = ma.id " +
                    "WHERE l.id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            List<Locacao> locacoes = readValues(ps.executeQuery());
            if (ObjectUtils.isNotEmpty(locacoes)) {
                locacao = locacoes.get(0);
            } else {
                throw new ResourceNotFoundException("A locação informada não foi encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locacao;
    }

    public List<Locacao> listAll() {
        List<Locacao> locacoes = null;
        try (Connection connection = PostgreSQLConnection.getConnetion()) {
            String sql = "SELECT " +
                    "l.id, " +
                    "l.data_locacao, " +
                    "l.data_devolucao, " +
                    "l.valor, " +
                    "a.id, " +
                    "a.placa, " +
                    "a.numero_portas, " +
                    "a.tipo_combustivel, " +
                    "a.cor, " +
                    "a.ano, " +
                    "a.chassi, " +
                    "a.valor_diaria, " +
                    "c.id, " +
                    "c.cpf, " +
                    "c.nome, " +
                    "c.data_nascimento, " +
                    "c.telefone, " +
                    "c.email, " +
                    "c.endereco_logradouro, " +
                    "c.endereco_numero, " +
                    "c.endereco_complemento, " +
                    "c.endereco_bairro, " +
                    "c.endereco_cep, " +
                    "c.endereco_cidade, " +
                    "c.endereco_uf, " +
                    "mo.id, " +
                    "mo.descricao, " +
                    "ma.id, " +
                    "ma.descricao " +
                    "FROM locacao AS l " +
                    "INNER JOIN automovel AS a ON l.id_automovel = a.id " +
                    "INNER JOIN cliente AS c ON l.id_cliente = c.id " +
                    "INNER JOIN modelo AS mo ON a.id_modelo = mo.id " +
                    "INNER JOIN marca AS ma ON mo.id_marca = ma.id " +
                    "ORDER BY l.data_locacao DESC;";
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
            locacao.setId(rs.getLong(1));
            locacao.setDataLocacao(rs.getDate(2));
            locacao.setDataDevolucao(rs.getDate(3));
            locacao.setValor(rs.getBigDecimal(4));
            Automovel automovel = new Automovel();
            automovel.setId(rs.getLong(5));
            automovel.setPlaca(rs.getString(6));
            automovel.setNumeroPortas(rs.getInt(7));
            automovel.setTipoCombustivel(rs.getString(8));
            automovel.setCor(rs.getString(9));
            automovel.setAno(rs.getInt(10));
            automovel.setChassi(rs.getString(11));
            automovel.setValorDiaria(rs.getBigDecimal(12));
            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong(13));
            cliente.setCpf(rs.getString(14));
            cliente.setNome(rs.getString(15));
            cliente.setDataNascimento(rs.getDate(16));
            cliente.setTelefone(rs.getString(17));
            cliente.setEmail(rs.getString(18));
            cliente.setEnderecoLogradouro(rs.getString(19));
            cliente.setEnderecoNumero(rs.getInt(20));
            cliente.setEnderecoComplemento(rs.getString(21));
            cliente.setEnderecoBairro(rs.getString(22));
            cliente.setEnderecoCep(rs.getString(23));
            cliente.setEnderecoCidade(rs.getString(24));
            cliente.setEnderecoUf(rs.getString(25));
            Modelo modelo = new Modelo();
            modelo.setId(rs.getLong(26));
            modelo.setDescricao(rs.getString(27));
            Marca marca = new Marca();
            marca.setId(rs.getLong(28));
            marca.setDescricao(rs.getString(29));
            modelo.setMarca(marca);
            automovel.setModelo(modelo);
            locacao.setAutomovel(automovel);
            locacao.setCliente(cliente);
            locacoes.add(locacao);
        }
        return locacoes;
    }
}
