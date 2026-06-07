package homework.week4.Post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDeclareResponseDto {
    private Long post_id;
    private Boolean post_hide;
}
