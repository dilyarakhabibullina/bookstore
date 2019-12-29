package ru.itpark.servlet;

import ru.itpark.constant.Constants;
import ru.itpark.domain.Book;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
        String url = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println(url);

        if (url.equals("/")) {
            try {
                req.setAttribute("items", bookService.getAll());
                req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
            return;
        }

        if (url.equals("/search")) {
            final String q = req.getParameter("q");
            final Collection<Book> items = bookService.searchByAuthor(q);

            req.setAttribute(Constants.ITEMS, items);
            req.setAttribute(Constants.SEARCH_QUERY, q);

            req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
            return;
        }

//        String url = req.getRequestURI().substring(req.getContextPath().length());
//        System.out.println(url);

//        if (url.equals("/search")) {
////            if (req.getMethod().equals("GET")) {
////                try {
////                    req.setAttribute(Constants.ITEMS, bookService.getAll());
////                } catch (SQLException e) {
////                    e.printStackTrace();
////                }
////                req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
////                return;
////            }
////        }

//        if (url.equals("/hous")) {
//            req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
//            resp.getWriter().write("super");
//            return;
//        }

    }

    //что означает req.getPart?
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var name = req.getParameter("name");
            var author = req.getParameter("author");
            var part = req.getPart("file");

            if (part.getSize() != 0) {
                var file = fileService.writeFile(part);
                bookService.create(name, author, file);
            } else {
                bookService.create(name, author, null);
            }

            resp.sendRedirect(req.getRequestURI());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        String url = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println(url);

//            if (url.equals("/search")) {
//                if (req.getMethod().equals("GET")) {
//                    try {
//                        req.setAttribute(Constants.ITEMS, bookService.getAll());
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
//                    return;
//                }
//            }
    }
}
