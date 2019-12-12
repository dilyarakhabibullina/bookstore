package ru.itpark.service;

import ru.itpark.domain.Book;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class BookService {
    private final DataSource ds;

    public BookService() throws NamingException, SQLException {
        var context = new InitialContext();
        ds = (DataSource) context.lookup("java:/comp/env/jdbc/db");
        try (var conn = ds.getConnection()) {
            try (var stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS books (id TEXT PRIMARY KEY, name TEXT NOT NULL, author TEXT NOT NULL, file TEXT);");
            }
        }
    }

    public List<Book> getAll() throws SQLException {
        try (var conn = ds.getConnection()) {
            try (var stmt = conn.createStatement()) {
                try (var rs = stmt.executeQuery("SELECT id, name, author, file FROM books;")) {
                    var list = new ArrayList<Book>();
                    while (rs.next()) {
                        list.add(new Book(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("author"),
                                rs.getString("file")
                        ));
                    }
                    return list;
                }
            }
        }
    }

    public void create(String name, String author, String file) throws SQLException {
        try (var conn = ds.getConnection()) {
            try (var stmt = conn.prepareStatement("INSERT INTO books (id, name, author, file) VALUES (?, ?, ?, ?)")) {
                stmt.setString(1, UUID.randomUUID().toString());
                stmt.setString(2, name.toLowerCase());
                stmt.setString(3, author.toLowerCase());
                stmt.setString(4, file.toLowerCase());
                stmt.execute();

            }
        }
    }






//    public Collection<Book> getAll() {
//        return books;
//    }

    public Collection<Book> searchByAuthor(String author) {
                    Collection<Book> books = new LinkedList<>();
//            books.add(new Book ("1","w","carrol","www"));
//            books.add(new Book ("1","w","hem","www"));
//            books.add(new Book ("1","w","lensioni","www"));

        return books.stream()
                .filter(o -> o.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

//    public Collection<House> searchByUnderground(String underground) {
//        return items.stream()
//                .filter(o -> o.getUndergrounds().contains(underground))
//                .collect(Collectors.toList());
//    }

}
