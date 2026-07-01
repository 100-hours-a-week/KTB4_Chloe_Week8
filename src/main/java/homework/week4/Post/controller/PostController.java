package homework.week4.Post.controller;

import homework.week4.Post.dto.*;
import homework.week4.Post.service.PostService;
import homework.week4.User.service.UserService;
import homework.week4.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://127.0.0.1:5500")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/{user_id}")
    public ResponseEntity<ApiResponse<List<PostResponseDto>>>listPost(
            @PathVariable Long user_id,
            @RequestParam(value = "cursor", required = false) Long cursorId, //null 허용
            @RequestParam("limit") int limit_count
    ){

        List<PostResponseDto> result= postService.listPost(user_id,cursorId, limit_count);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("게시글목록 조회_성공",result));

    }

    @PostMapping(
            value = "/{user_id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(
            @PathVariable Long user_id,
            @Valid @ModelAttribute PostRequestDto request){

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
    public ResponseEntity<ApiResponse<Void>> modifyPost(
            @PathVariable Long user_id,
            @PathVariable Long post_id,
            @Valid @RequestBody PostRequestDto request
    ){
        postService.modifyPost(user_id, post_id, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
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
    public ResponseEntity<ApiResponse<PostReportResponseDto>> declarePost(
            @PathVariable Long user_id,
            @PathVariable Long post_id
    ){
        PostReportResponseDto result = postService.reportPost(user_id,post_id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("게시글 신고 요청 완료",result));
    }

    //좋아요 등록
    @PostMapping("/{user_id}/{post_id}/like")
    public ResponseEntity<ApiResponse<PostLikeResponseDto>> likeCreatePost(
            @PathVariable Long user_id,
            @PathVariable Long post_id
            ){
        PostLikeResponseDto result = postService.createLike(user_id,post_id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("좋아요 요청 완료",result));
    }

    //좋아요 취소
    @DeleteMapping("/{user_id}/{post_id}/like")
    public ResponseEntity<ApiResponse<PostLikeResponseDto>> likeCancelPost(
            @PathVariable Long user_id,
            @PathVariable Long post_id

    ){
        PostLikeResponseDto result = postService.cancelLike(user_id,post_id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("좋아요 삭제 완료",result));
    }

}
