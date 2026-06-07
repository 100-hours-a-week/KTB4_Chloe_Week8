package homework.week4.Comment.repository;

import homework.week4.Comment.entity.Comment;
import homework.week4.Post.entity.Post;
import homework.week4.User.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CommentRepository {
    private final Map<Long, Comment> comments = new HashMap<>();
    private Long comment_db_id = 0L;

    private final UserRepository userRepository;

    public CommentRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Comment createComment(Long user_id, Long post_id,Comment comment){
        comment_db_id ++;
        comments.put(comment_db_id,comment);

        String commenter = userRepository.getUsers().get(user_id).getNickname();
        comments.get(comment_db_id).setComment_id(comment_db_id);
        comments.get(comment_db_id).setCommenter(commenter);
        comments.get(comment_db_id).setParent_comment_id(0L);
        comments.get(comment_db_id).setIs_having_child(false);
        comments.get(comment_db_id).setUnknown_mark(false);
        comments.get(comment_db_id).setPost_id(post_id);

       return comments.get(comment_db_id);

    }

    public Comment createChildComment(Long user_id, Long post_id,Long comment_id,Comment comment){
        comment_db_id ++;
        comments.put(comment_db_id,comment);

        String commenter = userRepository.getUsers().get(user_id).getNickname();
        comments.get(comment_db_id).setComment_id(comment_db_id);
        comments.get(comment_db_id).setCommenter(commenter);
        comments.get(comment_db_id).setParent_comment_id(comment_id);
        comments.get(comment_db_id).setIs_having_child(false);
        comments.get(comment_db_id).setUnknown_mark(false);
        comments.get(comment_db_id).setPost_id(post_id);

        //부모 댓글
        comments.get(comment_id).setIs_having_child(true);

        return comments.get(comment_db_id);

    }

    //댓글 주인 여부 (수정,삭제에서 사용)
    public Boolean checkCommentOwner(Long user_id,Long comment_id){
        String nickname = userRepository.getUser(user_id).getNickname();
        String commenter = comments.get(comment_id).getCommenter();

        return nickname.equals(commenter);
    }

    //게시글 수정
    public Comment modifyComment(Long comment_id, Comment comment) {
        comments.get(comment_id).modifyComment(
                comment.getComment_content()
        );

        return comments.get(comment_id);

    }

    public Boolean deleteComment(Long comment_id){
        if(comments.get(comment_id).getIs_having_child()){
            comments.get(comment_id).setUnknown_mark(true);

            return true;
        }
        return false;

    }
}
