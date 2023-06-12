package com.challenge.library;

import com.challenge.library.model.param.BookParam;
import com.challenge.library.service.BookService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializeData(BookService bookService) {
        return (ApplicationArguments args) -> {
            List<BookParam> books = Arrays.asList(
                    new BookParam("The Great Gatsby", "F. Scott Fitzgerald", "Fiction"),
                    new BookParam("To Kill a Mockingbird", "Harper Lee", "Fiction"),
                    new BookParam("1984", "George Orwell", "Science Fiction"),
                    new BookParam("Pride and Prejudice", "Jane Austen", "Classic"),
                    new BookParam("The Catcher in the Rye", "J.D. Salinger", "Fiction"),
                    new BookParam("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
                    new BookParam("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
                    new BookParam("The Lord of the Rings", "J.R.R. Tolkien", "Fantasy"),
                    new BookParam("To Kill a Mockingbird", "Harper Lee", "Fiction"),
                    new BookParam("Animal Farm", "George Orwell", "Satire")
            );
            bookService.saveBooks(books);
        };
    }

}
