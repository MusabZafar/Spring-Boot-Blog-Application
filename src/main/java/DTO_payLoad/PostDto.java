package DTO_payLoad;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    //title should not be null or empty
    //title should have at least 2 charachters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 charachters")
    private String title;
    //Post description should not be null or empty
    //Post description should have at least 10 charachters
    @NotEmpty
    @Size(min = 10, message = "Post has at least 10 charachters")
    private String description;
    //Post description should not be null or empty
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
    private Long categoryId;

}
