package com.challenge.library.indexer;

import com.challenge.library.model.po.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookIndexReader {

    // Analyzer strategy
    private static final Analyzer ANALYSER_STRATEGY = new StandardAnalyzer();

    // The directory that will hold all our indexed fields
    private final Directory indexDirectory;

    // Default searching fields if there are fields provided
    private final String[] searchFieldsProperty;

    public BookIndexReader(@Value("${lucene.book.indexing-file-path}") String indexDirectory,
                           @Value("${lucene.book.search-fields}") String[] searchFieldsProperty) throws IOException {
        // Get specified path from properties
        Path indexDirectoryPath = Paths.get(indexDirectory);
        // Check if the path exist
        if (!Files.exists(indexDirectoryPath)) {
            // Create path if not exist
            Files.createDirectories(indexDirectoryPath);
            // Configure the path as a new path for Lucene configuration
            this.indexDirectory = new MMapDirectory(indexDirectoryPath);
        } else {
            // If the path exist we use the previous specified path which hold all indexed elements of our project.
            this.indexDirectory = MMapDirectory.open(indexDirectoryPath);
        }
        this.searchFieldsProperty = searchFieldsProperty;
    }

    public List<Book> searchBooks(String searchTerm, int page, int size, String[] searchFieldsParam, String orderByField, String order) throws IOException {
        // Create instance for index reader using the configured index directory
        IndexReader INDEX_READER = DirectoryReader.open(indexDirectory);
        // Create an index searcher based on index reader created
        IndexSearcher INDEX_SEARCHER = new IndexSearcher(INDEX_READER);

        // Check if there are any provided search fields otherwise use the default configured fields
        String[] searchFields = searchFieldsParam == null || searchFieldsParam.length == 0 ?
                searchFieldsProperty : searchFieldsParam;

        // Create a multi fields query parser with a standard analyser
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(searchFields, ANALYSER_STRATEGY);
        // Allow wildcard leading matching
        queryParser.setAllowLeadingWildcard(true);
        // Create a query used the search term provided by a user
        Query query;
        try {
            query = queryParser.parse(searchTerm);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            throw new IllegalArgumentException("Invalid search term: " + searchTerm);
        }
        // Calculate the start index & end index to know how many hits is there
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, INDEX_SEARCHER.count(query));

        // Declare the list of to hold all result
        List<Book> books = new ArrayList<>();

        // if start index is equal to end index, It means that there are no hits
        if (startIndex == endIndex) {
            return books;
        }
        // Create a Sort object with the SortField
        Sort sortByField = new Sort(
                new SortField(orderByField, SortField.Type.STRING, !order.equalsIgnoreCase("asc")));
        // Get the total hits with their equivalent scores
        TopDocs topDocs = INDEX_SEARCHER.search(query, endIndex, sortByField);
        // Get documents with their scores
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        // Loop through documents and create instances of book
        for (int i = startIndex; i < endIndex; i++) {
            // Get document by index
            Document document = INDEX_SEARCHER.doc(scoreDocs[i].doc);
            // Create book document instance
            Book book = new Book();
            // Map fields
            book.setId(Long.parseLong(document.get("id")));
            book.setTitle(document.get("title"));
            book.setAuthor(document.get("author"));
            book.setCategory(document.get("category"));
            // Add constructed book to books list result
            books.add(book);
        }
        // Close reader
        INDEX_READER.close();
        // Return the book with hits
        return books;
    }

}
