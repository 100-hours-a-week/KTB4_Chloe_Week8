package homework.week4.Comment.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Setter
    private Long post_id;
    @Setter
    private Long parent_comment_id;

    @Setter
    private Long comment_id;
    @Setter
    private String commenter;

    private String comment_content;
    private LocalDateTime comment_datewritten;

    @Setter
    private Boolean is_having_child;

    @Setter
    private Boolean unknown_mark;

    public Comment(String comment_content,LocalDateTime comment_datewritten){
        this.comment_content = comment_content;
        this.comment_datewritten = comment_datewritten;
    }

    public Comment( String comment_content) {
        this.comment_content = comment_content;
    }

    public void modifyComment(String comment_content){
        this.comment_content = comment_content;
    }

}
