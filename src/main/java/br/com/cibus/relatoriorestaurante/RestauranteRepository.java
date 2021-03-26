package br.com.cibus.relatoriorestaurante;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    //public RelatorioRestaurantePorTipoDeCozinha getRestaurantesPor
}
