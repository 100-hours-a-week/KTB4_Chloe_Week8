package homework.week4.Post.entity;


import homework.week4.User.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

    private String title;
    private String content;

    @Column(name="post_image")
    private String postImage;

    @Column(name="date_written")
    private LocalDateTime dateWritten;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Column(name="is_edited")
    private Boolean isEdited = false;

    @Column(name="post_hide")
    private Boolean postHide = false; //게시물 숨김 여부



    @Column(name="like_count")
    private Long likeCount; //좋아요 관련

    @Column(name="comment_count")
    private Long commentCount; //댓글 관련

    @Column(name="view_count")
    private Long viewCount; //조회수

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @Column(name="deleted_at")
    private LocalDateTime deletedAt;


    //게시글 생성용 생성자
    public Post(String title, String content, String post_image, User writer, LocalDateTime datewritten){
        this.title = title;
        this.content = content;
        this.postImage = post_image;
        this.writer = writer;
        this.dateWritten = datewritten;

        this.likeCount = 0L;
        this.commentCount = 0L;
        this.viewCount = 0L;

        this.createdAt = datewritten;
    }

    public void modifyPost (String title, String content, String post_image, LocalDateTime updatedAt){
        this.title = title;
        this.content = content;
        this.postImage = post_image;

        // 게시글 보일 때, 수정 일시로 보이도록
        this.dateWritten = updatedAt;
        this.updatedAt = updatedAt;
        this.isEdited = true;
    }

    public void isDeleted(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }

    public void PostHideTrue(){
        this.postHide = true;
    }

    public void likeCount(Long likCount){
        this.likeCount = likCount;
    }

    public void commentCountIncrement() {
        this.commentCount = commentCount + 1L;
    }

    public void commentCountDecrement(){
        this.commentCount = commentCount - 1L;
    }

    public void viewCountIncrement() {
        this.viewCount = viewCount+1L;
    }



}
