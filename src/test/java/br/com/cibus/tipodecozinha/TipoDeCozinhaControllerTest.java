package br.com.cibus.tipodecozinha;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipoDeCozinhaController.class)
class TipoDeCozinhaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TipoDeCozinhaRepository tipoDeCozinhaRepository;

    @Test
    void lista() throws Exception {
        List<TipoDeCozinha> tiposDeCozinha = List.of(new TipoDeCozinha(1L,"Baiana"), new TipoDeCozinha(2L, "Italiana"));
        when(tipoDeCozinhaRepository.findByOrderByNomeAsc()).thenReturn(tiposDeCozinha);

        mockMvc.perform(get("/admin/tipos-de-cozinha"))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/listagem"))
                .andExpect(model().attribute("tiposDeCozinha", tiposDeCozinha));
    }

    @Test
    void mostra_formulario_adicionar() throws Exception {
        mockMvc.perform(get("/admin/tipos-de-cozinha/novo"))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/formulario-adicionar"));
    }

    @Test
    void adiciona() throws Exception {
        when(tipoDeCozinhaRepository.existsByNome("Mexicana")).thenReturn(false);

        mockMvc.perform(post("/admin/tipos-de-cozinha/novo")
                .param("nome", "Mexicana")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/admin/tipos-de-cozinha"));

        verify(tipoDeCozinhaRepository).save(any(TipoDeCozinha.class));
    }

    @Test
    void da_erro_ao_adicionar_com_nome_vazio_e_volta_para_formulario() throws Exception {
        mockMvc.perform(post("/admin/tipos-de-cozinha/novo")
                .param("nome", "")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/formulario-adicionar"));

        verify(tipoDeCozinhaRepository, never()).save(any(TipoDeCozinha.class));
    }

    @Test
    void da_erro_ao_adicionar_com_nome_muito_grande_e_volta_para_formulario() throws Exception {
        mockMvc.perform(post("/admin/tipos-de-cozinha/novo")
                .param("nome", "Cozinha Real Da Vossa Majestade Imperial Pedro de Alcântara João Carlos Leopoldo Salvador Bibiano Francisco Xavier de Paula Leocádio Miguel Gabriel Rafael Gonzaga de Bragança e Bourbon")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/formulario-adicionar"));

        verify(tipoDeCozinhaRepository, never()).save(any(TipoDeCozinha.class));
    }

    @Test
    void da_erro_ao_adicionar_com_nome_que_ja_existe_e_volta_para_formulario() throws Exception {
        when(tipoDeCozinhaRepository.existsByNome("Baiana")).thenReturn(true);

        mockMvc.perform(post("/admin/tipos-de-cozinha/novo")
                .param("nome", "Baiana")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/formulario-adicionar"));

        verify(tipoDeCozinhaRepository, never()).save(any(TipoDeCozinha.class));
    }

    @Test
    void mostra_formulario_editar() throws Exception {
        TipoDeCozinha baiana = new TipoDeCozinha(1L, "Baiana");
        when(tipoDeCozinhaRepository.findById(1L)).thenReturn(Optional.of(baiana));

        mockMvc.perform(get("/admin/tipos-de-cozinha/edicao/1")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/formulario-editar"))
                .andExpect(model().attribute("tipoDeCozinha", baiana));
    }

    @Test
    void edita() throws Exception {
        TipoDeCozinha baiana = new TipoDeCozinha(1L, "Baiana");
        when(tipoDeCozinhaRepository.findById(1L)).thenReturn(Optional.of(baiana));
        when(tipoDeCozinhaRepository.existsByNomeWithDifferentId("Nordestina", 1L)).thenReturn(false);

        mockMvc.perform(post("/admin/tipos-de-cozinha/edicao/1")
                .param("nome", "Nordestina")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/admin/tipos-de-cozinha"));

        verify(tipoDeCozinhaRepository).save(baiana);
    }

    @Test
    void da_erro_ao_editar_com_nome_vazio_e_volta_para_o_formulario() throws Exception {
        when(tipoDeCozinhaRepository.findById(1L)).thenReturn(Optional.of(new TipoDeCozinha(1L, "Baiana")));

        mockMvc.perform(post("/admin/tipos-de-cozinha/edicao/1")
                .param("nome", "")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/formulario-editar"));

        verify(tipoDeCozinhaRepository, never()).save(any(TipoDeCozinha.class));
    }

    @Test
    void da_erro_ao_editar_com_nome_muito_grande_e_volta_para_o_formulario() throws Exception {
        when(tipoDeCozinhaRepository.findById(1L)).thenReturn(Optional.of(new TipoDeCozinha(1L, "Baiana")));

        mockMvc.perform(post("/admin/tipos-de-cozinha/edicao/1")
                .param("nome", "Cozinha Real Da Vossa Majestade Imperial Pedro de Alcântara João Carlos Leopoldo Salvador Bibiano Francisco Xavier de Paula Leocádio Miguel Gabriel Rafael Gonzaga de Bragança e Bourbon")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/formulario-editar"));

        verify(tipoDeCozinhaRepository, never()).save(any(TipoDeCozinha.class));
    }

    @Test
    void da_erro_quando_nome_ja_existe_pra_outro_id_e_volta_para_formulario() throws Exception {
        TipoDeCozinha baiana = new TipoDeCozinha(1L, "Baiana");
        when(tipoDeCozinhaRepository.findById(1L)).thenReturn(Optional.of(baiana));
        when(tipoDeCozinhaRepository.existsByNomeWithDifferentId("Nordestina", 1L)).thenReturn(true);

        mockMvc.perform(post("/admin/tipos-de-cozinha/edicao/1")
                .param("nome", "Nordestina")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("tipo-de-cozinha/formulario-editar"));

        verify(tipoDeCozinhaRepository, never()).save(any(TipoDeCozinha.class));
    }

    @Test
    void remove() throws Exception {
        mockMvc.perform(post("/admin/tipos-de-cozinha/remocao/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/admin/tipos-de-cozinha"));

        verify(tipoDeCozinhaRepository).deleteById(1L);
    }

}