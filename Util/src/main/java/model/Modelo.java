package model;

import java.io.Serializable;

public class Modelo implements Serializable {

    private static final long serialVersionUID = -3632928702026880978L;

    private Long id;
    private String descricao;
    private Marca marca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, descrição: %s", id, descricao);
    }
}
