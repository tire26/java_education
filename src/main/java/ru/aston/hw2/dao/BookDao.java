package ru.aston.hw2.dao;

import ru.aston.hw2.entity.Author;
import ru.aston.hw2.entity.Book;
import ru.aston.hw2.entity.Genre;
import ru.aston.hw2.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao implements Dao<Book> {
    @Override
    public Optional<Book> get(long bookId) {
        String query = "SELECT b.id, b.name, b.year, g.id AS genre_id, g.name AS genre_name, a.id AS author_id, a.name AS author_name " +
                "FROM books b " +
                "JOIN genres g ON b.genre_id = g.id " +
                "JOIN authors_books ab ON b.id = ab.id_book " +
                "JOIN authors a ON ab.id_author = a.id " +
                "WHERE b.id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bookId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = bookFromResultSet(resultSet);
                    return Optional.of(book);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getAll() {
        String query = "SELECT b.id, b.name, b.year, g.id AS genre_id, g.name AS genre_name, a.id AS author_id, a.name AS author_name " +
                "FROM books b " +
                "JOIN genres g ON b.genre_id = g.id " +
                "JOIN authors_books ab ON b.id = ab.id_book " +
                "JOIN authors a ON ab.id_author = a.id ";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery(query)) {
                List<Book> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    Book book = bookFromResultSet(resultSet);
                    resultList.add(book);
                }
                return resultList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Book bookFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Integer year = resultSet.getInt("year");

        int genreId = resultSet.getInt("genre_id");
        String genreName = resultSet.getString("genre_name");
        Genre genre = new Genre(genreId, genreName);

        List<Author> authors = new ArrayList<>();
        int author_id;
        String author_name;
        do {
            author_id = resultSet.getInt("author_id");
            author_name = resultSet.getString("author_name");
            authors.add(new Author(author_id, author_name));
        } while (resultSet.next());

        Book book = new Book(id, name, year, genre);
        book.addAll(authors);
        return book;
    }

    @Override
    public void save(Book book) {
        String query = "INSERT INTO books(id, name, genre_id, year)" +
                " VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setInt(3, book.getGenre().getId());
            statement.setInt(4, book.getYear());
            int ignored = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        String query = "UPDATE books SET name = ? AND year = ? AND genre_id = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(4, book.getId());
            statement.setString(1, book.getName());
            statement.setInt(3, book.getGenre().getId());
            statement.setInt(2, book.getYear());
            int ignored = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Book book) {
        String query = "DELETE FROM aston_demo.public.books WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, book.getId());
            int ignored = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
