package br.com.cibus.relatoriorestaurante;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RelatorioDeRestaurantesPorTipoDeCozinhaController {

    private final RestauranteRepository restauranteRepository;

    public RelatorioDeRestaurantesPorTipoDeCozinhaController(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @GetMapping("/admin")
    public String listagem() {

        List<RelatorioRestaurantePorTipoDeCozinha> relatorioRestaurantePorTipoDeCozinhas = restauranteRepository.restaurantesPorTipoDeCozinha();

        return "relatorio-de-restaurante/listagem";
    }

}
