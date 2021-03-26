<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>

<custom:template title="Adicionar um Tipo de Cozinha">

    <form method="post" action="/admin/tipos-de-cozinha/novo">
        <label for="nome">Nome</label>
        <input id="nome" type="text" name="nome" maxlength="50" required>
        <form:errors path="tipoDeCozinhaParaAdicaoForm.nome"/>
        <input type="submit" value="Salvar">
        <a href="/admin/tipos-de-cozinha">Cancelar</a>
    </form>

</custom:template>