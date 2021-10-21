package uk.co.library.management.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.library.management.model.Book;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/books")
public interface BookController {

    /**
     * Create a book data
     *
     * @param book parameter
     * @return true if created else false
     * @throws ResponseStatusException CONFLICT if id already exists
     * @see Book
     */
    @PostMapping("/book")
    boolean createBookData(@RequestBody Book book);

    /**
     * Update book data.
     *
     * @param isbn primary key.
     * @param book book object to be updated.
     * @return true if updated else false.
     * @throws ResponseStatusException 404 if record not found for given id.
     * @see Book
     */
    @PutMapping("/book/{isbn}")
    boolean updateBookData(@PathVariable String isbn, @RequestBody Book book);

    /**
     * Delete book data.
     *
     * @param isbn primary key.
     * @return true if deleted else false.
     * @throws ResponseStatusException 404 if record not found for given id.
     * @see Book
     */
    @DeleteMapping("/book/{isbn}")
    void deleteBookData(@PathVariable String isbn);

    /**
     * Get all book data
     *
     * @return array of book data
     * @see Book
     */
    @GetMapping("/book")
    Collection<Book> getAllBookData();

    /**
     * Get Book data by isbn
     *
     * @param isbn primary key.
     * @return Book data for given isbn.
     * @throws ResponseStatusException NOT_FOUND if record not found for given ISBN
     * @see Book
     */
    @GetMapping("/book/{isbn}")
    Book getBookByIsbn(@PathVariable String isbn);

    /**
     * Get all book data
     *
     * @return array of book data
     * @see Book
     */
    @GetMapping("/search/book/{title}")
    List<Book> getAllBookByTitle(@PathVariable String title);

}
