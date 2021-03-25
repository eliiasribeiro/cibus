package br.com.cibus.tipodecozinha;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TipoDeCozinhaParaAdicaoValidator implements Validator {

    private TipoDeCozinhaRepository tipoDeCozinhaRepository;

    public TipoDeCozinhaParaAdicaoValidator(TipoDeCozinhaRepository tipoDeCozinhaRepository) {
        this.tipoDeCozinhaRepository = tipoDeCozinhaRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TipoDeCozinhaParaAdicaoForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TipoDeCozinhaParaAdicaoForm tipoDeCozinhaParaAdicaoForm = (TipoDeCozinhaParaAdicaoForm) target;

        if (tipoDeCozinhaRepository.existsByNome(tipoDeCozinhaParaAdicaoForm.getNome())) {
            errors.rejectValue("nome", "nome.ja.existente", "Nome já existente");
        }
     }
}
