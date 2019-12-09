<%@ page import="ru.itpark.domain.Book" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Auto Catalog</title>
    <%@ include file="bootstrap-css.jsp" %>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>Catalog</h1>
            <div class="row">
                <%--
        1. откуда взялось items в качестве параметра getAttribute?
      --%>

                <% if (request.getAttribute("items") != null) { %>
                <%for (Book item : (List<Book>) request.getAttribute("items")) {%>
                <div class="col-sm-6 mt-3">
                    <div class="card">
                        <%--
        2. что означает строчка ниже?  --%>
                        <img src="<%= request.getContextPath() %>/files/<%= item.getFile() %>" class="card-img-top"
                             alt="<%=item.getName() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%=item.getName() %>
                            </h5>
                            <p class="card-text"><%=item.getAuthor() %>
                            </p>
                        </div>
                    </div>
                </div>
                <%}%>
                <%}%>
            </div>

            <form action="<%= request.getContextPath() %>" method="post" enctype="multipart/form-data" class="mt-3">
                <div class="form-group">
                    <label for="name">Book Title</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="author">Author</label>
                    <textarea id="author" name="author" class="form-control" required></textarea>
                </div>
                <div class="custom-file">
                    <input type="file" id="file" name="file" class="custom-file-input" accept="file/*">
                    <label class="custom-file-label" for="file">Choose file...</label>
                </div>
                <button type="submit" class="btn btn-primary mt-3">Create</button>

            </form>
        </div>
    </div>
</div>
<%@ include file="bootstrap-scripts.jsp" %>
</body>
</html>