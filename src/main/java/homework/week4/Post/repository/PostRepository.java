package homework.week4.Post.repository;

import homework.week4.Post.entity.Post;
import homework.week4.User.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();
    private Long post_db_id = 0L;

    private final UserRepository userRepository;

    public PostRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //게시글 생성 -> 저장
    public Long savePost(Long user_id, Post post){
        post_db_id ++;
        posts.put(post_db_id,post);

        String writer = userRepository.getUsers().get(user_id).getNickname();
        posts.get(post_db_id).setPost_id(post_db_id); //게시글 번호 설정
        posts.get(post_db_id).setWriter(writer); //게시글 작성자 설정

        //초기화
        posts.get(post_db_id).setIs_edited(false);
        posts.get(post_db_id).setPost_hide(false);
        posts.get(post_db_id).setLike_count(0L);
        posts.get(post_db_id).setComment_count(0L);
        posts.get(post_db_id).setView_count(0L);


        return post_db_id;
    }

    //게시글 번호로 상세 게시글 반환 -> 이건 그냥 반환만 해주는 메서드(서버 / 진짜 조회)
    public Post returnPost(Long post_db_id){
        return posts.get(post_db_id);
    }

    //게시글 조회 -> 이 조회를 할 때 마다 조회수 늘리기
    public Post getPost(Long post_id){

        Long view_count = posts.get(post_id).getView_count();

        posts.get(post_id).setView_count(++view_count); //조회수 늘리기

        return posts.get(post_id);
    }



    //게시글 목록 조회
    public List<Post> listPost(Long cursor_id, Long limit_count){
        List<Post> postsList = new ArrayList<>();

        for(Long i=cursor_id; i< cursor_id+limit_count; i++){
            Post post = posts.get(i);

            if(post != null) {
                postsList.add(post);
            }
        }

        return postsList;
    }

    //게시글 주인 여부 (수정,삭제에서 사용)
    public Boolean checkpostOwner(Long user_id,Long post_id){
        String nickname = userRepository.getUser(user_id).getNickname();
        String writer = posts.get(post_id).getWriter();

        return nickname.equals(writer);
    }

    //게시글 수정
    public void modifyPost(Long post_id, Post post){
       posts.get(post_id).modifyPost(
               post.getTitle(),
               post.getContent(),
               post.getPost_image(),
               true //수정됨
       );

    }

    //게시글 삭제
    public void removePost(Long post_id){
        posts.remove(post_id);
    }

    //게시글 신고
    public Post declarePost(Long post_id){

        int declare_count = posts.get(post_id).getDeclare_count();
        posts.get(post_id).setDeclare_count(++declare_count); //조회수 늘리기

        if(declare_count==5){
            posts.get(post_id).setPost_hide(true);
        }

        return posts.get(post_id);

    }



    public Long likePost(Long post_id,Boolean is_liked){
        Long like_count = posts.get(post_id).getLike_count();

        if(is_liked) {
            posts.get(post_id).setLike_count(++like_count);
        }
        else{
            posts.get(post_id).setLike_count(--like_count);
        }


        return posts.get(post_id).getLike_count();
    }







}
