package homework.week4.Comment.service;

import homework.week4.Comment.dto.CommentContentResponseDto;
import homework.week4.Comment.dto.CommentDeleteResponseDto;
import homework.week4.Comment.dto.CommentRequestDto;
import homework.week4.Comment.dto.CommentResponseDto;
import homework.week4.Comment.entity.Comment;
import homework.week4.Comment.repository.CommentRepository;
import homework.week4.Post.entity.Post;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.User.service.UserService;
import homework.week4.exception.ForbiddenException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@Validated
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentResponseDto createComment(Long user_id,Long post_id, @Valid CommentRequestDto request){
        userService.checkUser(user_id); //에외가 일어나면 밑에도 실행 X
        LocalDateTime currentDateTime = LocalDateTime.now();

        Comment comment = new Comment(
                request.getComment_content(),
                currentDateTime
        );

        Comment commentdto = commentRepository.createComment(user_id,post_id,comment);
        return new CommentResponseDto(
                commentdto.getParent_comment_id(),
                commentdto.getComment_id(),
                commentdto.getCommenter(),
                commentdto.getComment_content(),
                commentdto.getComment_datewritten()
        );
    }

    public CommentResponseDto createChildComment(
            Long user_id,
            Long post_id,
            Long comment_id,
            @Valid CommentRequestDto request){
        userService.checkUser(user_id); //에외가 일어나면 밑에도 실행 X
        LocalDateTime currentDateTime = LocalDateTime.now();

        Comment comment = new Comment(
                request.getComment_content(),
                currentDateTime
        );

        Comment commentdto = commentRepository.createChildComment(user_id,post_id,comment_id,comment);
        return new CommentResponseDto(
                commentdto.getParent_comment_id(),
                commentdto.getComment_id(),
                commentdto.getCommenter(),
                commentdto.getComment_content(),
                commentdto.getComment_datewritten()
        );
    }

    public void verifyCommentOwner(Long user_id,Long post_id){
        if(commentRepository.checkCommentOwner(user_id,post_id)){
            return;
        }

        throw new ForbiddenException("댓글 변경 권한이 없습니다.");
    }

    public CommentContentResponseDto modifyComment(
            Long user_id,
            Long comment_id,
            @Valid CommentRequestDto request){
        userService.checkUser(user_id);
        verifyCommentOwner(user_id,comment_id);

        Comment modifycontent = new Comment(
                request.getComment_content()
        );

        Comment commentdto = commentRepository.modifyComment(comment_id,modifycontent);

        return new CommentContentResponseDto(commentdto.getComment_content());

    }

    public CommentDeleteResponseDto deleteComment(
            Long user_id,
            Long comment_id
    ){
        userService.checkUser(user_id);
        verifyCommentOwner(user_id,comment_id);

        Boolean unknown_mark = commentRepository.deleteComment(comment_id);

        return new CommentDeleteResponseDto(unknown_mark);
    }


}
