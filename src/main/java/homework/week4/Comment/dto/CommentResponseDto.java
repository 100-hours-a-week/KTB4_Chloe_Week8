package homework.week4.Comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String commenter;
    private String commentContent;
    private LocalDateTime commentDateWritten;
    private Boolean isBlinded;

}
