package homework.week4.Comment.repository;

import homework.week4.Comment.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("""
        SELECT c
        FROM Comment c
        WHERE c.deletedAt IS NULL
        AND c.commentId = :commentId
    """)
    Optional<Comment> findComment(@Param("commentId") Long commentId);


    @Query("""
        SELECT c
        FROM Comment c
        JOIN FETCH c.commenter
        WHERE c.deletedAt IS NULL
        AND c.post.postId = :postId
        ORDER BY c.commentId DESC
    """)
    List<Comment> findAllByPostId(@Param("postId") Long postId);

    @Modifying
    @Query("""
    UPDATE Comment c
    SET c.deletedAt = :deletedAt
    WHERE c.post.postId = :postId
    """)
    void deletePostIdComment(@Param("postId") Long postId, @Param("deletedAt")LocalDateTime deletedAt);

    @Query("""
        SELECT c.post.postId
        FROM Comment c
        WHERE c.commentId = :commentId
    """)
    Long findPostIdByCommentId(@Param("commentId") Long commentId);
}


