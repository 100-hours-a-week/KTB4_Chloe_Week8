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
    private String profieImage;
    private String commentContent;
    private LocalDateTime commentDateWritten;
    private Boolean isBlinded;

    //해당 댓글의 게시글 댓글 수
    private Long commentCount;

}
