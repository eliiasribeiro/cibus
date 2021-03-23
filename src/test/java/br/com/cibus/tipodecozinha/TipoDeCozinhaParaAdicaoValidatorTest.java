package br.com.cibus.tipodecozinha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;

class TipoDeCozinhaParaAdicaoValidatorTest {

    private TipoDeCozinhaForm tipoDeCozinhaForm;
    private TipoDeCozinhaRepository repository;
    private TipoDeCozinhaParaAdicaoValidator tipoDeCozinhaParaAdicaoValidator;
    private Errors errors;

    @BeforeEach
    void init() {
        tipoDeCozinhaForm = new TipoDeCozinhaForm();

        repository = mock(TipoDeCozinhaRepository.class);
        when(repository.existsByNome("Mexicana")).thenReturn(true);

        tipoDeCozinhaParaAdicaoValidator = new TipoDeCozinhaParaAdicaoValidator(repository);

        errors = mock(Errors.class);
    }

    @Test
    void quandoNomeJaExisteDeveDarErro() {
        tipoDeCozinhaForm.setNome("Mexicana");

        tipoDeCozinhaParaAdicaoValidator.validate(tipoDeCozinhaForm, errors);

        verify(errors).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quandoNomeNaoExisteNaoDaErro() {
        tipoDeCozinhaForm.setNome("Italiana");

        tipoDeCozinhaParaAdicaoValidator.validate(tipoDeCozinhaForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

}