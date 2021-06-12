package br.com.cibus.restaurante;

import br.com.cibus.tipodecozinha.TipoDeCozinha;
import br.com.cibus.tipodecozinha.TipoDeCozinhaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.util.stream.Collectors.toMap;

@Component
class ImportaRestaurantesAPartirDeUmCsv implements CommandLineRunner {

    private final TipoDeCozinhaRepository tipoDeCozinhaRepository;

    ImportaRestaurantesAPartirDeUmCsv(TipoDeCozinhaRepository tipoDeCozinhaRepository) {
        this.tipoDeCozinhaRepository = tipoDeCozinhaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String urlDoCsv = "https://docs.google.com/spreadsheets/d/1MVFOLpLt8N1uZHWdjhkJzyVoNDJqEaWY9j_4yOxxkWI/export?format=csv&id=1MVFOLpLt8N1uZHWdjhkJzyVoNDJqEaWY9j_4yOxxkWI&gid=0";

        List<String[]> dadosDosRestaurantes = new ArrayList<>();
        int count = 0;
        try (
                InputStream inputStream = new FileInputStream(ResourceUtils.getFile("classpath:Restaurantes - Dados.csv"));
//                InputStream inputStream = new URL(urlDoCsv).openStream();
                Scanner input = new Scanner(inputStream)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (count > 0) {
                    dadosDosRestaurantes.add(line.split(",\\s*(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
                }
                count++;
            }
        }

        Map<String, Long> idsDosTiposDeCozinhaPorNome = dadosDosRestaurantes.stream()
                .map(dadosDoRestaurante -> dadosDoRestaurante[1])
                .distinct()
                .map(tipoDeCozinhaRepository::findByNome)
                .collect(toMap(TipoDeCozinha::getNome, TipoDeCozinha::getId));

        String sqlTemplate = "insert into restaurante (nome, cnpj, endereco, cep, descricao, tipo_de_cozinha_id)" +
                " values ('%s', '%s', '%s', '%s', '%s', %d);";

        try (OutputStream outputStream = new FileOutputStream("src/main/resources/db/migration/V2021.03.26.16.15.30__carga_inicial_tabela_restaurante.sql", false);
             PrintStream output = new PrintStream(outputStream)) {
            dadosDosRestaurantes.forEach(dadosDoRestaurante -> {

                String nome = dadosDoRestaurante[0];
                String nomeDoTipoDeCozinha = dadosDoRestaurante[1];
                String cnpj = dadosDoRestaurante[2];
                String endereco = dadosDoRestaurante[3].replace("\"", "");
                String cep = dadosDoRestaurante[4];
                String descricao = dadosDoRestaurante.length > 5 ? dadosDoRestaurante[5].replace("\"", "") : "";

                Long idDoTipoDeCozinha = idsDosTiposDeCozinhaPorNome.get(nomeDoTipoDeCozinha);

                output.println(String.format(sqlTemplate, nome, cnpj, endereco, cep, descricao, idDoTipoDeCozinha));
            });
        }

    }
}
