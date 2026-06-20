package homework.week4.Post.entity;


import homework.week4.User.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {

    //@Setter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    private String title;
    private String content;
    private String post_image;
    private LocalDateTime datewritten;

    @Setter
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private String writer;


    //신고 테이블에서 count 해서 가져와야 함..
    //@Setter
    //private int declare_count; //신고 횟수..

    @Setter
    private Boolean is_edited = false;
    @Setter
    private Boolean post_hide = false; //게시물 숨김 여부


    @Setter
    private Long like_count; //좋아요 관련
    @Setter
    private Long comment_count; //댓글 관련
    @Setter
    private Long view_count; //조회수




    //게시글 생성용 생성자
    public Post(String title,String content,String post_image,LocalDateTime datewritten){
        this.title = title;
        this.content = content;
        this.post_image = post_image;
        this.datewritten = datewritten;
    }

    //게시글 수정용 생성자
    public Post(String title,String content,String post_image){
        this.title = title;
        this.content = content;
        this.post_image = post_image;
    }



    public void modifyPost (String title, String content, String post_image,Boolean is_edited){
        this.title = title;
        this.content = content;
        this.post_image = post_image;
        this.is_edited = is_edited;
    }

    public Long likeCount(Boolean is_liked){
        if( is_liked == true){
            like_count++;
        }
        else{
            like_count--;
        }

        return like_count;
    }

    public Long commentCountIncrement() {
        return ++comment_count;
    }

    public Long viewCountIncrement() {
        return ++view_count;
    }


    public int getDeclare_count() {
        return 1;
    }

    public void setDeclare_count(int i) {
    }
}
