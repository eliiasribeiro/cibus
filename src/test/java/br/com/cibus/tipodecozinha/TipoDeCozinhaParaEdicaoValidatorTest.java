package br.com.cibus.tipodecozinha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;

class TipoDeCozinhaParaEdicaoValidatorTest {

    private TipoDeCozinhaParaEdicaoForm tipoDeCozinhaParaEdicaoForm;
    private TipoDeCozinhaRepository repository;
    private TipoDeCozinhaParaEdicaoValidator tipoDeCozinhaParaEdicaoValidator;
    private Errors errors;

    @BeforeEach
    void init() {
        tipoDeCozinhaParaEdicaoForm = new TipoDeCozinhaParaEdicaoForm();

        repository = mock(TipoDeCozinhaRepository.class);
        when(repository.existsByNomeAndIdNot(eq("Mexicana"), not(eq(1L)))).thenReturn(true);

        tipoDeCozinhaParaEdicaoValidator = new TipoDeCozinhaParaEdicaoValidator(repository);
        errors = mock(Errors.class);
    }

    @Test
    void quandoNomeJaExisteEIdForOMesmoNaoDaErro() {
        tipoDeCozinhaParaEdicaoForm.setId(1L);
        tipoDeCozinhaParaEdicaoForm.setNome("Mexicana");

        tipoDeCozinhaParaEdicaoValidator.validate(tipoDeCozinhaParaEdicaoForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quandoNomeJaExisteMasIdForDiferenteDeveDarErro() {
        tipoDeCozinhaParaEdicaoForm.setId(99L);
        tipoDeCozinhaParaEdicaoForm.setNome("Mexicana");

        tipoDeCozinhaParaEdicaoValidator.validate(tipoDeCozinhaParaEdicaoForm, errors);

        verify(errors).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quandoNomeNaoExisteEIdForOMesmoNaoDaErro() {
        tipoDeCozinhaParaEdicaoForm.setId(1L);
        tipoDeCozinhaParaEdicaoForm.setNome("Italiana");

        tipoDeCozinhaParaEdicaoValidator.validate(tipoDeCozinhaParaEdicaoForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quandoNomeNaoExisteMasIdForDiferenteNaoDaErro() {
        tipoDeCozinhaParaEdicaoForm.setId(99L);
        tipoDeCozinhaParaEdicaoForm.setNome("Italiana");

        tipoDeCozinhaParaEdicaoValidator.validate(tipoDeCozinhaParaEdicaoForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }
}