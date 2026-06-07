package homework.week4.Post.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {

    @Setter
    private Long post_id;

    private String title;
    private String content;
    private String post_image;
    private LocalDateTime datewritten;

    @Setter
    private String writer;

    @Setter
    private Boolean is_edited;
    @Setter
    private int declare_count;
    @Setter
    private Boolean post_hide; //게시물 숨김 여부

    @Setter
    private Long like_count; //좋아요 관련
    @Setter
    private Long comment_count; //댓글 관련
    @Setter
    private Long view_count; //조회수




    public Post(String title,String content,String post_image,LocalDateTime datewritten){
        this.title = title;
        this.content = content;
        this.post_image = post_image;
        this.datewritten = datewritten;
    }

    public Post (String title, String content, String post_image){
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



}
