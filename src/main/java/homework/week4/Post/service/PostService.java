package homework.week4.Post.service;

import homework.week4.Comment.dto.CommentResponseDto;
import homework.week4.Comment.service.CommentService;
import homework.week4.Post.dto.*;
import homework.week4.Post.entity.Post;
import homework.week4.Post.repository.PostRepository;
import homework.week4.User.service.UserService;
import homework.week4.exception.ForbiddenException;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentService commentService;

    public PostResponseDto createPost (Long user_id, @Valid PostRequestDto request){
        userService.checkUser(user_id); //에외가 일어나면 밑에도 실행 X
        LocalDateTime currentDateTime = LocalDateTime.now();
        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                request.getPost_image(),
                currentDateTime
        );
        Long post_id = postRepository.savePost(user_id,post);

        Post postdto = postRepository.returnPost(post_id);

        return new PostResponseDto(
                postdto.getPost_id(),
                postdto.getTitle(),
                postdto.getContent(),
                postdto.getPost_image(),
                postdto.getDatewritten(),
                postdto.getWriter(),
                false,0L,0L,0L
        );


    }

    public List<PostResponseDto> listPost (Long user_id, Long cursor_id, Long limit_count){
        userService.checkUser(user_id);
        List<Post> postsList = postRepository.listPost(cursor_id,limit_count);

        List<PostResponseDto> postsListDto = new ArrayList<>();
        for(Post postdto : postsList){
            postsListDto.add(
                    new PostResponseDto(
                            postdto.getPost_id(),
                            postdto.getTitle(),
                            postdto.getContent(),
                            postdto.getPost_image(),
                            postdto.getDatewritten(),
                            postdto.getWriter(),
                            postdto.getIs_edited(),
                            postdto.getLike_count(),
                            postdto.getComment_count(),
                            postdto.getView_count()
                    )

            );
        }
        return postsListDto;
    }

    // 상세 게시글 조회
    public PostDetailResponseDto getPost(Long user_id, Long post_id){
        userService.checkUser(user_id); //에외가 일어나면 밑에도 실행 X

        Post postdto = postRepository.getPost(post_id);

        PostResponseDto postResponseDto = new PostResponseDto(
                postdto.getPost_id(),
                postdto.getTitle(),
                postdto.getContent(),
                postdto.getPost_image(),
                postdto.getDatewritten(),
                postdto.getWriter(),
                postdto.getIs_edited(),
                postdto.getLike_count(),
                postdto.getComment_count(),
                postdto.getView_count()
        );

        List<CommentResponseDto> commentResponseDto = commentService.listComment(post_id);

        return new PostDetailResponseDto(postResponseDto,commentResponseDto);
    }

    public void verifyPostOwner(Long user_id,Long post_id){
        if(postRepository.checkpostOwner(user_id,post_id)){
           return;
        }

        throw new ForbiddenException("게시물에 변경 권한이 없습니다.");
    }

    public void modifyPost (Long user_id,Long post_id,@Valid PostRequestDto request){
        userService.checkUser(user_id); //에외가 일어나면 밑에도 실행 X
        verifyPostOwner(user_id,post_id); //게시물에 대한 변경 권한 없음..
        Post modifycontent = new Post(
                request.getTitle(),
                request.getContent(),
                request.getPost_image()
        );

        postRepository.modifyPost(post_id,modifycontent);

    }

    public void deletePost(Long user_id, Long post_id){
        userService.checkUser(user_id);
        verifyPostOwner(user_id,post_id);

        postRepository.removePost(post_id);
    }

    public PostDeclareResponseDto declarePost(Long user_id, Long post_id){
        userService.checkUser(user_id);
        verifyPostOwner(user_id,post_id);

        Post postdto = postRepository.declarePost(post_id);

        return new PostDeclareResponseDto(
                postdto.getPost_id(),
                postdto.getPost_hide()
        );
    }

    public PostLikeResponseDto likePost(Long user_id, Long post_id, PostLikeRequestDto request){
        userService.checkUser(user_id);

        Boolean is_liked = request.getIs_liked();

        Long like_count = postRepository.likePost(post_id,is_liked);

        return new PostLikeResponseDto(like_count);
    }

}
