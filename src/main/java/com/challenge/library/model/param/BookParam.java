package com.challenge.library.model.param;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class BookParam {

    @NotNull(message = "title should not be null.")
    @Size(min = 4, max = 255, message = "title must greater than or equal to 4 and less than or equal to 255.")
    private String title;

    @NotNull(message = "author should not be null.")
    @Size(min = 4, max = 100, message = "author must greater than or equal to 4 and less than or equal to 100.")
    private String author;

    @NotNull(message = "category should not be null.")
    @Size(min = 4, max = 50, message = "category must greater than or equal to 4 and less than or equal to 100.")
    private String category;

}
