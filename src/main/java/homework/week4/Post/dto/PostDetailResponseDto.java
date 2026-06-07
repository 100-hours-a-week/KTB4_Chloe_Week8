package homework.week4.Post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import homework.week4.Comment.dto.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostDetailResponseDto {

    @JsonProperty("post")
    private PostResponseDto postResponseDto;

    @JsonProperty("comments")
    private List<CommentResponseDto> commentResponseDto;
}
