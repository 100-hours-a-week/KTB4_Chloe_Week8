package homework.week4.Comment.controller;

import homework.week4.Comment.dto.CommentContentResponseDto;
import homework.week4.Comment.dto.CommentDeleteResponseDto;
import homework.week4.Comment.dto.CommentRequestDto;
import homework.week4.Comment.dto.CommentResponseDto;
import homework.week4.Comment.service.CommentService;
import homework.week4.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{user_id}/{post_id}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @PathVariable Long user_id,
            @PathVariable Long post_id,
            @Valid @RequestBody CommentRequestDto request
            ){
        CommentResponseDto result = commentService.createComment(user_id, post_id, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("댓글 생성 완료",result));
    }

    @PostMapping("/{comment_id}/replies")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createChileComment(
            @PathVariable Long user_id,
            @PathVariable Long post_id,
            @PathVariable Long comment_id,
            @Valid @RequestBody CommentRequestDto request
    ){
        CommentResponseDto result = commentService.createChildComment(user_id, post_id, comment_id,request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("대댓글 생성 완료",result));
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<ApiResponse<CommentContentResponseDto>> modifyComment(
            @PathVariable Long user_id,
            @PathVariable Long comment_id,
            @Valid @RequestBody CommentRequestDto request
    ){
        CommentContentResponseDto result = commentService.modifyComment(user_id,comment_id,request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("댓글 수정 완료",result));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<ApiResponse<CommentDeleteResponseDto>> deleteComment(
            @PathVariable Long user_id,
            @PathVariable Long comment_id
    ){
        CommentDeleteResponseDto result = commentService.deleteComment(user_id,comment_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("댓글 삭제 완료",result));
    }

}
