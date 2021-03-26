package br.com.cibus.relatoriorestaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    @Query(value = "select count(r.id) as quantidadeDeRestaurantes, r.tipoDeCozinha.nome as nomeTipoDeCozinha from Restaurante r group by r.tipoDeCozinha.nome order by r.tipoDeCozinha.nome")
    List<RelatorioRestaurantePorTipoDeCozinha> restaurantesPorTipoDeCozinha();
}
