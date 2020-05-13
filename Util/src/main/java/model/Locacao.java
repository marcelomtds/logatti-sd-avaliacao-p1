package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Locacao implements Serializable {

    private static final long serialVersionUID = -2151938904485960398L;

    private Long id;
    private Date dataLocacao;
    private Date dataDevolucao;
    private BigDecimal valor;
    private Automovel automovel;
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, data de locação: %s, data de devolução: %s, valor da locação: %.2f",
                id, new SimpleDateFormat("dd/MM/yyyy").format(dataLocacao), new SimpleDateFormat("dd/MM/yyyy").format(dataDevolucao), valor);
    }
}