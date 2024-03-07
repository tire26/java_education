package ru.aston.hw4.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.aston.hw4.dto.ArticleCreationDto;
import ru.aston.hw4.dto.ArticleCreationWithIdDto;
import ru.aston.hw4.dto.ArticleResponseDto;
import ru.aston.hw4.service.ArticleService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "ArticleServlet", urlPatterns = "/articles")
public class ArticleServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        Gson gson = new Gson();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<ArticleResponseDto> articles = articleService.getAll();
            String json = gson.toJson(articles);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(json);
            resp.getWriter().flush();
        } else {
            long articleId = Long.parseLong(pathInfo.substring(1));
            Optional<ArticleResponseDto> articleOptional = articleService.get(articleId);

            if (articleOptional.isPresent()) {
                ArticleResponseDto articleResponseDto = articleOptional.get();
                String json = gson.toJson(articleResponseDto);
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
        ArticleCreationDto articleCreationDto = objectMapper.readValue(requestBody, ArticleCreationDto.class);
        articleService.save(articleCreationDto);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining());
        ArticleCreationWithIdDto articleCreationWithIdDto = objectMapper.readValue(requestBody, ArticleCreationWithIdDto.class);
        articleService.update(articleCreationWithIdDto);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        articleService.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
