package homework.week4.Post.service;

import homework.week4.Comment.dto.CommentResponseDto;
import homework.week4.Comment.service.CommentService;
import homework.week4.Post.dto.*;
import homework.week4.Post.entity.Post;
import homework.week4.Post.repository.PostRepository;
import homework.week4.User.entity.User;
import homework.week4.User.service.UserService;
import homework.week4.exception.ForbiddenException;
import homework.week4.exception.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentService commentService;

    //게시글 등록
    @Transactional
    public PostResponseDto createPost(Long userId, @Valid PostRequestDto request ){
        User writer = userService.getValidUser(userId);
        LocalDateTime createdDateTime = LocalDateTime.now();

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                request.getPostImage(),
                writer,
                createdDateTime
        );

        postRepository.save(post);

        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getPostImage(),
                post.getDateWritten(),
                post.getWriter().getNickname(),
                0L,0L,0L
        );
    }

    //게시글 목록 조회
    public List<PostResponseDto> listPost (Long userId, Long cursorId, int limitCount) {
        userService.checkUser(userId); //사용자 여부 확인
        Pageable pageable = PageRequest.of(0, limitCount);
        List<Post> postList = postRepository.findLatestPosts(cursorId,pageable);

        List<PostResponseDto> postsListDto = new ArrayList<>();
        for(Post postdto : postList){
            postsListDto.add(
                    new PostResponseDto(
                        postdto.getPostId(),
                        postdto.getTitle(),
                        postdto.getContent(),
                        postdto.getPostImage(),
                        postdto.getDateWritten(),
                        postdto.getWriter().getNickname(),
                        postdto.getLikeCount(),
                        postdto.getCommentCount(),
                        postdto.getViewCount()
                    )
            );
        }
        return postsListDto;
    }

    //게시글 확인 및 반환
    public Post getValidPost(Long postId){
        Post post = postRepository.findPost(postId).orElseThrow(
                () -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

        return post;
    }


    // 상세 게시글 조회
    public PostDetailResponseDto getPost(Long userId, Long postId){
        userService.checkUser(userId);
        Post post = getValidPost(postId);

        PostResponseDto postResponseDto = new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getPostImage(),
                post.getDateWritten(),
                post.getWriter().getNickname(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getViewCount()

        );

        List<CommentResponseDto> commentResponseDto = commentService.listComment(postId);

        return new PostDetailResponseDto(postResponseDto,commentResponseDto);
    }


    //게시글 사용자 인증 -> 수정,삭제 할 때만  사용
    public void verifyPostOwner(Long userId,Long postId,String message){
        Post post = getValidPost(postId);
        if(!(userId.equals(post.getWriter().getUserId()))){
            throw new ForbiddenException(message);
        }
    }


    //게시글 수정
    @Transactional
    public void modifyPost (Long userId,Long postId, @Valid PostRequestDto request){
        userService.getValidUser(userId); //에외가 일어나면 밑에도 실행 X
        verifyPostOwner(userId,postId,"게시글 수정 권한이 없습니다.");//게시물에 대한 변경 권한 없음..

        Post post = getValidPost(postId);
        LocalDateTime updatedDateTime = LocalDateTime.now();

        //게시글 수정
        post.modifyPost(
                request.getTitle(),
                request.getContent(),
                request.getPostImage(),
                updatedDateTime
        );
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long userId, Long postId){
        userService.getValidUser(userId);
        verifyPostOwner(userId,postId,"게시글 삭제 권한이 없습니다.");

        Post post = getValidPost(postId);
        LocalDateTime deletedDateTime = LocalDateTime.now();

        post.isDeleted(deletedDateTime);

    }



    public PostDeclareResponseDto reportPost(Long user_id, Long post_id){
        userService.getValidUser(user_id);

        Post postdto = postRepository.reportPost(post_id);

        return new PostDeclareResponseDto(
                postdto.getPost_id(),
                postdto.getPost_hide()
        );
    }

    public PostLikeResponseDto likePost(Long user_id, Long post_id, PostLikeRequestDto request){
        userService.getValidUser(user_id);

        Boolean is_liked = request.getIs_liked();

        Long like_count = postRepository.likePost(post_id,is_liked);

        return new PostLikeResponseDto(like_count);
    }

}
