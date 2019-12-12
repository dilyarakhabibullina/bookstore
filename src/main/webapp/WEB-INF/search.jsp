<%@ page import="ru.itpark.domain.Book" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.itpark.constant.Constants" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <form action="<%= request.getContextPath() %>/houses/search">
        <input name="district" placeholder="Поиск по району">
        <%-- если поле всего одно, то Enter приводит к отправке формы --%>
    </form>

    <ul>
        <% for (Book item : (Collection<Book>)request.getAttribute(Constants.ITEMS)) { %>
        <li>
            <%= item.getAuthor() %> : <%= item.getName() %>
            <form  action="<%= request.getContextPath() %>/search" method="post">
                <input type="hidden" name="id" value="<%= item.getId() %>">
                <input type="hidden" name="action" value="edit">
                <button>Редактировать</button>
            </form>
            <form  action="<%= request.getContextPath() %>/search" method="post">
                <input type="hidden" name="id" value="<%= item.getId() %>">
                <input type="hidden" name="action" value="remove">
                <button>Удалить</button>
            </form>
<%--            <ul>--%>
<%--                <% for (String underground : item.getUndergrounds()) { %>--%>
<%--                <li><%= underground %></li>--%>
<%--                <% } %>--%>
<%--            </ul>--%>
        </li>
        <% } %>
    </ul>

    <% Book item = (Book) request.getAttribute(Constants.ITEM); %>
    <form action="<%= request.getContextPath() %>/search" method="post">
        <input type="hidden" name="id" value="<%= item == null ? "" : item.getId() %>">
        <input type="hidden" name="action" value="save">
        <input name="name" placeholder="Название" value="<%= item == null ? "" : item.getName() %>">
        <input name="author" placeholder="Автор" value="<%= item == null ? "" : item.getAuthor() %>">
        <input name="piece" placeholder="Что ищем?" value="<%= item == null ? "" : item.getFile() %>">
        <button>Сохранить</button>
    </form>

</body>
</html>
