package homework.week4.Post.repository;

import homework.week4.Post.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.*;

@Repository

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("""
    SELECT p
    FROM Post p
    JOIN FETCH p.writer
    WHERE p.deletedAt IS NULL
    AND (:cursorId IS NULL OR p.postId < :cursorId)
    AND p.postHide = false
    ORDER BY p.postId DESC
""")
    List<Post>findLatestPosts( @Param("cursorId")Long cursorId, Pageable pageable );


    @Query("""
        SELECT p
        FROM Post p
        JOIN FETCH p.writer
        WHERE p.deletedAt IS NULL
        AND p.postId = :postId
        AND p.postHide = false
    """)
    Optional<Post> findPost(@Param("postId") Long postId);

    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM Post p
        WHERE p.deletedAt IS NULL
        AND p.postId = :postId
        AND p.postHide = false
    """)
    Boolean existsPost(@Param("postId") Long postId);






}
