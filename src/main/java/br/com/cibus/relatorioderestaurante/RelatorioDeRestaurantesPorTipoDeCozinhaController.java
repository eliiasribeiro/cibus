package br.com.cibus.relatorioderestaurante;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RelatorioDeRestaurantesPorTipoDeCozinhaController {

    private final RestauranteRepository restauranteRepository;

    public RelatorioDeRestaurantesPorTipoDeCozinhaController(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @GetMapping("/admin")
    public String listagem(Model model) {
        List<RelatorioRestaurantePorTipoDeCozinha> relatorioRestaurantePorTipoDeCozinhas = restauranteRepository.restaurantesPorTipoDeCozinha();
        model.addAttribute("restaurantesPorTipoDeCozinha", relatorioRestaurantePorTipoDeCozinhas);
        return "relatorio-de-restaurante/listagem";
    }

}
