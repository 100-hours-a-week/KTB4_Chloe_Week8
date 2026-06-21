package homework.week4.Post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostChangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long changeId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    @Column(name ="changed_at")
    private LocalDateTime changedAt;

    @Column(name = "changed_title")
    private String changedTitle;

    @Column(name = "changed_content")
    private String changedContetnt;

    @Column(name = "changed_post_image")
    private String changedPostImage;

    public PostChangeHistory(
            Post postId,
            LocalDateTime changedAt,
            String changedTitle,
            String changedContetnt,
            String changedPostImage
            ){
        this.postId = postId;
        this.changedAt = changedAt;
        this.changedTitle = changedTitle;
        this.changedContetnt = changedContetnt;
        this.changedPostImage = changedPostImage;

    }
}
