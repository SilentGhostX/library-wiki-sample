package com.challenge.library.repository;

import com.challenge.library.model.po.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByIsIndexed(boolean isIndexed);

}
