package com.challenge.library.controller;

import com.challenge.library.model.param.BookParam;
import com.challenge.library.model.vo.BookVO;
import com.challenge.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public List<BookVO> findAll() throws IOException {
        return bookService.findAll();
    }

    @PostMapping
    public void saveBook(@RequestBody @Valid BookParam bookParam) throws IOException {
        bookService.saveBook(bookParam);
    }

    @PostMapping("/collection")
    public void saveBook(@RequestBody @Valid List<BookParam> bookParams) throws IOException {
        bookService.saveBooks(bookParams);
    }

    @PostMapping("/index")
    public void buildIndex() throws IOException {
        bookService.buildIndex();
    }

    @PostMapping("/clear-and-index")
    public void clearAndIndexAll() throws IOException {
        bookService.clearAndIndexAll();
    }

    @GetMapping("/search")
    public List<BookVO> searchBooks(
            @RequestParam("q") String searchTerm,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "fields", required = false) String fields,
            @RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
            @RequestParam(value = "order", defaultValue = "ASC") String order
    ) throws IOException {
        return bookService.searchBooks(searchTerm, page, size, fields, orderBy, order);
    }

}
