package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = new Book("1234567890", "Title", "Author", 9.90);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathValue("@.price").isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var book = new Book("1234567890", "Title", "Author", 9.90);
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.90
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(book);
    }
}
