package ru.aston.hw4.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.aston.hw4.dto.BookCreationDto;
import ru.aston.hw4.dto.BookCreationWithIdDto;
import ru.aston.hw4.dto.BookResponseDto;
import ru.aston.hw4.dto.GenreDto;
import ru.aston.hw4.service.BookService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "BookServlet", urlPatterns = "/books")
public class BookServlet extends HttpServlet {

    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        Gson gson = new Gson();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<BookResponseDto> books = bookService.getAll();
            String json = gson.toJson(books);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(json);
            resp.getWriter().flush();
        } else {
            long bookId = Long.parseLong(pathInfo.substring(1));
            Optional<BookResponseDto> bookOptional = bookService.get(bookId);

            if (bookOptional.isPresent()) {
                BookResponseDto book = bookOptional.get();
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
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining());
        BookCreationDto book = objectMapper.readValue(requestBody, BookCreationDto.class);
        bookService.save(book);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining());
        BookCreationWithIdDto book = objectMapper.readValue(requestBody, BookCreationWithIdDto.class);
        bookService.update(book);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        bookService.delete(id);

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
