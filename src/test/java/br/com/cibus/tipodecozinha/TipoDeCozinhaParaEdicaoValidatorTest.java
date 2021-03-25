package br.com.cibus.tipodecozinha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;

class TipoDeCozinhaParaEdicaoValidatorTest {

    private TipoDeCozinhaParaEdicaoForm tipoDeCozinhaParaEdicaoForm;
    private TipoDeCozinhaParaEdicaoValidator tipoDeCozinhaParaEdicaoValidator;
    private Errors errors;

    @BeforeEach
    void init() {
        tipoDeCozinhaParaEdicaoForm = new TipoDeCozinhaParaEdicaoForm();

        TipoDeCozinhaRepository repository = mock(TipoDeCozinhaRepository.class);
        when(repository.existsByNomeWithDifferentId(eq("Mexicana"), not(eq(1L)))).thenReturn(true);

        tipoDeCozinhaParaEdicaoValidator = new TipoDeCozinhaParaEdicaoValidator(repository);
        errors = mock(Errors.class);
    }

    @Test
    void quando_nome_ja_existe_e_id_for_o_mesmo_nao_da_erro() {
        tipoDeCozinhaParaEdicaoForm.setId(1L);
        tipoDeCozinhaParaEdicaoForm.setNome("Mexicana");

        tipoDeCozinhaParaEdicaoValidator.validate(tipoDeCozinhaParaEdicaoForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quando_nome_ja_existe_mas_id_for_diferente_deve_dar_erro() {
        tipoDeCozinhaParaEdicaoForm.setId(99L);
        tipoDeCozinhaParaEdicaoForm.setNome("Mexicana");

        tipoDeCozinhaParaEdicaoValidator.validate(tipoDeCozinhaParaEdicaoForm, errors);

        verify(errors).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quando_nome_nao_existe_e_id_for_o_mesmo_nao_da_erro() {
        tipoDeCozinhaParaEdicaoForm.setId(1L);
        tipoDeCozinhaParaEdicaoForm.setNome("Italiana");

        tipoDeCozinhaParaEdicaoValidator.validate(tipoDeCozinhaParaEdicaoForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }

    @Test
    void quando_nome_nao_existe_mas_id_for_diferente_nao_da_erro() {
        tipoDeCozinhaParaEdicaoForm.setId(99L);
        tipoDeCozinhaParaEdicaoForm.setNome("Italiana");

        tipoDeCozinhaParaEdicaoValidator.validate(tipoDeCozinhaParaEdicaoForm, errors);

        verify(errors, never()).rejectValue("nome", "nome.ja.existente", "Nome já existente");
    }
}