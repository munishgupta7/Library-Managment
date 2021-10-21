package uk.co.library.management.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import uk.co.library.management.model.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("mock")
public class BookControllerMockImpl implements BookController {

    private final Map<String, Book> bookMap = new HashMap<>();

    @Override
    public boolean createBookData(Book book) {
        final String isbn = book.getIsbn();
        if (!(bookMap.containsKey(isbn))) {
            bookMap.put(isbn, book);
            return true;
        } else {
            final String errorMessage = String.format(
                    "Record with the given isbn: %s already exists ", isbn);
            throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
        }
    }

    @Override
    public boolean updateBookData(String isbn, Book book) {
        if (bookMap.containsKey(isbn)) {
            bookMap.put(isbn, book);
            return true;
        } else {
            final String errorMessage = String.format(
                    "Record not found for given isbn: %s ", isbn);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
    }

    @Override
    public void deleteBookData(String isbn) {
        if (bookMap.containsKey(isbn)) {
            bookMap.remove(isbn);
        } else {
            final String errorMessage = String.format(
                    "Record not found for given isbn: %s ", isbn);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
    }

    @Override
    public Collection<Book> getAllBookData() {
        return bookMap.values();
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        if (bookMap.containsKey(isbn))
            return bookMap.get(isbn);
        else {
            final String errorMessage = String.format(
                    "Record not found for given isbn: %s ", isbn);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
    }

    @Override
    public List<Book> getAllBookByTitle(String title) {
        Book bookSearch = new Book();
        List<Book> search = new ArrayList<>();
        Collection<Book> books = bookMap.values();
        books.forEach(book -> {
            if (book.getTitle().equals(title)) {
                bookSearch.setTitle(book.getTitle());
                bookSearch.setAuthor(book.getAuthor());
                bookSearch.setDescription(book.getDescription());
                bookSearch.setIsbn(book.getIsbn());
                bookSearch.setTag1(book.getTag1());
                bookSearch.setTag1(book.getTag2());
                search.add(book);
            }
        });
        return search;
    }
}
