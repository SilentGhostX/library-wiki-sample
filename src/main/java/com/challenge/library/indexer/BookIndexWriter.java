package com.challenge.library.indexer;

import com.challenge.library.model.po.Book;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class BookIndexWriter {

    // Analyzer strategy
    private static final Analyzer ANALYSER_STRATEGY = new StandardAnalyzer();

    // Index writer to add a new book to index search registry
    private IndexWriter INDEX_WRITER;

    // The directory that will hold all our indexed fields
    private final Directory indexDirectory;

    public BookIndexWriter(@Value("${lucene.book.indexing-file-path}") String indexDirectory) throws IOException {
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
    }

    @PostConstruct
    public void init() throws IOException {
        // Index writer config
        IndexWriterConfig INDEX_WRITER_CONFIG = new IndexWriterConfig(ANALYSER_STRATEGY);
        // Index writer which will write documents to index directory
        INDEX_WRITER = new IndexWriter(indexDirectory, INDEX_WRITER_CONFIG);
    }

    public void index(List<Book> books) throws IOException {
        // Loop through books and add them to searching registry
        for (Book book : books) {
            index(book);
        }
    }

    public void clearAndIndexAll(List<Book> books) throws IOException {
        // Clear existing indexes
        INDEX_WRITER.deleteAll();
        // Loop through books and add them to searching registry
        for (Book book : books) {
            index(book);
        }
    }

    public void index(Book book) throws IOException {
        // Declare document and add it to registry
        Document document = new Document();
        document.add(new StringField("id", book.getId().toString(), Field.Store.YES));
        document.add(new TextField("title", book.getTitle(), Field.Store.YES));
        document.add(new SortedDocValuesField("title", new BytesRef(book.getTitle())));
        document.add(new TextField("author", book.getAuthor(), Field.Store.YES));
        document.add(new SortedDocValuesField("author", new BytesRef(book.getAuthor()) ));
        document.add(new TextField("category", book.getCategory(), Field.Store.YES));
        document.add(new SortedDocValuesField("category", new BytesRef(book.getCategory()) ));
        // Add document to registry
        INDEX_WRITER.addDocument(document);
        // Commit a new document to documents registry
        INDEX_WRITER.commit();
    }

    @PreDestroy
    private void destroy() throws IOException {
        INDEX_WRITER.close();
    }

}
