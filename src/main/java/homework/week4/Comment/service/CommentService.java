package homework.week4.Comment.service;

import homework.week4.Comment.dto.*;
import homework.week4.Comment.entity.Comment;
import homework.week4.Comment.repository.CommentRepository;
import homework.week4.Post.entity.Post;
import homework.week4.Post.service.PostVerifyService;
import homework.week4.User.entity.User;
import homework.week4.User.service.UserService;
import homework.week4.exception.ForbiddenException;
import homework.week4.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
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
    private final PostVerifyService postVerifyService;

    //일반 댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long userId, Long postId, @Valid CommentRequestDto request){
        User user = userService.getValidUser(userId); //에외가 일어나면 밑에도 실행 X
        Post post = postVerifyService.getValidPost(postId);
        LocalDateTime createdDateTime = LocalDateTime.now();

        Comment comment = new Comment(
                user,
                post,
                request.getCommentContent(),
                createdDateTime
        );

        commentRepository.save(comment);
        post.commentCountIncrement();

        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getCommenter().getNickname(),
                comment.getCommentContent(),
                comment.getCommentDateWritten()
        );
    }

    //대댓글 생성
    @Transactional
    public ChildCommentResponseDto createChildComment(
            Long userId,
            Long postId,
            Long commentId,
            @Valid CommentRequestDto request){

        User user = userService.getValidUser(userId); //에외가 일어나면 밑에도 실행 X
        Post post = postVerifyService.getValidPost(postId);
        Comment comment = getValidComment(commentId); //부모 댓글

        LocalDateTime createdDateTime = LocalDateTime.now();

        Comment childcomment = new Comment(
                user,
                post,
                comment,
                request.getCommentContent(),
                createdDateTime
        );

        commentRepository.save(childcomment);
        comment.isHavingChildTrue(); //부모 댓글 자식 있다고 설정

        post.commentCountIncrement();

        return new ChildCommentResponseDto(
                childcomment.getParent().getCommentId(),
                childcomment.getCommentId(),
                childcomment.getCommenter().getNickname(),
                childcomment.getCommentContent(),
                childcomment.getCommentDateWritten()
        );
    }


    // 댓글 검증 및 반환
    public Comment getValidComment(Long commentId){
        Comment comment = commentRepository.findComment(commentId).orElseThrow(
                () -> new NotFoundException("해당 댓글이 존재하지 않습니다."));

        return comment;
    }




    //댓글 목록 반환 -> 상세 게시글을 위해
    @Transactional(readOnly = true)
    public List<CommentResponseDto> listComment (Long postId){
        List<Comment> commentsList = commentRepository.findAllByPostId(postId);

        List<CommentResponseDto> commentsListDto = new ArrayList<>();
        for(Comment commentdto : commentsList){
            commentsListDto.add(
                    new CommentResponseDto(
                            commentdto.getCommentId(),
                            commentdto.getCommenter().getNickname(),
                            commentdto.getCommentContent(),
                            commentdto.getCommentDateWritten()
                    )

            );
        }
        return commentsListDto;
    }

    //댓글 사용자 인증 -> 수정,삭제 할 때만  사용
    public void verifyCommentOwner(Long userId,Long commentId,String message){
        Comment comment = getValidComment(commentId);

        if(!(userId.equals(comment.getCommenter().getUserId()))){
            throw new ForbiddenException(message);
        }
    }

    //댓글 수정
    @Transactional
    public CommentContentResponseDto modifyComment(
            Long userId,
            Long postId,
            Long commentId,
            @Valid CommentRequestDto request){

        userService.checkUser(userId);
        postVerifyService.checkPost(postId);
        verifyCommentOwner(userId,commentId,"댓글 수정 권한이 없습니다.");

        Comment comment = getValidComment(commentId);

        LocalDateTime updatedDateTime = LocalDateTime.now();

        comment.modifyComment(request.getCommentContent(),updatedDateTime);

        return new CommentContentResponseDto(comment.getCommentContent());

    }

    //댓글 삭제
    @Transactional
    public CommentDeleteResponseDto deleteComment(
            Long userId,
            Long postId,
            Long commentId
    ){
        userService.getValidUser(userId);
        postVerifyService.checkPost(postId);
        verifyCommentOwner(userId,commentId,"댓글 삭제 권한이 없습니다.");

        Comment comment = getValidComment(commentId); //삭제할 댓글

        LocalDateTime deletedDateTime = LocalDateTime.now();


        if(comment.getIsHavingChild()){
            //대댓글이 있는 일반 댓글 블라인드 처리 + 댓글 본문에 "알수 없는 댓글이다" 추가
            comment.isBlindedTrue();
        }
        else{
            //대댓글이 없는 댓글 삭제 일시 추가
            comment.isDeleted(deletedDateTime);
        }


        return new CommentDeleteResponseDto(comment.getIsBlinded());
    }


}
