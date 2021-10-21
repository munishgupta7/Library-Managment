package uk.co.library.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Generated
@NoArgsConstructor
public class Book {
    @NonNull
    private String isbn;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private String description;
    @NonNull
    private String tag1;
    @NonNull
    private String tag2;

}
