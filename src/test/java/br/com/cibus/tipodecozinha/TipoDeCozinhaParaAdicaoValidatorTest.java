package br.com.cibus.tipodecozinha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

class TipoDeCozinhaParaAdicaoValidatorTest {

    private TipoDeCozinhaParaAdicaoForm tipoDeCozinhaParaAdicaoForm;
    private TipoDeCozinhaParaAdicaoValidator tipoDeCozinhaParaAdicaoValidator;
    private Errors errors;

    @BeforeEach
    void init() {
        tipoDeCozinhaParaAdicaoForm = new TipoDeCozinhaParaAdicaoForm();

        TipoDeCozinhaRepository repository = mock(TipoDeCozinhaRepository.class);
        when(repository.existsByNome("Mexicana")).thenReturn(true);

        tipoDeCozinhaParaAdicaoValidator = new TipoDeCozinhaParaAdicaoValidator(repository);

        errors = mock(Errors.class);
    }

    @Test
    void quando_nome_ja_existe_deve_dar_erro() {
        tipoDeCozinhaParaAdicaoForm.setNome("Mexicana");

        tipoDeCozinhaParaAdicaoValidator.validate(tipoDeCozinhaParaAdicaoForm, errors);

        verify(errors).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quando_nome_nao_existe_nao_da_erro() {
        tipoDeCozinhaParaAdicaoForm.setNome("Italiana");

        tipoDeCozinhaParaAdicaoValidator.validate(tipoDeCozinhaParaAdicaoForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

}