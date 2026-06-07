package homework.week4.Comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long parent_comment_id;
    private Long comment_id;
    private String commenter;
    private String comment_content;
    private LocalDateTime comment_datewritten;
}
