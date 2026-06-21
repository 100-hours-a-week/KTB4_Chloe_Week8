package homework.week4.Comment.repository;

import homework.week4.Comment.entity.Comment;

import homework.week4.Post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    List<Comment> findAll(Long postId);
}
