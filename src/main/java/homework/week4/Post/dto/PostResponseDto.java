package homework.week4.Post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long post_id;

    private String title;
    private String content;
    private String post_image;
    private LocalDateTime datewritten;
    private String writer;

    private Long like_count;
    private Long comment_count;
    private Long view_count;
}
