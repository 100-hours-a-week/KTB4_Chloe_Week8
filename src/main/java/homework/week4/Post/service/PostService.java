package homework.week4.Post.service;

import homework.week4.Comment.dto.CommentResponseDto;
import homework.week4.Comment.repository.CommentRepository;
import homework.week4.Comment.service.CommentService;
import homework.week4.Post.dto.*;
import homework.week4.Post.entity.Like;
import homework.week4.Post.entity.Post;
import homework.week4.Post.entity.PostChangeHistory;
import homework.week4.Post.entity.PostReportHistory;
import homework.week4.Post.repository.ChangeRepository;
import homework.week4.Post.repository.LikeRespoitory;
import homework.week4.Post.repository.PostRepository;
import homework.week4.Post.repository.ReportRepository;
import homework.week4.User.entity.User;
import homework.week4.User.service.UserService;
import homework.week4.exception.DuplicateResourceException;
import homework.week4.exception.ForbiddenException;
import homework.week4.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRespoitory likeRespoitory;
    private final ReportRepository reportRepository;
    private final ChangeRepository changeRepository;
    private final CommentRepository commentRepository;

    private final UserService userService;
    private final CommentService commentService;
    private final PostVerifyService postVerifyService;

    //게시글 등록
    @Transactional
    public PostResponseDto createPost(Long userId, @Valid @ModelAttribute PostRequestDto request ){
        User writer = userService.getValidUser(userId);
        LocalDateTime createdDateTime = LocalDateTime.now();

        MultipartFile file = request.getPostImage();
        String postImagePath = null;

        if (file != null && !file.isEmpty()) {

            String uploadDir = "src/main/resources/static/UploadPhoto/PostImage";

            Path directoryPath = Paths.get(uploadDir).toAbsolutePath();

            Path savePath = directoryPath.resolve(file.getOriginalFilename());

            try {
                Files.createDirectories(directoryPath);
                System.out.println("저장 경로 = " + savePath.toAbsolutePath());

                postImagePath = "/UploadPhoto/PostImage/" + file.getOriginalFilename();

                file.transferTo(savePath.toFile());

                System.out.println("파일 저장 성공");
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
                throw new RuntimeException("파일 저장에 실패했습니다.", e);
            }
        }

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                postImagePath,
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
    @Transactional(readOnly = true)
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



    // 상세 게시글 조회
    @Transactional(readOnly = true)
    public PostDetailResponseDto getPost(Long userId, Long postId){
        userService.checkUser(userId);
        Post post = postVerifyService.getValidPost(postId);

        post.viewCountIncrement();

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

        post.viewCountIncrement();

        List<CommentResponseDto> commentResponseDto = commentService.listComment(postId);

        return new PostDetailResponseDto(postResponseDto,commentResponseDto);
    }


    //게시글 사용자 인증 -> 수정,삭제 할 때만  사용
    public void verifyPostOwner(Long userId,Long postId,String message){
        Post post = postVerifyService.getValidPost(postId);
        if(!(userId.equals(post.getWriter().getUserId()))){
            throw new ForbiddenException(message);
        }
    }


    //게시글 수정
    @Transactional
    public void modifyPost (Long userId, Long postId, @Valid @ModelAttribute PostRequestDto request){
        userService.checkUser(userId); //에외가 일어나면 밑에도 실행 X
        verifyPostOwner(userId,postId,"게시글 수정 권한이 없습니다.");//게시물에 대한 변경 권한 없음..

        Post post = postVerifyService.getValidPost(postId);
        LocalDateTime updatedDateTime = LocalDateTime.now();

        MultipartFile file = request.getPostImage();
        String postImagePath = null;

        if (file != null && !file.isEmpty()) {

            String uploadDir = "src/main/resources/static/UploadPhoto/PostImage";

            Path directoryPath = Paths.get(uploadDir).toAbsolutePath();

            Path savePath = directoryPath.resolve(file.getOriginalFilename());

            try {
                Files.createDirectories(directoryPath);
                System.out.println("저장 경로 = " + savePath.toAbsolutePath());

                postImagePath = "/UploadPhoto/ProfileImage/" + file.getOriginalFilename();

                file.transferTo(savePath.toFile());

                System.out.println("파일 저장 성공");
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
                throw new RuntimeException("파일 저장에 실패했습니다.", e);
            }
        }

        //게시글 수정
        post.modifyPost(
                request.getTitle(),
                request.getContent(),
                postImagePath,
                updatedDateTime
        );

        //게시글 수정 이력 보존
        PostChangeHistory postChangeHistory = new PostChangeHistory(
                post,
                updatedDateTime,
                request.getTitle(),
                request.getContent(),
                postImagePath
        );

        //수정 이력 저장
        changeRepository.save(postChangeHistory);

    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long userId, Long postId){
        userService.checkUser(userId);
        verifyPostOwner(userId,postId,"게시글 삭제 권한이 없습니다.");

        Post post = postVerifyService.getValidPost(postId);
        LocalDateTime deletedDateTime = LocalDateTime.now();

        post.isDeleted(deletedDateTime);
        //게시글을 참조하고 있는 댓글 다 삭제..
        commentRepository.deletePostIdComment(postId,deletedDateTime);

    }

    //게시글 좋아요 등록
    @Transactional
    public PostLikeResponseDto createLike(Long userId, Long postId){

        //게시글 & 사용자 검증 다 함.
        User user = userService.getValidUser(userId);
        Post post = postVerifyService.getValidPost(postId);

        //좋아요 이미 있는지 확인
        likeRespoitory.findByUserUserIdAndPostPostId(user.getUserId(), post.getPostId())
                .ifPresent(like -> {
                    throw new DuplicateResourceException("이미 존재하는 좋아요입니다.");
                });

        Like like = new Like(user,post);

        likeRespoitory.save(like);

        Long likeCount = likeRespoitory.countByPostPostId(postId);

        post.likeCount(likeCount);

        return new PostLikeResponseDto(likeCount);
    }

    //게시글 좋아요 취소
    @Transactional
    public PostLikeResponseDto cancelLike(Long userId, Long postId){

        User user = userService.getValidUser(userId);
        Post post = postVerifyService.getValidPost(postId);

        Like like = likeRespoitory.findByUserUserIdAndPostPostId(user.getUserId(),post.getPostId())
                .orElseThrow(() -> new NotFoundException("해당 좋아요가 존재하지 않습니다."));

        //좋아요 삭제하고
        likeRespoitory.delete(like);

        //삭제가 적용된 좋아요 수 반환
        Long likeCount = likeRespoitory.countByPostPostId(postId);

        //좋아요 수 게시글 likeCount에 업데이트
        post.likeCount(likeCount);

        return new PostLikeResponseDto(likeCount);

    }


    //게시글 신고
    @Transactional
    public PostReportResponseDto reportPost(Long userId, Long postId){
        User user = userService.getValidUser(userId);
        Post post = postVerifyService.getValidPost(postId);

        LocalDateTime reportedDateTime = LocalDateTime.now();

        PostReportHistory postReportHistory = new PostReportHistory(user,post,reportedDateTime);

        //신고 저장
        reportRepository.save(postReportHistory);

        //count 해서 신고 횟수 계산
        int reportCount = reportRepository.countByPostPostId(postId);

        if(reportCount == 5){
            post.PostHideTrue();
        }

        return new PostReportResponseDto(
                post.getPostId(),
                post.getPostHide()
        );
    }



}
