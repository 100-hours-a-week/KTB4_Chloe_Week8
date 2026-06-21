package homework.week4.Comment.entity;

import homework.week4.Post.entity.Post;
import homework.week4.User.entity.User;
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
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // 자기 참조 ( 부모 댓글 (1) <-> 대댓글들(N) )
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @OneToMany(mappedBy = "parentCommentId")
    private List<Comment> childrenComments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User commenter;

    @Column(name ="comment_content")
    private String commentContent;
    @Column(name ="comment_date_written")
    private LocalDateTime commentDateWritten;

    @Column(name =" is_having_child")
    private Boolean isHavingChild;
    @Column(name =" is_blinded")
    private Boolean isBlinded;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @Column(name="deleted_at")
    private LocalDateTime deletedAt;
    


    public Comment(User user, Post post, String comment_content, LocalDateTime createdAt){
        this.commenter = user;
        this.post = post;
        this.commentContent = comment_content;
        this.commentDateWritten = createdAt;
        this.createdAt = createdAt;
    }

    public Comment(User user, Post post, Comment parent, String comment_content, LocalDateTime createdAt){
        this.commenter = user;
        this.post = post;
        this.parent = parent;
        this.commentContent = comment_content;
        this.commentDateWritten = createdAt;
        this.createdAt = createdAt;
    }

    public void modifyComment(String comment_content,LocalDateTime updatedAt){
        this.commentContent = comment_content;
        this.commentDateWritten = updatedAt;
        this.updatedAt = updatedAt;
    }

}
