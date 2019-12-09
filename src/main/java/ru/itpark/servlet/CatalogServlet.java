package ru.itpark.servlet;

import ru.itpark.service.BookService;
import ru.itpark.service.FileService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CatalogServlet extends HttpServlet {
    private BookService bookService;
    private FileService fileService;

    @Override
    public void init() throws ServletException {
        InitialContext context = null;
        try {
            context = new InitialContext();
            //что означают строки ниже? папки comp, env, bean?
            bookService = (BookService) context.lookup("java:/comp/env/bean/auto-service");
            fileService = (FileService) context.lookup("java:/comp/env/bean/file-service");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("items", bookService.getAll());
            req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
//что означает req.getPart?
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var name = req.getParameter("name");
            var author = req.getParameter("author");
            var part = req.getPart("file");

            var file = fileService.writeFile(part);

            bookService.create(name, author, file);
            resp.sendRedirect(String.join("/", req.getContextPath(), req.getServletPath()));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
