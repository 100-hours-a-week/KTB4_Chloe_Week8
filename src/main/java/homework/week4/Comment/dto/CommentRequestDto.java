package homework.week4.Comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "댓글 본문은 필수값 입니다.")
    private String commentContent;
}
