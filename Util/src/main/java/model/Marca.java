package model;

import java.io.Serializable;

public class Marca implements Serializable {

    private static final long serialVersionUID = 5388928189437167613L;

    private Long id;
    private String descricao;

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

    @Override
    public String toString() {
        return String.format("ID: %d, descrição: %s", id, descricao);
    }
}
