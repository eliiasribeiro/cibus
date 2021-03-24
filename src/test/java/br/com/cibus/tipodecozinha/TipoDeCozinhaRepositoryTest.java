package br.com.cibus.tipodecozinha;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class TipoDeCozinhaRepositoryTest {

    @Autowired
    private TipoDeCozinhaRepository tipoDeCozinhaRepository;

    @Test
    void deveListarOsNomesEmOrdemAlfabetica() {
        // Árabe, Baiana, Chinesa e Italiana já vem cadastradas
        tipoDeCozinhaRepository.saveAll(
                List.of(new TipoDeCozinha("Mexicana"),
                        new TipoDeCozinha("coreana"),
                        new TipoDeCozinha("Alemã")));

        List<TipoDeCozinha> tiposDeCozinha = tipoDeCozinhaRepository.findByOrderByNomeAsc();

        assertThat(tiposDeCozinha).hasSize(7);
        assertThat(tiposDeCozinha)
                .extracting(TipoDeCozinha::getNome)
                .containsExactly("Alemã", "Árabe", "Baiana", "Chinesa", "coreana", "Italiana", "Mexicana");
    }

    @Test
    void deveIndicarQuandoONomeJaExiste() {
        tipoDeCozinhaRepository.save(new TipoDeCozinha("Chilena"));

        boolean jaExisteQuandoFoiCadastrado = tipoDeCozinhaRepository.existsByNome("Chilena");
        assertThat(jaExisteQuandoFoiCadastrado).isTrue();

        boolean naoExisteQuandoAindaNaoFoiCadastrado = tipoDeCozinhaRepository.existsByNome("Contemporânea");
        assertThat(naoExisteQuandoAindaNaoFoiCadastrado).isFalse();
    }

    @Test
    void deveIndicarQuandoOutroTipoDeDoCozinhaJaTemOMesmoNome() {
        TipoDeCozinha indiana = tipoDeCozinhaRepository.save(new TipoDeCozinha("Indiana"));

        Long mesmoId = indiana.getId();
        boolean existeComMesmoNomeEMesmoId = tipoDeCozinhaRepository.existsByNomeAndIdNot("Indiana", mesmoId);
        assertThat(existeComMesmoNomeEMesmoId).isFalse();

        long outroIdQualquer = 999L;
        boolean existeComMesmoNomeMasComOutroId = tipoDeCozinhaRepository.existsByNomeAndIdNot("Indiana", outroIdQualquer);
        assertThat(existeComMesmoNomeMasComOutroId).isTrue();
    }
}
