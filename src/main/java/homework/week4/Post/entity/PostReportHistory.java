package homework.week4.Post.entity;

import homework.week4.User.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostReportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "report_id")
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    @Column (name = "reported_at")
    private LocalDateTime reportedAt;

    public  PostReportHistory(User userId,Post postId,LocalDateTime reportedAt){
        this.userId = userId;
        this.postId = postId;
        this.reportedAt = reportedAt;

    }


}
