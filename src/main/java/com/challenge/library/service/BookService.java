package com.challenge.library.service;

import com.challenge.library.indexer.BookIndexReader;
import com.challenge.library.indexer.BookIndexWriter;
import com.challenge.library.mapper.BookMapper;
import com.challenge.library.model.param.BookParam;
import com.challenge.library.model.po.Book;
import com.challenge.library.model.vo.BookVO;
import com.challenge.library.repository.BookRepository;
import com.challenge.library.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final static String DELIMITER = ",";

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookIndexWriter bookIndexWriter;
    private final BookIndexReader bookIndexReader;

    public void saveBooks(List<BookParam> bookParams) throws IOException {
        // Check if the books params is empty or null before processing them
        if (bookParams == null || bookParams.isEmpty()) return;
        // Persist all provided books
        for (BookParam bookParam : bookParams) {
            this.saveBook(bookParam);
        }
    }

    public void saveBook(BookParam bookParam) throws IOException {
        // Mapping bookParam to book
        Book book = bookMapper.map(bookParam);
        // Update isIndexed to true
        book.setIndexed(true);
        // Set indexed on
        book.setIndexedOn(new Date());
        // Save the book within H2 database
        book = bookRepository.save(book);
        // Add the persisted book for indexing using Apache Lucene
        bookIndexWriter.index(book);
    }

    // Index only unindexed books
    public void buildIndex() throws IOException {
        List<Book> unIndexedBooks = bookRepository.findByIsIndexed(false);
        bookIndexWriter.index(unIndexedBooks);
    }

    // Clear and index all existing books
    public void clearAndIndexAll() throws IOException {
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> {
            // Update isIndexed to true
            book.setIndexed(true);
            // Set indexed on
            book.setIndexedOn(new Date());
            // Save the book within H2 database
            bookRepository.save(book);
        });
        bookIndexWriter.clearAndIndexAll(books);
    }

    public List<BookVO> findAll() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.map(books);
    }

    public List<BookVO> searchBooks(String searchTerm, int page, int size, String searchFieldsParam, String orderByField, String order) throws IOException {
        // Convert and split search fields params
        String[] searchFieldsSplitted = StringUtils.split(searchFieldsParam, DELIMITER);
        // Search for books based on the provided params
        List<Book> books = bookIndexReader.searchBooks(searchTerm, page, size, searchFieldsSplitted, orderByField, order);
        // Map and return the list of founded books
        return this.bookMapper.map(books);
    }

}
