package homework.week4.Comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Setter
    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Long post_id;


    @Setter
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();


    //User 외래키로 변경
    @Setter
    private String commenter;

    private String comment_content;
    private LocalDateTime comment_datewritten;

    @Setter
    private Boolean is_having_child;

    @Setter
    private Boolean is_blinde;
    


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

    public Long getParent_comment_id() {
        return 1L;
    }
}
