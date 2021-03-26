package br.com.cibus.relatoriorestaurante;

import br.com.cibus.tipodecozinha.TipoDeCozinha;

import javax.persistence.*;

@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TipoDeCozinha tipoDeCozinha;

}
