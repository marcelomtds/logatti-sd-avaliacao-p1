package model;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {

    private static final long serialVersionUID = 7106801829663849989L;

    private Long id;
    private String cpf;
    private String nome;
    private Date dataNascimento;
    private String telefone;
    private String email;
    private String EnderecoLogradouro;
    private Integer EnderecoNumero;
    private String EnderecoComplemento;
    private String EnderecoBairro;
    private String EnderecoCep;
    private String EnderecoLocalidade;
    private String EnderecoUf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnderecoLogradouro() {
        return EnderecoLogradouro;
    }

    public void setEnderecoLogradouro(String enderecoLogradouro) {
        EnderecoLogradouro = enderecoLogradouro;
    }

    public Integer getEnderecoNumero() {
        return EnderecoNumero;
    }

    public void setEnderecoNumero(Integer enderecoNumero) {
        EnderecoNumero = enderecoNumero;
    }

    public String getEnderecoComplemento() {
        return EnderecoComplemento;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        EnderecoComplemento = enderecoComplemento;
    }

    public String getEnderecoBairro() {
        return EnderecoBairro;
    }

    public void setEnderecoBairro(String enderecoBairro) {
        EnderecoBairro = enderecoBairro;
    }

    public String getEnderecoCep() {
        return EnderecoCep;
    }

    public void setEnderecoCep(String enderecoCep) {
        EnderecoCep = enderecoCep;
    }

    public String getEnderecoLocalidade() {
        return EnderecoLocalidade;
    }

    public void setEnderecoLocalidade(String enderecoLocalidade) {
        EnderecoLocalidade = enderecoLocalidade;
    }

    public String getEnderecoUf() {
        return EnderecoUf;
    }

    public void setEnderecoUf(String enderecoUf) {
        EnderecoUf = enderecoUf;
    }
}
