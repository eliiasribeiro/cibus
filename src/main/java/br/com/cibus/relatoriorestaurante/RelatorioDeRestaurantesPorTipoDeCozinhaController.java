package br.com.cibus.relatoriorestaurante;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RelatorioDeRestaurantesPorTipoDeCozinhaController {

    @GetMapping("/admin")
    public String listagem() {
        return "relatorio-de-restaurante/listagem";
    }

}
