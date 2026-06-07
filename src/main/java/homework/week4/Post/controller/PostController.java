package homework.week4.Post.controller;

import homework.week4.Post.dto.*;
import homework.week4.Post.service.PostService;
import homework.week4.User.service.UserService;
import homework.week4.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponseDto>>>listPost(
            @RequestParam("cursor") Long cursor_id,
            @RequestParam("limit") Long limit_count
    ){

        List<PostResponseDto> result= postService.listPost(cursor_id, limit_count);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("게시글목록 조회_성공",result));

    }

    @PostMapping("/{user_id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(
            @PathVariable Long user_id,
            @Valid @RequestBody PostRequestDto request){

        PostResponseDto result = postService.createPost(user_id,request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("게시글 생성 완료",result));
    }

    @GetMapping("/{user_id}/{post_id}")
    public ResponseEntity<ApiResponse<PostDetailResponseDto>> getPost(
            @PathVariable Long user_id,
            @PathVariable Long post_id
    ){
        PostDetailResponseDto result = postService.getPost(user_id,post_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("상세게시글 조회_성공",result));
    }

    @PutMapping("/{user_id}/{post_id}")
    public ResponseEntity<String> modifyPost(
            @PathVariable Long user_id,
            @PathVariable Long post_id,
            @Valid @RequestBody PostRequestDto request
    ){
        postService.modifyPost(user_id, post_id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("게시글 수정 완료");
    }

    @DeleteMapping("/{user_id}/{post_id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long user_id,
            @PathVariable Long post_id
    ){
        postService.deletePost(user_id,post_id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/{user_id}/{post_id}/declartion")
    public ResponseEntity<ApiResponse<PostDeclareResponseDto>> declarePost(
            @PathVariable Long user_id,
            @PathVariable Long post_id
    ){
        PostDeclareResponseDto result = postService.declarePost(user_id,post_id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("게시글 신고 요청 완료",result));
    }

    @PostMapping("/{user_id}/{post_id}/like")
    public ResponseEntity<ApiResponse<PostLikeResponseDto>> likePost(
            @PathVariable Long user_id,
            @PathVariable Long post_id,
            @RequestBody PostLikeRequestDto request

            ){
        PostLikeResponseDto result = postService.likePost(user_id,post_id,request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("좋아요 요청 완료",result));
    }

}
