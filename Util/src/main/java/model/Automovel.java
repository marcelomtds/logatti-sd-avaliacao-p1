package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Automovel implements Serializable {

    private static final long serialVersionUID = 1079592713556780115L;

    private Long id;
    private String placa;
    private Integer numeroPortas;
    private String tipoCombustivel;
    private String cor;
    private Integer ano;
    private String chassi;
    private BigDecimal valorDiaria;
    private Modelo modelo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getNumeroPortas() {
        return numeroPortas;
    }

    public void setNumeroPortas(Integer numeroPortas) {
        this.numeroPortas = numeroPortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(BigDecimal valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, placa: %s, número de portas: %d, tipo de combustível: %s, cor: %s, ano: %d, chassi: %s, valor da diária: %.2f",
                id, placa, numeroPortas, tipoCombustivel, cor, ano, chassi, valorDiaria);
    }
}
