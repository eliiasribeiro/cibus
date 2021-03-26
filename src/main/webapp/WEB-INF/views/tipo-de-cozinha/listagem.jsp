<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>

<custom:template title="Tipos de Cozinha">

    <h1>Tipos de cozinha</h1>
    <a href="/admin/tipos-de-cozinha/novo">Adicionar novo</a>
    <table>
        <tr>
            <th>Nome</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${tiposDeCozinha}" var="tipoDeCozinha">
            <tr>
                <td>${tipoDeCozinha.nome}</td>
                <td><a href="/admin/tipos-de-cozinha/edicao/${tipoDeCozinha.id}">Editar</a></td>
                <td>
                    <form method="post" action="/admin/tipos-de-cozinha/remocao/${tipoDeCozinha.id}">
                        <button type="submit">Remover</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</custom:template>
