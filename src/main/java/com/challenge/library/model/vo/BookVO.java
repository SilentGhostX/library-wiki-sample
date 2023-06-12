package com.challenge.library.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookVO {

    private Long id;
    private String title;
    private String author;
    private String category;

}
