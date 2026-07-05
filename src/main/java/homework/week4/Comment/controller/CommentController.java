package homework.week4.Comment.controller;

import homework.week4.Comment.dto.*;
import homework.week4.Comment.service.CommentService;
import homework.week4.Security.Userdetails.CustomUserDetails;
import homework.week4.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://127.0.0.1:5500")
@RestController
@RequestMapping("/posts/{post_id}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long post_id,
            @Valid @RequestBody CommentRequestDto request
            ){
        Long userId = userDetails.getUserId();
        CommentResponseDto result = commentService.createComment(userId, post_id, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("댓글 생성 완료",result));
    }

    //대댓글 생성 요청
    @PostMapping("/{comment_id}/replies")
    public ResponseEntity<ApiResponse<ChildCommentResponseDto>> createChileComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long post_id,
            @PathVariable Long comment_id,
            @Valid @RequestBody CommentRequestDto request
    ){
        Long userId = userDetails.getUserId();
        ChildCommentResponseDto result = commentService.createChildComment(userId, post_id, comment_id,request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("대댓글 생성 완료",result));
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<ApiResponse<CommentContentResponseDto>> modifyComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long post_id,
            @PathVariable Long comment_id,
            @Valid @RequestBody CommentRequestDto request
    ){
        Long userId = userDetails.getUserId();
        CommentContentResponseDto result = commentService.modifyComment(userId,post_id,comment_id,request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("댓글 수정 완료",result));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<ApiResponse<CommentDeleteResponseDto>> deleteComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long post_id,
            @PathVariable Long comment_id
    ){
        Long userId = userDetails.getUserId();
        CommentDeleteResponseDto result = commentService.deleteComment(userId,post_id,comment_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("댓글 삭제 완료",result));
    }

}
