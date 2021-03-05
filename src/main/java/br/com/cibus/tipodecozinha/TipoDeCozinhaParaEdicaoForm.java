package br.com.cibus.tipodecozinha;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TipoDeCozinhaParaEdicaoForm {

    @NotNull
    @Min(1)
    private Long id;

    @NotBlank
    @Size(min=1, max = 50)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    TipoDeCozinha toEntity() {
        return new TipoDeCozinha(this.id, this.nome);
    }

}
