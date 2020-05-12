package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cliente implements Serializable {

    private static final long serialVersionUID = 7106801829663849989L;

    private Long id;
    private String cpf;
    private String nome;
    private Date dataNascimento;
    private String telefone;
    private String email;
    private String enderecoLogradouro;
    private Integer enderecoNumero;
    private String enderecoComplemento;
    private String enderecoBairro;
    private String enderecoCep;
    private String enderecoCidade;
    private String enderecoUf;

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
        return enderecoLogradouro;
    }

    public void setEnderecoLogradouro(String enderecoLogradouro) {
        this.enderecoLogradouro = enderecoLogradouro;
    }

    public Integer getEnderecoNumero() {
        return enderecoNumero;
    }

    public void setEnderecoNumero(Integer enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    public String getEnderecoBairro() {
        return enderecoBairro;
    }

    public void setEnderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
    }

    public String getEnderecoCep() {
        return enderecoCep;
    }

    public void setEnderecoCep(String enderecoCep) {
        this.enderecoCep = enderecoCep;
    }

    public String getEnderecoCidade() {
        return enderecoCidade;
    }

    public void setEnderecoCidade(String enderecoCidade) {
        this.enderecoCidade = enderecoCidade;
    }

    public String getEnderecoUf() {
        return enderecoUf;
    }

    public void setEnderecoUf(String enderecoUf) {
        this.enderecoUf = enderecoUf;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, CPF: %s, nome: %s, data de nascimento: %s, telefone: %s, e-mail: %s " +
                        "- Endereço = Logradouro: %s, número: %d, complemento: %s, bairro: %s, CEP: %s, cidade: %s, UF: %s",
                id, cpf, nome, new SimpleDateFormat("dd/MM/yyyy").format(dataNascimento) , telefone, email,
                enderecoLogradouro, enderecoNumero, enderecoComplemento, enderecoBairro, enderecoCep, enderecoCidade, enderecoUf);
    }
}
