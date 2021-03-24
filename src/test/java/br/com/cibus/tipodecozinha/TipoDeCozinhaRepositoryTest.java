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
    void deve_listar_os_nomes_em_ordem_alfabetica() {
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
    void deve_indicar_quando_o_nome_ja_existe() {
        tipoDeCozinhaRepository.save(new TipoDeCozinha("Chilena"));

        boolean jaExiste = tipoDeCozinhaRepository.existsByNome("Chilena");
        assertThat(jaExiste).isTrue();
    }

    @Test
    void deve_indicar_quando_o_nome_ainda_nao_existe() {
        boolean jaExiste = tipoDeCozinhaRepository.existsByNome("Contemporânea");
        assertThat(jaExiste).isFalse();
    }

    @Test
    void deve_indicar_quando_outro_tipo_de_cozinha_ja_tem_o_mesmo_nome() {
        TipoDeCozinha indiana = tipoDeCozinhaRepository.save(new TipoDeCozinha("Indiana"));

        Long mesmoId = indiana.getId();
        boolean existeComMesmoNomeEMesmoId = tipoDeCozinhaRepository.existsByNomeAndIdNot("Indiana", mesmoId);
        assertThat(existeComMesmoNomeEMesmoId).isFalse();

        long outroIdQualquer = 999L;
        boolean existeComMesmoNomeMasComOutroId = tipoDeCozinhaRepository.existsByNomeAndIdNot("Indiana", outroIdQualquer);
        assertThat(existeComMesmoNomeMasComOutroId).isTrue();
    }
}
