package homework.week4.Post.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostEditResponseDto {

    private String title;
    private String content;
    private String postImage;
}
