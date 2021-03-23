package br.com.cibus.tipodecozinha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;

class TipoDeCozinhaParaAdicaoValidatorTest {

    private TipoDeCozinhaParaAdicaoForm tipoDeCozinhaParaAdicaoForm;
    private TipoDeCozinhaRepository repository;
    private TipoDeCozinhaParaAdicaoValidator tipoDeCozinhaParaAdicaoValidator;
    private Errors errors;

    @BeforeEach
    void init() {
        tipoDeCozinhaParaAdicaoForm = new TipoDeCozinhaParaAdicaoForm();

        repository = mock(TipoDeCozinhaRepository.class);
        when(repository.existsByNome("Mexicana")).thenReturn(true);

        tipoDeCozinhaParaAdicaoValidator = new TipoDeCozinhaParaAdicaoValidator(repository);

        errors = mock(Errors.class);
    }

    @Test
    void quandoNomeJaExisteDeveDarErro() {
        tipoDeCozinhaParaAdicaoForm.setNome("Mexicana");

        tipoDeCozinhaParaAdicaoValidator.validate(tipoDeCozinhaParaAdicaoForm, errors);

        verify(errors).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quandoNomeNaoExisteNaoDaErro() {
        tipoDeCozinhaParaAdicaoForm.setNome("Italiana");

        tipoDeCozinhaParaAdicaoValidator.validate(tipoDeCozinhaParaAdicaoForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

}