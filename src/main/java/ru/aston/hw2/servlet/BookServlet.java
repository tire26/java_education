package ru.aston.hw2.servlet;

import com.google.gson.Gson;
import ru.aston.hw2.entity.Book;
import ru.aston.hw2.entity.Genre;
import ru.aston.hw2.service.BookService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "BookServlet", urlPatterns = "/books")
public class BookServlet extends HttpServlet {

    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        Gson gson = new Gson();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<Book> books = bookService.getAll();
            String json = gson.toJson(books);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(json);
            resp.getWriter().flush();
        } else {
            long bookId = Long.parseLong(pathInfo.substring(1));
            Optional<Book> bookOptional = bookService.get(bookId);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                String json = gson.toJson(book);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().print(json);
                resp.getWriter().flush();
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String name = req.getParameter("name");
        int year = Integer.parseInt(req.getParameter("year"));
        int genreId = Integer.parseInt(req.getParameter("genre_id"));
        Genre genre = new Genre(genreId, null);

        Book book = new Book(0, name, year, genre);
        bookService.save(book);

        String json = gson.toJson(book);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(json);
        resp.getWriter().flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        long bookId = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        int year = Integer.parseInt(req.getParameter("year"));
        int genreId = Integer.parseInt(req.getParameter("genre_id"));
        Genre genre = new Genre(genreId, null);

        Book book = new Book((int) bookId, name, year, genre);
        bookService.update(book);

        String json = gson.toJson(book);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(json);
        resp.getWriter().flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        long bookId = Long.parseLong(req.getParameter("id"));
        Book book = new Book((int) bookId, null, 0, null);
        bookService.delete(book);

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
