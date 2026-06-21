package homework.week4.Comment.service;

import homework.week4.Comment.dto.*;
import homework.week4.Comment.entity.Comment;
import homework.week4.Comment.repository.CommentRepository;
import homework.week4.Post.entity.Post;
import homework.week4.Post.service.PostService;
import homework.week4.User.entity.User;
import homework.week4.User.service.UserService;
import homework.week4.exception.ForbiddenException;
import homework.week4.exception.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;
    private final PostService postService;

    //일반 댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long userId, Long postId, @Valid CommentRequestDto request){
        User user = userService.getValidUser(userId); //에외가 일어나면 밑에도 실행 X
        Post post = postService.getValidPost(postId);
        LocalDateTime createdDateTime = LocalDateTime.now();

        Comment comment = new Comment(
                user,
                post,
                request.getComment_content(),
                createdDateTime
        );

        commentRepository.save(comment);

        return new CommentResponseDto(
                comment.getComment_id(),
                comment.getCommenter().getNickname(),
                comment.getCommentContent(),
                comment.getCommentDateWritten()
        );
    }

    // 댓글 검증 및 반환
    public Comment getValidComment(Long commentId){
        Comment comment = commentRepository.findComment(commentId).orElseThrow(
                () -> new NotFoundException("해당 댓글이 존재하지 않습니다."));

        return comment;
    }

    //대댓글 생성
    public ChildCommentResponseDto createChildComment(
            Long userId,
            Long postId,
            Long commentId,
            @Valid CommentRequestDto request){

        User user = userService.getValidUser(userId); //에외가 일어나면 밑에도 실행 X
        Post post = postService.getValidPost(postId);
        Comment comment = getValidComment(commentId);

        LocalDateTime createdDateTime = LocalDateTime.now();

        Comment childcomment = new Comment(
                user,
                post,
                comment,
                request.getComment_content(),
                createdDateTime
        );

        commentRepository.save(childcomment);
        return new ChildCommentResponseDto(
                childcomment.getParent().getCommentId(),
                childcomment.getCommentId(),
                childcomment.getCommenter().getNickname(),
                childcomment.getCommentContent(),
                childcomment.getCommentDateWritten()
        );
    }


    //댓글 목록 반환 -> 상세 게시글을 위해
    public List<CommentResponseDto> listComment (Long postId){
        List<Comment> commentsList = commentRepository.findAll(postId);

        List<CommentResponseDto> commentsListDto = new ArrayList<>();
        for(Comment commenttdto : commentsList){
            commentsListDto.add(
                    new CommentResponseDto(
                            commenttdto.getParent_comment_id(),
                            commenttdto.getComment_id(),
                            commenttdto.getCommenter(),
                            commenttdto.getComment_content(),
                            commenttdto.getComment_datewritten()
                    )

            );
        }
        return commentsListDto;
    }

    public void verifyCommentOwner(Long user_id,Long post_id,String Emessage){
        if(commentRepository.checkCommentOwner(user_id,post_id)){
            return;
        }

        throw new ForbiddenException(Emessage);
    }

    public CommentContentResponseDto modifyComment(
            Long user_id,
            Long comment_id,
            @Valid CommentRequestDto request){
        userService.getValidUser(user_id);
        verifyCommentOwner(user_id,comment_id,"댓글 수정 권한이 없습니다.");

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
        userService.getValidUser(user_id);
        verifyCommentOwner(user_id,comment_id,"댓글 삭제 권한이 없습니다.");

        Boolean unknown_mark = commentRepository.deleteComment(comment_id);

        return new CommentDeleteResponseDto(unknown_mark);
    }


}
