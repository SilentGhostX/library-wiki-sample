package com.challenge.library.mapper;

import com.challenge.library.model.param.BookParam;
import com.challenge.library.model.po.Book;
import com.challenge.library.model.vo.BookVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book map(BookParam bookParam);

    List<BookVO> map(List<Book> books);

    BookVO map(Book book);


}
