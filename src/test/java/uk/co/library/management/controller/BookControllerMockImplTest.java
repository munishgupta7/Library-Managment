package uk.co.library.management.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.co.library.management.model.Book;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({BookController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerMockImplTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Order(1)
    public void createBookTest() throws Exception {

        // Given & When
        final boolean status = new ObjectMapper().readValue(mockMvc.perform(
                post("/books/book")
                        .content(String.valueOf(constructedBookJsonData1()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), boolean.class);

        // Then
        assertThat(status).isTrue();
    }

    @Test
    @Order(2)
    void createBookTestThrowsException() throws Exception {

        // When
        mockMvc.perform(post("/books/book")
                .content(String.valueOf(constructedBookJsonData1()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> {
                    assertThat(result.getResolvedException()).isNotNull();
                    assertThat(result.getResolvedException().getMessage()).isEqualTo(
                            "409 CONFLICT \"Record with the given isbn: isbn1 already exists \"");
                });

    }

    @Test
    @Order(3)
    void getBookByIsbn() throws Exception {

        // Given & When
        final Book book = new ObjectMapper().readValue(
                mockMvc.perform(get("/books/book/isbn1"))
                        .andExpect(status().isOk()).andReturn().getResponse()
                        .getContentAsString(), Book.class);

        //Then
        assertThat(book).isEqualTo(constructedBookData());
    }

    @Test
    @Order(4)
    void getBookByIsbnThrowException() throws Exception {

        /// When
        mockMvc.perform(get("/books/book/isbn2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assertThat(result.getResolvedException()).isNotNull();
                    assertThat(result.getResolvedException().getMessage()).isEqualTo(
                            "404 NOT_FOUND \"Record not found for given isbn: isbn2 \"");
                });
    }

    @Test
    @Order(5)
    void updateBookData() throws Exception {

        // Given & When
        final boolean status = new ObjectMapper().readValue(mockMvc.perform(
                put("/books/book/isbn1")
                        .content(String.valueOf(constructedBookJsonData1Updated()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), boolean.class);

        //Then
        assertThat(status).isTrue();
    }

    @Test
    @Order(6)
    void updateBookDataThrowException() throws Exception {

        // When
        mockMvc.perform(put("/books/book/isbn2")
                .content(String.valueOf(constructedBookJsonData1Updated()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assertThat(result.getResolvedException()).isNotNull();
                    assertThat(result.getResolvedException().getMessage()).isEqualTo(
                            "404 NOT_FOUND \"Record not found for given isbn: isbn2 \"");
                });
    }

    @Test
    @Order(7)
    void getAllBookData() throws Exception {

        // Given & When
        final List<Book> bookList = new ObjectMapper().readValue(
                mockMvc.perform(get("/books/book"))
                        .andExpect(status().isOk()).andReturn().getResponse()
                        .getContentAsString(), new TypeReference<List<Book>>() {
                });

        //Then
        assertThat(bookList).isEqualTo(constructedBookList());
    }

    @Test
    @Order(8)
    void getAllBookDataThrowException() throws Exception {

        /// When
        mockMvc.perform(get("/books/book2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException()).isNull());
    }

    @Test
    @Order(9)
    void getBookByTitle() throws Exception {

        // Given & When
        final List<Book> book = new ObjectMapper().readValue(
                mockMvc.perform(get("/books/search/book/The 7 Habit of effective People"))
                        .andExpect(status().isOk()).andReturn().getResponse()
                        .getContentAsString(), new TypeReference<List<Book>>() {
                });

        //Then
        assertThat(book).isEqualTo(constructedBookList());
    }

    @Test
    @Order(10)
    void deleteBookData() throws Exception {

        // When
        mockMvc.perform(delete("/books/book/978-02-12345-55")
                .content(String.valueOf(constructedBookJsonData1Updated()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assertThat(result.getResolvedException()).isNotNull();
                    assertThat(result.getResolvedException().getMessage()).isEqualTo(
                            "404 NOT_FOUND \"Record not found for given isbn: 978-02-12345-55 \"");
                });

    }

    @Test
    @Order(11)
    void deleteBookDataThrowException() throws Exception {

        // When
        mockMvc.perform(delete("/books/book/978-02-12345-56")
                .content(String.valueOf(constructedBookJsonData1Updated()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assertThat(result.getResolvedException()).isNotNull();
                    assertThat(result.getResolvedException().getMessage()).isEqualTo(
                            "404 NOT_FOUND \"Record not found for given isbn: 978-02-12345-56 \"");
                });

    }

    private JSONObject constructedBookJsonData1() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("isbn", "isbn1");
        object.put("title", "Test1");
        object.put("author", "Test1");
        object.put("description", "Test1");
        object.put("tag1", "Test1");
        object.put("tag2", "Test1");
        return object;
    }

    private JSONObject constructedBookJsonData1Updated() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("isbn", "978-02-12345-55");
        object.put("title", "The 7 Habit of effective People");
        object.put("author", "Jack");
        object.put("description", "Test1");
        object.put("tag1", "The Power of Focus");
        object.put("tag2", "The 7 Habit of effective People");
        return object;
    }

    private List<Book> constructedBookList() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(constructedBookUpdatedData());
        return bookList;
    }

    private Book constructedBookUpdatedData() {
        Book book = new Book();
        book.setIsbn("978-02-12345-55");
        book.setTitle("The 7 Habit of effective People");
        book.setAuthor("Jack");
        book.setDescription("Test1");
        book.setTag1("The Power of Focus");
        book.setTag2("The 7 Habit of effective People");
        return book;
    }

    private Book constructedBookData() {
        Book book = new Book();
        book.setIsbn("isbn1");
        book.setTitle("Test1");
        book.setAuthor("Test1");
        book.setDescription("Test1");
        book.setTag1("Test1");
        book.setTag2("Test1");
        return book;
    }

}