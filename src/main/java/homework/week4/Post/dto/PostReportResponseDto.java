package homework.week4.Post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostReportResponseDto {
    private Long postId;
    private Boolean postHide;
}
