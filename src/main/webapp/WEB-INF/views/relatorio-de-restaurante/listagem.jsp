<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <title>Relatório de Restaurantes por tipo de cozinha</title>
</head>
<body>

<h1>Relatório</h1>
<h2>Restaurantes por tipo de cozinha</h2>
<table>
    <tr>
        <th>Tipo de Cozinha</th>
        <th>Qtd.</th>
    </tr>
    <c:forEach items="${restaurantesPorTipoDeCozinha}" var="restaurantePorTipoDeCozinha">
        <tr>
            <td>${restaurantePorTipoDeCozinha.nomeTipoDeCozinha}</td>
            <td>${restaurantePorTipoDeCozinha.quantidadeDeRestaurantes}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>